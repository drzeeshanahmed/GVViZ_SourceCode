import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class LoadingScreen extends SwingWorker<Object, Object> {
	
	JWindow window;
	JLabel label;
	
	LoadingScreen() {
		window = new JWindow();
		label = new JLabel("Loading", SwingConstants.CENTER);
	    window.getContentPane().add(label);
	    window.setBounds(500, 150, 300, 200);
	    window.setVisible(false);
	}

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
