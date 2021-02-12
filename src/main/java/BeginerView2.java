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
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 * @author eduard
 *
 */
public class BeginerView2 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton Search = null;

	JButton SelectAll = null;
	JButton DeselectAll = null;
    
	JTextField TPMTextMin = null;
	JTextField TPMTextMax = null;
	JTextField SIDText = null;
	JTextField SearchText = null;
	
	JLabel TPM = null;
	JLabel SID = null;
	JLabel Gene = null;
	JLabel limitName3 = null;
	
	JLabel SelectDB = null;
	JLabel SelectData = null;
	
	JLabel AND = null;
	
	JRadioButton coding;
	JRadioButton non_coding;
	JRadioButton all;
	JRadioButton all2;
	ButtonGroup group1;
	
	JRadioButton length_button;
	JRadioButton effective_length_button;
	JRadioButton expected_count_button;
	JRadioButton TPM_button;
	JRadioButton FPKM_button;
	ButtonGroup group2;
	
	JComboBox ListOne;
	JComboBox ListTwo;
	

	String[] compStringsFirst = { ">", "<", ">=", "<=", "=", "!="};
	String[] compStringsSecond = { "none", ">", "<", ">=", "<=", "=", "!="};
	
	JTable table;
	DefaultTableModel dtm;
	
	GridBagConstraints gbc;
	
	BeginerView2() {
		
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 1, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        
        this.setLayout(new GridBagLayout());

	    TPM = new JLabel("Transcripts Per Kilobase Million (TPM):");
	    SID = new JLabel("Select Sample IDs:");
	    Font f = SID.getFont();
	    SID.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
	    Gene = new JLabel("Gene or Disease Name:");
	    SelectDB = new JLabel("Select the Database:");
	    SelectDB.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

	    SelectData = new JLabel("Select Expression Data:");
	    SelectData.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
	    
	    AND = new JLabel("AND");
	    AND.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
	    
	    TPMTextMin = new JTextField(15);
	    TPMTextMax = new JTextField(15);
	    SIDText = new JTextField(15);
	    SearchText = new JTextField(15);

	    Search = new JButton("Search");
	    Search.setActionCommand("Search");
	    
	    length_button = new JRadioButton("Length");
	    length_button.setActionCommand("length");
	    
	    effective_length_button = new JRadioButton("Effective Length");
	    effective_length_button.setActionCommand("effective_length");
	    
	    expected_count_button = new JRadioButton("Expected Count");
	    expected_count_button.setActionCommand("expected_count");
	    
	    TPM_button = new JRadioButton("TPM");
	    TPM_button.setActionCommand("TPM");
	    
	    FPKM_button = new JRadioButton("FPKM");
	    FPKM_button.setActionCommand("FPKM");
	    
        coding = new JRadioButton("Protein Coding");
        non_coding = new JRadioButton("Non-Coding");
        all = new JRadioButton("Select All");
        all2 = new JRadioButton("Select All Samples");
        group1 = new ButtonGroup();
        
        all2.setForeground(Color.red);
        
        all.setSelected(true);
        
        group1.add(coding);
        group1.add(non_coding);
        group1.add(all);
        group1.add(all2);
	    
	    group2 = new ButtonGroup();
	    group2.add(length_button);
	    group2.add(effective_length_button);
	    group2.add(expected_count_button);
	    group2.add(TPM_button);
	    group2.add(FPKM_button);
	    
	    TPM_button.setSelected(true);
	    
	    ListOne = new JComboBox(compStringsFirst);
	    ListTwo = new JComboBox(compStringsSecond);
	    
	    this.add(SelectDB,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(Box.createVerticalStrut(5), gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(coding, gbc);
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(non_coding, gbc);
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(all, gbc);
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(all2, gbc);
		gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(Box.createVerticalStrut(30), gbc);
		gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(SelectData,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(Box.createVerticalStrut(5), gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(length_button,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(effective_length_button,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(expected_count_button,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(TPM_button,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(FPKM_button,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(ListOne);
	    gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(TPMTextMin,gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(Box.createVerticalStrut(5), gbc);
		this.add(AND);
		this.add(Box.createVerticalStrut(5), gbc);
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    this.add(ListTwo);
	    gbc.gridy = GridBagConstraints.RELATIVE;
		this.add(TPMTextMax,gbc);
		this.add(Box.createVerticalStrut(30), gbc);
		this.add(SID,gbc);
		this.add(Box.createVerticalStrut(5), gbc);
		this.add(SIDText,gbc);

	}

	public JButton getSearch() {
		return Search;
	}

	public void setSearch(JButton search) {
		Search = search;
	}

	public JTextField getTPMText() {
		return TPMTextMin;
	}

	public void setTPMText(JTextField tPMText) {
		TPMTextMin = tPMText;
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

	public ButtonGroup getGroup2() {
		return group2;
	}

	public void setGroup2(ButtonGroup group2) {
		this.group2 = group2;
	}

	public JRadioButton getCoding() {
		return coding;
	}

	public void setCoding(JRadioButton coding) {
		this.coding = coding;
	}

	public JRadioButton getAll() {
		return all;
	}

	public void setAll(JRadioButton all) {
		this.all = all;
	}

	public JRadioButton getAll2() {
		return all2;
	}

	public void setAll2(JRadioButton all2) {
		this.all2 = all2;
	}

	public JRadioButton getNon_coding() {
		return non_coding;
	}

	public void setNon_coding(JRadioButton non_coding) {
		this.non_coding = non_coding;
	}

	public JComboBox getListOne() {
		return ListOne;
	}

	public void setListOne(JComboBox listOne) {
		ListOne = listOne;
	}

	public JComboBox getListTwo() {
		return ListTwo;
	}

	public void setListTwo(JComboBox listTwo) {
		ListTwo = listTwo;
	}

	public JRadioButton getTPM_button() {
		return TPM_button;
	}

	public void setTPM_button(JRadioButton tPM_button) {
		TPM_button = tPM_button;
	}

	public JRadioButton getLength_button() {
		return length_button;
	}

	public void setLength_button(JRadioButton length_button) {
		this.length_button = length_button;
	}

	public JRadioButton getEffective_length_button() {
		return effective_length_button;
	}

	public void setEffective_length_button(JRadioButton effective_length_button) {
		this.effective_length_button = effective_length_button;
	}

	public JRadioButton getExpected_count_button() {
		return expected_count_button;
	}

	public void setExpected_count_button(JRadioButton expected_count_button) {
		this.expected_count_button = expected_count_button;
	}

	public JTextField getTPMTextMax() {
		return TPMTextMax;
	}

	public void setTPMTextMax(JTextField tPMTextMax) {
		TPMTextMax = tPMTextMax;
	}
}
