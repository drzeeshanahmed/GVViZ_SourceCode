
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JMenu exportMenu;
	private JMenu helpMenu;
	JMenuBar menuBar;
	private JMenuItem saveItem;
	private JMenuItem saveJPEG;
	private JMenuItem closeItem;
	private JMenuItem connectItem;
	private JMenuItem helpItem;
	private JMenuItem about;
	private JMenuItem saveCSV;
	
	public MenuView () {
		fileMenu = new JMenu("File");
		exportMenu = new JMenu("Export");
	    helpMenu = new JMenu("Help");
		
	    ImageIcon exitIcon = new ImageIcon("src/resources/exit.png");

	    
	    closeItem = new JMenuItem("Exit", exitIcon);
	    closeItem.setActionCommand("Close");
	    
	    connectItem = new JMenuItem("Connect to the database");
	    connectItem.setActionCommand("Connect");
	    
	    fileMenu.add(connectItem);
	    fileMenu.addSeparator();
	    fileMenu.add(closeItem);
	    
	    saveItem = exportMenu.add("Save as PNG");
	    saveItem.setActionCommand("SavePNG");
	    
	    exportMenu.addSeparator();
	    
	    saveJPEG = exportMenu.add("Save as TIFF");
	    saveJPEG.setActionCommand("SaveJPEG");
	    
	    exportMenu.addSeparator();
	    
	    saveCSV = exportMenu.add("Save data as CSV");
	    saveCSV.setActionCommand("SaveCSV");
	    
	    helpItem = helpMenu.add("Contact Us");
	    helpItem.setActionCommand("Contact");
	    
	    helpMenu.addSeparator();
	    
	    about = helpMenu.add("About");
	    about.setActionCommand("AboutVersion");
	    
	    menuBar = new JMenuBar();
	    menuBar.add(fileMenu); 
	    menuBar.add(exportMenu);
	    menuBar.add(helpMenu);
	    
        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("mac")) {
    	    System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
	}

	public JMenu getFileMenu() {
		return fileMenu;
	}

	public void setFileMenu(JMenu fileMenu) {
		this.fileMenu = fileMenu;
	}

	public JMenuItem getsaveItem() {
		return saveItem;
	}

	public JMenuItem getSaveJPEG() {
		return saveJPEG;
	}

	public void setSaveTTF(JMenuItem saveJPEG) {
		this.saveJPEG = saveJPEG;
	}

	public JMenuItem getHelpItem() {
		return helpItem;
	}

	public void setHelpItem(JMenuItem helpItem) {
		this.helpItem = helpItem;
	}

	public void setsaveItem(JMenuItem saveItem) {
		this.saveItem = saveItem;
	}

	public JMenuItem getCloseItem() {
		return closeItem;
	}

	public void setCloseItem(JMenuItem closeItem) {
		this.closeItem = closeItem;
	}

	public JMenuItem getSaveCSV() {
		return saveCSV;
	}

	public void setSaveCSV(JMenuItem saveCSV) {
		this.saveCSV = saveCSV;
	}

	public JMenuItem getAbout() {
		return about;
	}

	public void setAbout(JMenuItem about) {
		this.about = about;
	}

	public JMenuItem getConnectItem() {
		return connectItem;
	}

	public void setConnectItem(JMenuItem connectItem) {
		this.connectItem = connectItem;
	}
}
