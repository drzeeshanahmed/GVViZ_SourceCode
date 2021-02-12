import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTableModel() {
      super(new String[]{"", "", ""}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      Class clazz = String.class;
      switch (columnIndex) {
        case 0:
          clazz = Boolean.class;
          break;
        default:
            clazz = String.class;
            break;
      }
      return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return column == 0;
    }


  }


