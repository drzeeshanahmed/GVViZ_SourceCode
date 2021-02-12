import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DBViewFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel dbhostname;
	private JLabel dbport;
	private JLabel dbuser;
	private JLabel dbpass;
	private JTextField dbporttext;
	private JTextField dbhosttext;
	private JTextField dbusertext;
	private JPasswordField dbpasstext;
	private JButton button;	
	
	
	public DBViewFrame () {
        super("GVViZ Database Settings");
		
        dbhostname = new JLabel("Hostname:");
        dbport = new JLabel("Port:");
        dbuser = new JLabel("Username:");
        dbpass = new JLabel("Password:");
        
        dbporttext = new JTextField(6);
        dbhosttext = new JTextField(15);
        dbusertext = new JTextField(15);
        dbpasstext = new JPasswordField(15);

        button = new JButton("Connect");
        button.setActionCommand("DBSubmit");
        
	    this.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        
        this.add(dbhostname,c);
        this.add(dbhosttext,c);
        this.add(dbport,c);
        this.add(dbporttext,c);
        this.add(dbuser,c);
        this.add(dbusertext,c);
        this.add(dbpass,c);
        this.add(dbpasstext,c);
        this.add(button,c);        
	}
	
	public JTextField getDbporttext() {
		return dbporttext;
	}

	public void setDbporttext(String dbporttext) {
		this.dbporttext.setText(dbporttext);
	}

	public JTextField getDbhosttext() {
		return dbhosttext;
	}

	public void setDbhosttext(String dbhosttext) {
		this.dbhosttext.setText(dbhosttext);
	}

	public JTextField getDbusertext() {
		return dbusertext;
	}

	public void setDbusertext(String dbusertext) {
		this.dbusertext.setText(dbusertext);
	}

	public JPasswordField getDbpasstext() {
		return dbpasstext;
	}

	public void setDbpasstext(String dbpasstext) {
		this.dbpasstext.setText(dbpasstext);
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}
}
