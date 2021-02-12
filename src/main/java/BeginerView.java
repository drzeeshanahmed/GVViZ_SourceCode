import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 * @author eduard
 *
 */
public class BeginerView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton Search = null;
	JButton Submit = null;
	JButton SelectAll = null;
	JButton DeselectAll = null;
    
	JTextField TPMText = null;
	JTextField SIDText = null;
	JTextField SearchText = null;
	
	JLabel TPM = null;
	JLabel SID = null;
	JLabel Gene = null;
	JLabel limitName3 = null;
	
	JButton SubmitQuery = null;
	
	JTable table;
	DefaultTableModel dtm;
	
	GridBagConstraints gbc;
	
	BeginerView() {
	
        this.setLayout(new GridBagLayout());
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 1, 0, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        

        
        Gene = new JLabel("Serarch Gene or Disease Name:");

	    SearchText = new JTextField(15);
	    SearchText.setFont(SearchText.getFont().deriveFont(17f));

	    Search = new JButton("Search");
	    Search.setActionCommand("Search");
	    Search.setFont(Search.getFont().deriveFont(17f));
	    
	    SelectAll = new JButton("Select All");
	    SelectAll.setActionCommand("SelectAll");
	    
	    DeselectAll = new JButton("Deselect All");
	    DeselectAll.setActionCommand("DeselectAll");
	    
	    SubmitQuery = new JButton("Submit Query");
	    SubmitQuery.setActionCommand("SubmitBeg");
	    SubmitQuery.setFont(SubmitQuery.getFont().deriveFont(17f));
	    
	    table = new JTable();
		dtm = new MyTableModel();//new DefaultTableModel(0,0);
		table.setModel(dtm);  
        JScrollPane tableSP = new JScrollPane(table);
		//tableSP.setPreferredSize(new Dimension(1500,700));
		
        gbc.anchor = GridBagConstraints.CENTER;
		this.add(Gene,gbc);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(SearchText,gbc);
		this.add(Search,gbc);
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(tableSP,gbc);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		this.add(SelectAll,gbc);
		this.add(DeselectAll, gbc);
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(SubmitQuery, gbc);
	}

	public JButton getSearch() {
		return Search;
	}

	public void setSearch(JButton search) {
		Search = search;
	}

	public JButton getSubmit() {
		return Submit;
	}

	public void setSubmit(JButton submit) {
		Submit = submit;
	}

	public JTextField getTPMText() {
		return TPMText;
	}

	public void setTPMText(JTextField tPMText) {
		TPMText = tPMText;
	}

	public JTextField getSIDText() {
		return SIDText;
	}

	public void setSIDText(JTextField sIDText) {
		SIDText = sIDText;
	}

	public JTextField getSearchText() {
		return SearchText;
	}

	public void setSearchText(JTextField searchText) {
		SearchText = searchText;
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}

	public JButton getSelectAll() {
		return SelectAll;
	}

	public void setSelectAll(JButton selectAll) {
		SelectAll = selectAll;
	}

	public JButton getDeselectAll() {
		return DeselectAll;
	}

	public void setDeselectAll(JButton deselectAll) {
		DeselectAll = deselectAll;
	}

	public JButton getSubmitQuery() {
		return SubmitQuery;
	}

	public void setSubmitQuery(JButton submitQuery) {
		SubmitQuery = submitQuery;
	}
}
