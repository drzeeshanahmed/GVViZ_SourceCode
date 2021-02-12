import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class Model {

	 private Object[][] data;
	 private Object[][] GeneData;
	 private double[][] renderData;
	 private String[] columns;
	 private String[] GeneColumns;
	 private List<String> hscolums;
	 private List<String> gscolums;
	 private Object[] xcolumns;
	 private Object[] ycolumns;
	 private Object[] ycolumnLeft;
	 private Object[] ycolumnRight;
	 private Object[] zcolumns;
	 private boolean xFocus;
	 private boolean yFocus;
	 private boolean zFocus;
	 private String xValueString;
	 private String yValueString;
	 private String zValueString;
	 private int xValue;
	 private int yValue;
	 private int zValue;
	 private BufferedImage img;
	 private String[] dbs;
	 private String[] tables;
	 private String[] beginerColumns;
	 private Hashtable<String, Integer> h;
	 private Hashtable<String, String> ENSG;
	 private String [] gene;
	 private String [] SID;
	 private String xTitle;
	 private DBViewFrame frame;
	 private Double min;
	 private Double max;
	 private String [] geneName;
	 private String [] diseaseName;
	 private boolean updated = true;

	public Model() {
		 
      this.data = new Object[][]{{3,2,3,4,5,6},
            {2,3,4,5,6,7},
            {3,4,5,6,7,6},
            {4,5,6,7,6,5}};
		 
	 }

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public boolean isxFocus() {
		return xFocus;
	}

	public void setxFocus(boolean xFocus) {
		this.xFocus = xFocus;
	}

	public boolean isyFocus() {
		return yFocus;
	}

	public void setyFocus(boolean yFocus) {
		this.yFocus = yFocus;
	}

	public String getxValueString() {
		return xValueString;
	}

	public void setxValueString(String xValueString) {
		this.xValueString = xValueString;
	}

	public String getyValueString() {
		return yValueString;
	}

	public void setyValueString(String yValueString) {
		this.yValueString = yValueString;
	}

	public int getxValue() {
		return xValue;
	}

	public void setxValue(int xValue) {
		this.xValue = xValue;
	}

	public int getyValue() {
		return yValue;
	}

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}

	public double[][] getRenderData() {
		return renderData;
	}

	public void setRenderData(double[][] data2) {
		this.renderData = data2;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public Object[] getXcolumns() {
		return xcolumns;
	}

	public void setXcolumns(Object[] xcolumns) {
		this.xcolumns = xcolumns;
	}

	public Object[] getYcolumns() {
		return ycolumns;
	}

	public void setYcolumns(Object[] ycolumns) {
		this.ycolumns = ycolumns;
	}

	public Object[] getZcolumns() {
		return zcolumns;
	}

	public void setZcolumns(Object[] zcolumns) {
		this.zcolumns = zcolumns;
	}

	public boolean iszFocus() {
		return zFocus;
	}

	public void setzFocus(boolean zFocus) {
		this.zFocus = zFocus;
	}

	public String getzValueString() {
		return zValueString;
	}

	public void setzValueString(String zValueString) {
		this.zValueString = zValueString;
	}

	public int getzValue() {
		return zValue;
	}

	public void setzValue(int zValue) {
		this.zValue = zValue;
	}

	public List<String> getHscolums() {
		return hscolums;
	}

	public void setHscolums(List<String> hscolums) {
		this.hscolums = hscolums;
	}

	public String[] getDbs() {
		return dbs;
	}

	public void setDbs(String[] dbs) {
		this.dbs = dbs;
	}

	public String[] getTables() {
		return tables;
	}

	public void setTables(String[] tables) {
		this.tables = tables;
	}

	public String[] getBeginerColumns() {
		return beginerColumns;
	}

	public void setBeginerColumns(String[] beginerColumns) {
		this.beginerColumns = beginerColumns;
	}

	public Object[][] getGeneData() {
		return GeneData;
	}

	public void setGeneData(Object[][] geneData) {
		GeneData = geneData;
	}

	public String[] getGeneColumns() {
		return GeneColumns;
	}

	public void setGeneColumns(String[] geneColumns) {
		GeneColumns = geneColumns;
	}

	public List<String> getGscolums() {
		return gscolums;
	}

	public void setGscolums(List<String> gscolums) {
		this.gscolums = gscolums;
	}

	public Hashtable<String, Integer> getH() {
		return h;
	}

	public void setH(Hashtable<String, Integer> h) {
		this.h = h;
	}

	public String[] getGene() {
		return gene;
	}

	public void setGene(String[] gene) {
		this.gene = gene;
	}

	public String[] getSID() {
		return SID;
	}

	public void setSID(String[] sID) {
		SID = sID;
	}
	
	public DBViewFrame getFrame() {
		return frame;
	}

	public void setFrame(DBViewFrame frame) {
		this.frame = frame;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Hashtable<String, String> getENSG() {
		return ENSG;
	}

	public void setENSG(Hashtable<String, String> eNSG) {
		ENSG = eNSG;
	}

	public String[] getGeneName() {
		return geneName;
	}

	public void setGeneName(String[] geneName) {
		this.geneName = geneName;
	}

	public String[] getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String[] diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Object[] getYcolumnLeft() {
		return ycolumnLeft;
	}

	public void setYcolumnLeft(Object[] ycolumnLeft) {
		this.ycolumnLeft = ycolumnLeft;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public Object[] getYcolumnRight() {
		return ycolumnRight;
	}

	public void setYcolumnRight(Object[] ycolumnRight) {
		this.ycolumnRight = ycolumnRight;
	}
}
