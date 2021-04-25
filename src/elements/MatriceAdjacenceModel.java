package elements;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MatriceAdjacenceModel extends AbstractTableModel {
	private int [][] matriceAdj;
	private ArrayList<Sommet> sommets;
	
	//--------- Constructeur ---
	public MatriceAdjacenceModel(int [][] matriceAdj, ArrayList<Sommet> sommets)
	{
		this.matriceAdj = matriceAdj;
		this.sommets = sommets;
	}
	
	//--------- Getters --------
	public int[][] getMatriceAdj() {
		return matriceAdj;
	}

	public ArrayList<Sommet> getSommets() {
		return sommets;
	}
	
	//--------- Setters --------
	public void setMatriceAdj(int[][] matriceAdj) {
		this.matriceAdj = matriceAdj;
	}

	public void setSommets(ArrayList<Sommet> sommets) {
		this.sommets = sommets;
	}
	
	// --------- Fonctions --------
	@Override
	public int getRowCount() {
		return this.matriceAdj.length;// + 1;
	}

	@Override
	public int getColumnCount() {
		return this.matriceAdj.length + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return this.sommets.get(rowIndex).getNameSommet();
		return this.matriceAdj[rowIndex][columnIndex-1];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
	{
		this.matriceAdj[rowIndex][columnIndex] = (int) aValue;
	}
	
	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) 
	{
		if(columnIndex == 0)
			return String.class;
		return Integer.class;
	}
	
	@Override
	public String getColumnName(int column) 
	{
		if(column == 0)
			return "Sommet";
		return this.sommets.get(column-1).getNameSommet();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		return false;
	}

}
