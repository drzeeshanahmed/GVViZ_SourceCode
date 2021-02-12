import java.awt.Color;
import java.awt.Cursor;
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

public class ExecuteQuery2 extends SwingWorker<Void, Void> {
	
	String query;
	int rows;
	int columns;
	String value;
	Statement stmt;
	Model model;
	BeginerView begView;
	ResultSetMetaData rsmd;
	JLabel processing;
	JLabel count;
	
	ExecuteQuery2(String query,  Statement stmt, Model model, BeginerView begView, JLabel processing, JLabel count) {
		this.query = query;
		this.stmt = stmt;
		this.model = model;
		this.begView = begView;
		this.processing = processing;
		this.count = count;
	}

	@Override
	protected Void doInBackground() {
		
		try {
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
	        
	        
			  model.setGeneData(resultSet);
			  
			String[] columNames = new String[columns];
			
			for(int i =1; i <= columns; i++) {
				//System.out.println(rsmd.getColumnName(i));
				columNames[i - 1] = rsmd.getColumnName(i);
			}
			  
			  model.setGeneColumns(columNames);
			  model.setGscolums(Arrays.asList(model.getGeneColumns()));
			  
			  String[] a = model.getGeneColumns();
			  String[] b = new String[1];
			  b[0] = "Include it";
			  
			  String[] both = Stream.concat(Arrays.stream(b), Arrays.stream(a)).toArray(String[]::new);
			 
			  if(model.getGeneColumns().length >= 4) {
				both[1] = "Ensembl Id";
				both[2] = "Gene Name";
			  	both[3] = "Category";
			  	both[4] = "Disease";
			  } else {
				  both[1] = "Gene Id";
			  }
			  
			  
			  begView.getDtm().setColumnIdentifiers(both);
			  begView.getDtm().setRowCount(rows);
			  begView.getDtm().setColumnCount(columns + 1);
			  
			  count.setText("Number of Records Found: " + rows);
			  
			  Hashtable<String, String> ENSG = new Hashtable<String, String>();
			  
			  String conversion = "";
			 
			  for(int i =0; i < rows; i++) {
				for(int j =0; j < columns; j++) {
					begView.getDtm().setValueAt(model.getGeneData()[i][j], i, j + 1); 
					if(j > 0)
						conversion += model.getGeneData()[i][j] + ":"; 
				}
				
				if(columns > 1) {
					//System.out.println(model.getGeneData()[i][0] + " " +  conversion);
					ENSG.put((String) model.getGeneData()[i][0], conversion); 
					conversion = ""; 
				}
				
				begView.getDtm().setValueAt(Boolean.FALSE, 0, 0);
			  }
			  
			  model.setENSG(ENSG);
			
			//System.out.println("Got the results");
		} catch (SQLException e1) { 
			  JTextArea ta = new JTextArea(2, 30);
			  ta.setText(e1.toString()); ta.setEditable(false);
			  JOptionPane.showMessageDialog(null, new JScrollPane(ta),"Error in SQL Query", JOptionPane.ERROR_MESSAGE); 
		}
		return null; 
	}
	
	@Override
    public void done() {
		JFrame TopFrame = (JFrame) SwingUtilities.getWindowAncestor(begView);
		TopFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		processing.setText("Available...");
		processing.setForeground(Color.green);
	}
}
