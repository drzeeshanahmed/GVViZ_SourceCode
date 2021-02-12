import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SqlView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTable table;
	JTextArea areaText;
	JTableHeader header;
	JButton submit;
	DefaultTableModel dtm;
	JScrollPane scrollpane;

	
	SqlView() {
		table = new JTable();
		areaText = new JTextArea(10,80);
		areaText.setText("Insert you SQL query here...");
		submit = new JButton("Submit Query");
		submit.setActionCommand("SQLSubmit");
		scrollpane = new JScrollPane(areaText);
		
		//this.add(scrollpane);
		
		dtm = new DefaultTableModel(0,0);
		table.setModel(dtm);
		
		//dtm.setValueAt(aValue, row, column);
		
	    this.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 1, 0, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

		c.fill = GridBagConstraints.BOTH;
		c.gridy = GridBagConstraints.RELATIVE;
	    c.weightx = 1;
	    c.weighty = 1;
	    this.add(scrollpane,c);
	    c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 0;
		c.weighty = 0;
	    this.add(submit,c);
	    c.gridy = GridBagConstraints.RELATIVE;
	    
	    this.add(new JScrollPane(table),c);
	    		
		header = table.getTableHeader();
	}

	public JTextArea getAreaText() {
		return areaText;
	}

	public void setAreaText(JTextArea areaText) {
		this.areaText = areaText;
	}

	public JButton getSubmit() {
		return submit;
	}

	public void setSubmit(JButton submit) {
		this.submit = submit;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTableHeader getHeader() {
		return header;
	}

	public void setHeader(JTableHeader header) {
		this.header = header;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
	
}
