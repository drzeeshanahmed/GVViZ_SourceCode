import java.awt.Color;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ExecuteQuery extends SwingWorker<Void, Void> {
	
	String query1;
	String query2;
	int rows;
	int columns;
	String value;
	Statement stmt;
	Model model;
	BeginerView2 begView2;
	ResultSetMetaData rsmd;
	List<String> SIDs;
	JLabel processing;
	int SIDCount;
	
	ExecuteQuery(String query1, String query2, String value, Statement stmt, Model model, BeginerView2 begView2, List<String> SIDs, JLabel processing, int SIDCount) {
		this.query1 = query1;
		this.query2 = query2;
		this.value = value;
		this.stmt = stmt;
		this.model = model;
		this.begView2 = begView2;
		this.SIDs = SIDs;
		this.processing = processing;
		this.SIDCount = SIDCount;
	}

	@Override
	protected Void doInBackground() {
		
		try {
		
		ResultSet rs = stmt.executeQuery(query1);
		rsmd=rs.getMetaData();  


		int columns2 = rs.getMetaData().getColumnCount();
		rs.last(); 
		int rows2 = rs.getRow();		
		rs.beforeFirst();		
		
		Object[][] resultSet = new Object[rows2][columns2];
        int row = 0;
        while (rs.next()) {
            for (int i = 0; i < columns2; i++) {
                resultSet[row][i] = rs.getObject(i+1);
            }
            row++;
        }
		
		  String sgrid[] = new String[resultSet.length]; 
	
		  for(int j =0;j<resultSet.length;j++){
			 sgrid[j] = resultSet[j][0].toString();
		  }
		  					  
		  String sArray[] = new String[SIDs.size()]; 
		  for(int j =0;j<SIDs.size();j++){
			  sArray[j] = SIDs.get(j);
		  }
		  							  
		  ArrayList<String> names = new ArrayList<String>();
		  names.add("gene_id");
		  names.add("SID");
		  names.add("TPM");
		  
		  String[] n = {"gene_id", "SID", "TPM"};
		  
		  
		  rows = sArray.length * sgrid.length;
		  columns = 3;
		  
		rs = stmt.executeQuery(query2);
		Hashtable<String, Integer> h = new Hashtable<String, Integer>(); 
		Object[][] data = new Object[rows][columns];
		
		int counter = 0;
		
	    String[] geneName = new String[sgrid.length];
	    String[] diseaseName = new String[sgrid.length];
		
		for (int i =0; i < sArray.length; i++) {
			for (int j =0; j < sgrid.length; j++) {
				h.put(sArray[i] + " " + sgrid[j], counter);
				data[counter][0] = sgrid[j];
				data[counter][1] = sArray[i];
				data[counter][2] = 0;
				counter += 1;
				
				 if(begView2.getAll2().isSelected() == false) {
					 					 
					  String v = model.getENSG().get(sgrid[j].toString());
					  geneName[j] = v.split(":")[0];
					  					  
					  String longName = v.split(":")[2];
					  if(longName.split("_").length > 4) {
						  diseaseName[j] = longName.split("_")[0] + "_";
					      diseaseName[j] += longName.split("_")[1] + "_";
					  	  diseaseName[j] += longName.split("_")[2] + "_";
					  	  diseaseName[j] += longName.split("_")[3] + "_";
					  	  diseaseName[j] += "...";
					  }else
						  diseaseName[j] = v.split(":")[2];
				 }
			}
		}
		
		if(begView2.getAll2().isSelected() == false) {
			model.setGeneName(geneName);
			model.setDiseaseName(diseaseName);
		}
				
		model.setH(h);
		model.setSID(sArray);
		model.setGene(sgrid);		
		
        while (rs.next()) {
        	String gid = rs.getString("gene_id");
        	String sid = rs.getString("SID");
        	double tpmid = rs.getDouble(value);
        	        	
        	data[h.get(sid + " " + gid)][0] = gid;
        	data[h.get(sid + " " + gid)][1] = sid;
        	data[h.get(sid + " " + gid)][2] = tpmid;
        }
		System.out.println("Done");
		
		  model.setData(data); 
		  model.setColumns(n);
		  model.setHscolums(names);
		  
		 model.setxValue(SIDCount);
		 model.setyValue(sgrid.length);
		} catch (SQLException e1) { 
			  JTextArea ta = new JTextArea(2, 30);
			  ta.setText(e1.toString()); ta.setEditable(false);
			  JOptionPane.showMessageDialog(null, new JScrollPane(ta),"Error in SQL Query", JOptionPane.ERROR_MESSAGE); 
		}
		return null; 
	}
	
	@Override
    public void done() {
		JFrame TopFrame = (JFrame) SwingUtilities.getWindowAncestor(begView2);
		TopFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		processing.setText("Available...");
		processing.setForeground(Color.green);
	}
}
