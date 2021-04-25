package elements;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MatriceIncidenceModel extends AbstractTableModel {
	private int [][] matriceInc;
	private ArrayList<Sommet> sommets;
	
	//--------- Constructeur ------------
	public MatriceIncidenceModel(int [][] matriceInc, ArrayList<Sommet> sommets)
	{
		this.matriceInc = matriceInc;
		this.sommets = sommets;
	}
	
	//--------- Getters ----------------
	public int[][] getMatriceInc() {
		return matriceInc;
	}

	public ArrayList<Sommet> getSommets() {
		return sommets;
	}
	
	//--------- Setters -----------------
	public void setMatriceInc(int[][] matriceInc) {
		this.matriceInc = matriceInc;
	}

	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}
	
	//-------- Fonctions ----------------
	@Override
	public int getRowCount() {
		return this.matriceInc.length;// + 1;
	}

	@Override
	public int getColumnCount() {
		return this.matriceInc.length + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return this.sommets.get(rowIndex).getNameSommet();
		return this.matriceInc[rowIndex][columnIndex-1];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		this.matriceInc[rowIndex][columnIndex] = (int) aValue;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) {
		if(columnIndex == 0)
			return String.class;
		return Integer.class;
	}
	
	@Override
	public String getColumnName(int column) {
		if(column == 0)
			return "Sommet";
		return this.sommets.get(column-1).getNameSommet();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
