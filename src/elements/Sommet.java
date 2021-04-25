package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;


public class Sommet{// implements Comparator<Sommet>{
	
	private int x;
	private int y;
	private int rayon;
	private String nameSommet;
	private Color background;
	private Color foreground;
	private boolean algo;
	//private boolean chooseColor;
	private boolean selected;
	public ArrayList<Sommet> sommetEntrant = new ArrayList<Sommet>();
	public ArrayList<Sommet> sommetSortant = new ArrayList<Sommet>();
	private int degree = 0;
	private int parent = 0;

	//---------- Constructeur ------------
	public Sommet(int x, int y, int rayon, String nameSommet, Color background) {
		super();
		this.x = x;
		this.y = y;
		this.rayon = rayon;
		this.nameSommet = nameSommet;
		this.background = background;
	}
	
	//---------- Constructeur ------------
	public Sommet(Sommet s)
	{
		this.x = s.x;
		this.y = s.y;
		this.rayon = s.rayon;
		this.nameSommet = s.nameSommet;
	}
	
	public boolean isEqual(Sommet s)
	{
		if(this.x == s.x && this.y == s.y && this.nameSommet == s.nameSommet)
			return true;
		else
			return false;
	}
	
	//-------- Getters ------------
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDiametre() {
		return rayon;
	}
	
	public String getNameSommet() {
		return nameSommet;
	}

	public Color getBackground() {
		return background;
	}

	public Color getForeground() {
		return foreground;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean isAlgo()
	{
		return algo;
	}
	
	public int getDegree()
	{
		int d = 0;
		if(InfosGraph.simple)
			d = this.sommetSortant.size();
		if(InfosGraph.oriente)
			d = (this.sommetEntrant.size() + this.sommetSortant.size());
		return d;
	}
	
	public int getNbreSommetEnt()
	{
		return this.sommetEntrant.size();
	}
	
	public int getNbreSommetSort()
	{
		return this.sommetSortant.size();
	}
	
	public int getParent()
	{
		return this.parent;
	}
	
	public void setParent(int parent)
	{
		this.parent = parent;
	}
	
	public String getEntr()
	{
		String names = "";
		for(Sommet s : sommetEntrant)
			if(s==sommetEntrant.get(0))
			{
				names += s.getNameSommet();
			}
			else
			{ names += " - " + s.getNameSommet(); }
		return names;
	}
	
	public String getSort()
	{
		String names = "";
		for(Sommet s : sommetSortant)
		{
			if(s==sommetSortant.get(0))
			{
				names += s.getNameSommet();
			}
			else
			{names += " - " + s.getNameSommet();}
		}
		return names;
	}
	
	public ArrayList<Sommet> getSomEntr()
	{
		return this.sommetEntrant;
	}
	
	public ArrayList<Sommet> getSomSort()
	{
		return this.sommetSortant;
	}
	
	public Rectangle getBounds(){
        return new Rectangle(x-rayon, y-rayon, rayon*2, rayon*2);
    }

	
	//-------- Setters ------------
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setDiametre(int diametre)
	{
		this.rayon = diametre;
	}

	public void setNameSommet(String nameSommet) {
		this.nameSommet = nameSommet;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setAlgo(boolean algo)
	{
		this.algo = algo;
	}
	
	public void setDegree(int degree)
	{
		this.degree = degree;
	}
	
	//-------- ajouter sommet entrant ---------
	public void addSommetEnt(Sommet s)
	{
		for(Sommet sm : sommetEntrant)
		{
			if(!sm.isEqual(s))
			{
				sommetEntrant.add(s);
			}
		}
	}
	
	//-------- supprimer sommet entrant -------
	public void removeSommetEnt(Sommet s)
	{
		this.sommetEntrant.remove(s);
	}
	
	//-------- ajouter sommet sortant ---------
	public void addSommetSort(Sommet s)
	{
		for(Sommet sm : sommetEntrant)
		{
			if(!sm.isEqual(s))
			{
				sommetSortant.add(s);
			}
		}
	}
	
	//------- supprimer sommet sortant --------
	public void removeSommetSort(Sommet s)
	{
		this.removeSommetSort(s);
	}
	
	
	//--------- dessiner sommet ---------------
	public void dessinerSommet(Graphics2D s)
	{
		s.setColor(background);
		s.fillOval(x-rayon, y-rayon, rayon*2, rayon*2);
		s.drawString(nameSommet, x-rayon/2, y+rayon/4);
		if(selected)
		{
            s.setColor(Color.orange);
            s.fillOval(x-rayon, y-rayon, rayon*2, rayon*2);
        }
		if(algo)
		{
			s.setColor(Color.magenta);
			s.fillOval(x-rayon, y-rayon, rayon*2, rayon*2);
		}
		if(InfosGraph.chooseColorSommet)
		{
			s.setColor(background);
			s.fillOval(x-rayon, y-rayon, rayon*2, rayon*2);
		}
        s.setColor(Color.white);
        int labelWidth = s.getFontMetrics().stringWidth(nameSommet);
        int labelHeight = s.getFontMetrics().getHeight();
        s.drawString(nameSommet, x-labelWidth/2, y+labelHeight/4);
	}

	
}
