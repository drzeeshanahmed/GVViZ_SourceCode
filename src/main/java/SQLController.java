import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


public class SQLController extends SwingWorker<Void, Void> implements ActionListener, FocusListener{
	
	private Model model;
	private SqlView sqlview;
	private DBViewFrame dbview;
	private BeginerView begView;
	private BeginerView2 begView2;
	private Statement stmt;
	private Connection con;
	public Boolean connection;
	private ResultSetMetaData rsmd;
	private List<String> SIDs;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	int columns = 0;
	int rows = 0;
	LoadingScreen l;
	JLabel status;
	JLabel processing;
	JLabel count;
		
	SQLController(Model model, SqlView sqlview, DBViewFrame dbview, BeginerView begView, BeginerView2 begView2, JLabel stat, JLabel processing, JLabel count) {
		this.model = model;
		this.sqlview = sqlview;
		this.dbview = dbview;
		this.begView = begView;
		this.begView2 = begView2;
		connection = false;
		l = new LoadingScreen();
		SIDs = new ArrayList<String>();
		status = stat;
		this.processing = processing;
		this.count = count;
	}
	
	public void initController() {
		dbview.getButton().addActionListener(this);
		sqlview.getSubmit().addActionListener(this);
		begView.getSearch().addActionListener(this);
		begView.getSubmitQuery().addActionListener(this);
		begView.getSelectAll().addActionListener(this);
		begView.getDeselectAll().addActionListener(this);
	}
	
	public void Connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");  
		
		con = DriverManager.getConnection( "jdbc:mysql://"+dbview.getDbhosttext().getText()+ ":" + dbview.getDbporttext().getText() +"/?useSSL=false" ,dbview.getDbusertext().getText(), new String(dbview.getDbpasstext().getPassword()));
		stmt = con.createStatement(); 
		connection = true;
		
		Object src[][] = ExecuteQury("SHOW DATABASES;");
		String[] array = Stream.of(src).flatMap(Stream::of).toArray(String[]::new);
		model.setDbs(array);
		
		dbview.setVisible(false);
		status.setText("Connected to the DB");
		status.setForeground(Color.green);
	}
	
	public Object[][] ExecuteQury(String query) throws SQLException {
		ResultSet rs = stmt.executeQuery(query);
		rsmd=rs.getMetaData();  


		columns = rs.getMetaData().getColumnCount();
		rs.last(); 
		rows = rs.getRow();		
		rs.beforeFirst();
				
		//System.out.println(rows + " " + columns);
		
		Object[][] resultSet = new Object[rows][columns];
        int row = 0;
        while (rs.next()) {
            for (int i = 0; i < columns; i++) {
                resultSet[row][i] = rs.getObject(i+1);
            }
            row++;
        }
		
		//System.out.println("Got the results");

		return resultSet;
	}
	
	public String[] getColumNames() throws SQLException {
		 
		String[] columNames = new String[columns];
		
		for(int i =1; i <= columns; i++) {
			//System.out.println(rsmd.getColumnName(i));
			columNames[i - 1] = rsmd.getColumnName(i);
		}
		
		return columNames;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand()) {
		case "DBSubmit":
			if(dbview.getDbhosttext().getText().isEmpty() == true || dbview.getDbhosttext().getText().isEmpty() == true || dbview.getDbusertext().getText().isEmpty() == true) {
				JOptionPane.showMessageDialog(dbview, "The hostname, username and port can't not be empty.", "DB Connection Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					Connect();
					dbview.setDbhosttext("");
					dbview.setDbpasstext("");
					dbview.setDbporttext("");
					dbview.setDbusertext("");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(dbview, "Error Login in DB.", "DB Connection Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case "SQLSubmit":
			
			if(con == null) {
				JOptionPane.showMessageDialog(null, "You need to connect to database before submitting a query.", "DB Connection Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
			
			try {
				model.setData(ExecuteQury(sqlview.getAreaText().getText()));
				model.setColumns(getColumNames());
				//System.out.println("Set Columns: " + Arrays.toString(model.getColumns()));
				model.setHscolums(Arrays.asList(model.getColumns()));
				System.out.println("Done");
				sqlview.getDtm().setColumnIdentifiers(model.getColumns());
				sqlview.getDtm().setRowCount(rows);
				sqlview.getDtm().setColumnCount(columns);
				
				count.setText("Number of Records Found: " + rows);
				
				for(int i =0; i < rows; i++) {
					for(int j =0; j < columns; j++) {
						sqlview.getDtm().setValueAt(model.getData()[i][j], i, j);
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JTextArea ta = new JTextArea(2, 30);
				ta.setText(e1.toString());
				ta.setEditable(false);
				JOptionPane.showMessageDialog(null, new JScrollPane(ta), "Error in SQL Query", JOptionPane.ERROR_MESSAGE);
			}
			break;
			
		  case "Search":
			  
				if(con == null) {
					JOptionPane.showMessageDialog(null, "You need to connect to database before submitting a query.", "DB Connection Error", JOptionPane.ERROR_MESSAGE);
					break;
				}
			  
			  try {
					JFrame TopFrame = (JFrame) SwingUtilities.getWindowAncestor(begView2);
					TopFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				  processing.setText("Processing...");
				  processing.setForeground(Color.red);
					
				  selectAll(false);
				  stmt.executeQuery("USE db_pmi;");
				  String q = "";
				  //String q = "SELECT DISTINCT(gene_id) FROM zeegenomics.qc_genes_c1_c2 USE INDEX(idx_SITP)";
				  
				  if(begView2.getNon_coding().isSelected()) 
					  //q += "SELECT GD_Ensembl_Id, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\" AND GD_Category!=\"Protein_Coding\";";
					  q += "SELECT GD_Ensembl_Id, GD_Gene_Name, GD_Category, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\" AND GD_Category!=\"Protein_Coding\";";
				  else if(begView2.getCoding().isSelected()) 
					  //q += "SELECT GD_Ensembl_Id, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\" AND GD_Category=\"Protein_Coding\";";
					  q += "SELECT GD_Ensembl_Id, GD_Gene_Name, GD_Category, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\" AND GD_Category=\"Protein_Coding\";";
				  else if(begView2.getAll().isSelected())
					  //q += "SELECT GD_Ensembl_Id, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\";";
					  q += "SELECT GD_Ensembl_Id, GD_Gene_Name, GD_Category, GD_Disease FROM db_pmi.gene_disease_hfc WHERE GD_Disease LIKE \"%" + begView.getSearchText().getText() + "%\";";
				  else if(begView2.getAll2().isSelected())
					  q += "SELECT DISTINCT(gene_id) FROM zeegenomics.qc_genes_c1_c2 USE INDEX(idx_SITP)";

				  System.out.println(q);
				  
				  ExecuteQuery2 worker = new ExecuteQuery2(q, stmt, model, begView, processing, count);
				  worker.execute();	

			  } catch (SQLException e1) {
				  JTextArea ta = new JTextArea(2, 30);
				  ta.setText(e1.toString()); ta.setEditable(false);
				  JOptionPane.showMessageDialog(null, new JScrollPane(ta),"Error in SQL Query", JOptionPane.ERROR_MESSAGE);
			  }
			  break;
			  
		  case "SubmitBeg":		
			  			  
				if(con == null) {
					JOptionPane.showMessageDialog(null, "You need to connect to database before submitting a query.", "DB Connection Error", JOptionPane.ERROR_MESSAGE);
					break;
				}			  
			  	  String q = "";
			      String type = begView2.getGroup2().getSelection().getActionCommand();
			  
			  	  if(begView2.getAll2().isSelected() == true) {
			  		  q += " FROM zeegenomics.qc_genes_c1_c2 USE INDEX(idx_SITP) WHERE ";
			  		  type = "TPM";
			  	  }else
			  		  q += " FROM (SELECT gene_id, SID, "+type+" FROM zeegenomics.qc_genes_c1_c2 USE INDEX(idx_SITP) WHERE "; //SELECT * 
			  
			  	  //String q = "SELECT gene_id, SID, TPM FROM zeegenomics.qc_genes_c1_c2 USE INDEX(idx_SITP) ";
			  	  
				  String SID = begView2.getSIDText().getText().trim();
				  String TPM = begView2.getTPMText().getText().trim(); 
				  
				  SID = SID.replaceAll("[^,1234567890-]", "");
				  TPM = TPM.replaceAll("[^,.1234567890-]", "");	
				  
				  if(TPM.isEmpty() != true && SID.isEmpty() != true) { 
				  
				  List<String> SIDcoma = new ArrayList<String>();
				  List<String> SIDbar = new ArrayList<String>();
				  List<String> TPMcoma = new ArrayList<String>();
				  List<String> TPMbar = new ArrayList<String>();
				  SIDs = new ArrayList<String>();
				  int SIDCount = 0;
			      
				  String[] SIDArray = SID.split(",");
				  String[] TPMArray = TPM.split(",");
				  
				  if(SIDArray.length == 1 && SIDArray[0].isEmpty())
					  SIDArray = new String[0];
				  
				  if(TPMArray.length == 1 && TPMArray[0].isEmpty())
					  TPMArray = new String[0];
				  
				  
			      for (String element: SIDArray) {
			    	  if(!element.contains("-")) {
			    		  SIDcoma.add(element);
			    	  	  SIDs.add(element);
			    	  } else {
			    		  String[] tmp = element.split("-");
			    		  SIDbar.add(tmp[0]);
			    		  SIDbar.add(tmp[1]);
			    		  
			    		  idGenerator(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]));
			    		  
			    		  SIDCount += Math.abs(Integer.parseInt(tmp[0]) - Integer.parseInt(tmp[1])) + 1;
			    	  }	    		  
			      }
			      
			      for (String element: TPMArray) {
			    	  if(!element.contains("-"))
			    		  TPMcoma.add(element);
			    	  else {
			    		  String[] tmp = element.split("-");
			    		  TPMbar.add(tmp[0]);
			    		  TPMbar.add(tmp[1]);
			    	  }	    		  
			      }
			      
			      String comp = begView2.getListOne().getSelectedItem().toString();
			      String value = begView2.getTPMText().getText().trim();
			      value = value.replaceAll("[^,1234567890-]", "");
			      
			      String comp2 = begView2.getListTwo().getSelectedItem().toString();
			      String value2 = begView2.getTPMTextMax().getText().trim();
			      value2 = value2.replaceAll("[^,1234567890-]", "");
			      
			      SIDCount += SIDcoma.size();

			      for(int i =0; i < SIDbar.size(); i++) { 
			    	  
				      if(comp2 != "none" && value2.isEmpty() == false) {
				    	  q += "SID BETWEEN " + SIDbar.get(i) + " AND " + SIDbar.get(i + 1) + " AND " + type + " " + comp + " " + value + "  AND " + type + " " + comp2 + " " + value2 + " ";
				      }else {
				    	  q += "SID BETWEEN " + SIDbar.get(i) + " AND " + SIDbar.get(i + 1) + " AND " + type + " " + comp + " " + value + " ";
				      }
			    	  
					  i++;
					  if(i < SIDbar.size() - 1)
						  q += "OR ";
				  }
			      
			      if(SIDbar.size() > 0 && SIDcoma.size() > 0)
			    	  q += "OR ";
			      
				  for(int i =0; i < SIDcoma.size(); i++) {
					  
				      if(comp2 != "none" && value2.isEmpty() == false) {
						  if(i == SIDcoma.size() - 1 ) 
							  q += type + " " + comp + " " + value + " AND SID = " + SIDcoma.get(i) + " AND " + type + " " + comp2 + " " + value2 + " "; 
						  else 
							  q += type + " " + comp + " " + value + " AND SID = " + SIDcoma.get(i) + " AND " + type + " " + comp2 + " " + value2 + " OR ";
				      }else {					  
						  if(i == SIDcoma.size() - 1 ) 
							  q += type + " " + comp + " " + value +" AND SID = " + SIDcoma.get(i) + " "; 
						  else 
							  q += type + " " + comp + " " + value +" AND SID = " + SIDcoma.get(i) + " OR ";
				      }
				  }

				  ArrayList<String> grid = new ArrayList<String>();
				  				  
				  for(int i= 0; i < begView.getDtm().getRowCount(); i++) {
					  if(begView.getDtm().getValueAt(i, 0) != null && (boolean) begView.getDtm().getValueAt(i, 0) == true) {
						  if(!grid.contains((String)begView.getDtm().getValueAt(i, 1)))
							  grid.add((String) begView.getDtm().getValueAt(i, 1)); 
					  } 
				  }
				  
				  String q2 = "";
				  String q3 = "";
				  
				  if(begView2.getAll2().isSelected() == true) {						  
					  q2 = "SELECT distinct(gene_id) " + q + ";";
					  q3 = "SELECT gene_id, SID, " + type + " " + q + ";";
				  }else {
					  for (int i = 0; i < grid.size(); i++) {
						  if(i == 0)
							  q += ") AS tmp WHERE ";
						  
						  if(i == grid.size() - 1) 
							  q += "tmp.gene_id = \"" + grid.get(i) + "\"";
						  else
							  q += "tmp.gene_id = \"" + grid.get(i) + "\" OR ";   
					  }
					  					  
					  if(grid.size() == 0)
						  q += ") AS tmp";
					 
					  q += ";";
					  q2 = "SELECT distinct(tmp.gene_id) " + q;
					  q3 = "SELECT * " + q;
				  }
				  				  
				  //System.out.println(q2);
				  //System.out.println(q3);	
				  if(grid.size() != 0 && SIDs.size() != 0) {
					  Object[] options = {"Yes", "No"};
					  
					  int response = JOptionPane.showOptionDialog(null, "Are you sure you want to submit the query?", "Question", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					  if(response == 0) {
						  JFrame TopFrame = (JFrame) SwingUtilities.getWindowAncestor(begView2);
						  TopFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						  processing.setText("Processing...");
						  processing.setForeground(Color.red);
						  
						  ExecuteQuery worker2 = new ExecuteQuery(q2, q3, type, stmt, model, begView2, SIDs, processing, SIDCount);
						  worker2.execute();		  
					  }
				  }else {
					  JOptionPane.showMessageDialog(null, "The sample id and the gene or disease name can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
				  }
				  break;
				  }else {
					  JOptionPane.showMessageDialog(null, "The sample id and the gene or disease name can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
					  break;
				  }
				  
		  case "SelectAll":
			  selectAll(true);
			  break;
		  case "DeselectAll":
			  selectAll(false);
			  break;
		}
	}
	
	public void selectAll(boolean value) {
		if(model.getGeneData() != null) {
			for(int i =0; i < model.getGeneData().length; i++) {
				begView.getDtm().setValueAt(value, i, 0);
			}
		}
	}

	public Boolean getConnection() {
		return connection;
	}

	public void setConnection(Boolean connection) {
		this.connection = connection;
	}
	
	public void idGenerator (int a, int b) {
		if(b > a) {
			for(int i = a; i <= b; i++) {
				SIDs.add(Integer.toString(i));
			}
		}else if (a > b) {
			for(int i = b; i <= a; i++) {
				SIDs.add(Integer.toString(i));
			}
		}else {
			SIDs.add(Integer.toString(a));
		}		
	}
	
    Object[] getColumn(Object[][] matrix, int column) {
    	
    	Object[] tmp = new Object[matrix.length];
    	
    	for(int i =0; i < matrix.length; i++) {
    		tmp[i] = matrix[i][column];
    	}
    	
    	return tmp;
    }
    
    Object[] getDistinct(Object [] array) {
    	return Arrays.stream(array).distinct().toArray(Object[]::new);
    }

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub\
		
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}
}
