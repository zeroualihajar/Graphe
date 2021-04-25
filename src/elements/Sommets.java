package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import algorithmes.DegreeCompare;
import Interface.InfoSommet;
import Interface.Principal;

public class Sommets extends JPanel implements MouseMotionListener, MouseListener 
{
	
	private ArrayList<Sommet> sommets = new ArrayList<Sommet>();         //----- les sommets
	private ArrayList<String> sommetsNames = new ArrayList<String>();	//----- les noms des Sommets
	private ArrayList<Arete> aretes = new ArrayList<Arete>();	       //----- les arêtes
	private ArrayList<Double> aretesCouts = new ArrayList<Double>();  //----- les cotês des arêtes pour un graphe pondéré
	private ArrayList<Arete> toRemove = new ArrayList<Arete>();
	private ArrayList<Arete> aretesWareshall = new ArrayList<Arete>();
	private int[][] matriceAdj;
	private int[][] matriceInc;
	private String[][] matriceArc;
	private int[][] degrees;
	private ArrayList<Integer> degSommets;
	
	private Sommet selected;
	
	private JPopupMenu pop;
	private JPopupMenu popgraph;
	
	private JMenuItem information;
	private JMenuItem renommer;
	private JMenuItem supprimer;
	private JMenuItem stopSelected;
	private JMenuItem changeColor;
	
	public Sommets(Sommets copie)
	{
		this.sommets = copie.sommets;
		this.aretes = copie.aretes;
	}
	
	public Sommets()
	{
		super();
		//--------- background de la JscrollPane des Sommets ------
		this.setBackground(new Color(222, 245, 255));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		pop = new JPopupMenu();
		popgraph = new JPopupMenu();
		
		//--------- informations -----------
		ImageIcon iconInfos = new ImageIcon("src/icon/info.png");
		Image imgf = iconInfos.getImage();
		information = new JMenuItem("Information");
		information.setIcon(iconInfos);
		
		information.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				InfoSommet infosDialog = new InfoSommet(selected, sommets.size());
				pop.setVisible(false);
				unselected();
				infosDialog.setVisible(true);
			}
		});
		
		//--------- Renommer -----------
		renommer = new JMenuItem("Renommer");
		ImageIcon iconRename = new ImageIcon("src/icon/rename.png");
		Image imgR = iconRename.getImage();
		renommer.setIcon(iconRename);
		renommer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				pop.setVisible(false);
				String retour = JOptionPane.showInputDialog("Saisir le nouveau nom du sommet : ");
				while(sommetsNames.contains(retour))
				{
					retour = JOptionPane.showInputDialog("Un sommet de même nom existe déjà, Resaisir un autre nom : ");
				}
				while(retour.trim().isBlank())
				{
					retour = JOptionPane.showInputDialog("Il est nécesssaire de saisir un nom pour le sommet ! ");
				}
				while(sommetsNames.contains(retour))
				{
					retour = JOptionPane.showInputDialog("Un sommet de même nom existe déjà, Resaisir un autre nom : ");
				}
				selected.setNameSommet(retour);
				repaint();
				sommetsNames.remove(selected.getNameSommet());
				sommetsNames.add(retour);
				unselected();
			}
		});
		
		//---------  supprimer -----------
		ImageIcon iconDelete = new ImageIcon("src/icon/delete.png");
		Image imgS = iconDelete.getImage();
		supprimer = new JMenuItem("Supprimer");
		supprimer.setIcon(iconDelete);
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				pop.setVisible(false);
				int retour = JOptionPane.showConfirmDialog(pop, "Etes-vous sûr de vouloir supprimer ce sommet ?!", "Attention !",JOptionPane.OK_CANCEL_OPTION);
				if(retour == JOptionPane.OK_OPTION)
				{
					for(Arete a : aretes)
					{
						if(a.getSommet1().isEqual(selected) || a.getSommet2().isEqual(selected))
							toRemove.add(a);
					}
					sommets.remove(selected);
					sommetsNames.remove(selected.getNameSommet());
					aretes.removeAll(toRemove);
					Principal.textArc.setText(Integer.toString(aretes.size()));
					Principal.textSommet.setText(Integer.toString(sommets.size()));
				}
				unselected();
				repaint();
			}
		});
		
		//--------- arreter la selection -----------
		stopSelected = new JMenuItem("Arrêter la sélection");
		ImageIcon iconStop = new ImageIcon("src/icon/stop.png");
		stopSelected.setIcon(iconStop);
		stopSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pop.setVisible(false);
				unselected();
				repaint();
			}
		});
		
		pop.add(information);
		pop.add(renommer);
		pop.add(supprimer);
		pop.add(stopSelected);
	}
	
	public void clearPanel()
	{
		aretes.removeAll(aretes);
		sommets.removeAll(sommets);
		repaint();
	}
	
	//-------- Getters ---------------
	public ArrayList<Sommet> getSommets()
	{
		return this.sommets;
	}
	
	public String getSommetsNames()
	{
		String st = "";
		for(Sommet s : sommets)
			st += s.getNameSommet() + "  ";
		return st;
	}
	
	public ArrayList<Arete> getAretes()
	{
		return this.aretes;
	}
	
	public String getAretesNames()
	{
		String st = "";
		for(Arete a : aretes)
			st += a.getSommet1().getNameSommet() + "-" + a.getSommet2().getNameSommet() + " ";
		return st;
	}
	
	public ArrayList<Arete> getAretesWareshall()
	{
		return this.aretesWareshall;
	}
	
	public Sommet getSelected()
	{
		return this.selected;
	}
	
	public Arete getArete(Sommet s1, Sommet s2)
	{
		if(InfosGraph.simple)
		{
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2) || a.getSommet1().isEqual(s2) && a.getSommet2().isEqual(s1))
					return a;
			}
		}
		if(InfosGraph.oriente)
		{
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2))
					return a;
			}
		}
		return null;
	}
	
	//--------- circuit Absorbant --------------
	public void circuit()
	{
		int i = 0;
		for(Arete a : aretes)
		{
			if(a.getCout() < 0)
				i +=1;
		}
		if(i == 0)
			InfosGraph.circuitAbsorbant = false;
		else
			InfosGraph.circuitAbsorbant = true;
	}
	
	public Sommet getSommetAtPosition(int x, int y)
	{
		for(Sommet s : sommets)
		{
			if(s.getBounds().contains(x, y))
				return s;
		}
		return null;
	}
	
	public int[][] getMatriceAdjacence()
	{
		matriceAdj = new int[sommets.size()][sommets.size()];
		for(Arete a : aretes)
		{
			if(InfosGraph.simple)
			{
				matriceAdj[sommets.indexOf(a.getSommet1())][sommets.indexOf(a.getSommet2())] = 1;
				matriceAdj[sommets.indexOf(a.getSommet2())][sommets.indexOf(a.getSommet1())] = 1;
			}
			if(InfosGraph.oriente)
			{
				matriceAdj[sommets.indexOf(a.getSommet1())][sommets.indexOf(a.getSommet2())] = 1;
			}
		}
		return this.matriceAdj;
	}
	
	public int[][] getMatriceCout(int [][] matrice)
	{
		int matriceCout[][] = new int[sommets.size()][sommets.size()];
		if(InfosGraph.pondere)
		{
			for(int i = 0; i < sommets.size(); i++)
			{
				for(int j = 0; j < sommets.size(); j++)
				{
					if(matrice[i][j] == 1)
						matriceCout[i][j] = (int)getArete(sommets.get(i), sommets.get(j)).getCout();
					else
						matriceCout[i][j] = 0;
				}
			}
		}
		return matriceCout;
	}
	
	public int[][] getMatriceIncidence()
	{
		matriceInc = new int[sommets.size()][sommets.size()];
		if(InfosGraph.oriente)
		{
			for(Arete a : aretes)
			{
				matriceInc[sommets.indexOf(a.getSommet1())][sommets.indexOf(a.getSommet2())] = 1;
				matriceInc[sommets.indexOf(a.getSommet2())][sommets.indexOf(a.getSommet1())] = -1;
			}
		}
		else
		{
			for(int[] ligne : matriceAdj)
			{
				for(int colonne : ligne)
				{
					colonne = 0;
				}
			}
		}
		return matriceInc;
	}
	
	public String[][] getMatriceArc()
	{
		matriceArc = new String[sommets.size()][sommets.size()];
		for(String[] ligne : matriceArc)
		{
			for(String colonne : ligne)
			{
				colonne = "0";
			}
		}
		for(Arete a : aretes)
		{
			if(InfosGraph.simple)
			{
				matriceArc[sommets.indexOf(a.getSommet1())][sommets.indexOf(a.getSommet2())] = a.getSommet1().getNameSommet() + "" + a.getSommet2().getNameSommet();
				matriceArc[sommets.indexOf(a.getSommet2())][sommets.indexOf(a.getSommet1())] = a.getSommet2().getNameSommet() + "" + a.getSommet1().getNameSommet();
			}
			if(InfosGraph.oriente)
			{
				matriceArc[sommets.indexOf(a.getSommet1())][sommets.indexOf(a.getSommet2())] = a.getSommet1().getNameSommet() + "" + a.getSommet2().getNameSommet();
			}
		}
		return this.matriceArc;
	}
	
	public int[][] getDegrees() 
	{
		degrees = new int[sommets.size()][1];
		for(int[] ligne : degrees)
		{
			for(int colonne : ligne)
			{
				colonne = sommets.get(colonne).getDegree();
			}
		}
		return degrees;
	}
	
	public ArrayList<Integer> getDegSommets()
	{
		degSommets = new ArrayList<Integer>();
		for(Sommet s : sommets)
		{
			degSommets.add(s.getDegree());
		}
		return this.degSommets;
	}
	
	//--------- Fonctions ------------------
	public void unselected()
	{
		if(selected != null)
		{
			selected.setSelected(false);
			selected = null;
			Principal.btnDFS.setEnabled(false);
			Principal.btnBFS.setEnabled(false);
			Principal.btnDijkstra.setEnabled(false);
			Principal.btnBellmanford.setEnabled(false);
		}
	}

	public void isComplet()
	{
		int n = (sommets.size() * (sommets.size() - 1)) / 2; // Relation entre les sommets et les ar�tes dans le cas d'un graphe non orient�
		
		if(InfosGraph.simple)
		{
			if(aretes.size() >= n)
			{
				InfosGraph.complet = true;
			}
			else
			{
				InfosGraph.complet = false;
			}
		}
		if(InfosGraph.oriente)
		{
			if(aretes.size() >= 2*n)
			{
				InfosGraph.complet = true;
			}
			else
			{
				InfosGraph.complet = false;
			}
		}
	}
	
	public void isEulerien()
	{
		ArrayList<Integer> impair = new ArrayList<Integer>();
		for(int i : this.getDegSommets())
		{
			if(i % 2 != 0)
			{
				impair.add(i);
			}
		}
		//--------- size() == 0----------------
		if(impair.isEmpty())
		{
			InfosGraph.chaineEulerienne = true;
			InfosGraph.eulerien = true;
		}
		if(impair.size() == 2)
		{
			InfosGraph.chaineEulerienne = true;
			InfosGraph.eulerien = false;
		}
		else
		{
			InfosGraph.chaineEulerienne = false;
			InfosGraph.eulerien = false;
		}
	}
	
	public void isRegulier()
	{
		ArrayList<Integer> reg = new ArrayList<Integer>();
		int d = this.getDegSommets().get(0);
		for(Sommet s : sommets)
		{
			if(s.getDegree() == d)
				reg.add(s.getDegree());
		}
		if(reg.size() == sommets.size())
			InfosGraph.regulier = true;
		else
			InfosGraph.regulier = false;
	}
	
	
	public String liste()
	{
		String liste = "";
		for(Sommet s : sommets)
		{
			liste += " " + s.getNameSommet() +" - " + s.getSort() + "  =|" + "\n";
		}
		return liste;
	}	
	
	public ArrayList<Sommet> getVoisinsNonVisites(Sommet s, ArrayList<String> visites) {
        ArrayList<Sommet> liste = new ArrayList<>();
        int i = sommets.indexOf(s);
        int[][] m = this.getMatriceAdjacence();
        for (int j = 0; j < m[i].length; j++) {          
            if(m[i][j]==1 && i!=j && !visites.contains(sommets.get(j).getNameSommet()))liste.add(sommets.get(j));
        }
        Collections.sort(liste, new DegreeCompare());
        return liste;
    }
	
	//------- Chemin ----------------
	private ArrayList<Chemin> chemins;
    public ArrayList<Chemin> getChemins(Sommet depart, Sommet dest){
        chemins = new ArrayList<>();
        rec(depart,dest,dest,null);
        for (Iterator<Chemin> it = chemins.iterator(); it.hasNext();) {
            Chemin ch = it.next();
            if(ch.getSommets().get(0)!=depart)it.remove();
        }
        return chemins;
    }
    
    public void rec(Sommet depart, Sommet dest,Sommet last, Chemin c){
        ArrayList<Sommet> voisinsEntrants = last.getSomEntr();
        if((last==depart || voisinsEntrants.isEmpty()) && c!=null){
            if(c.getSommets().size()>0){
                double cout_ajoute = getArete(last, c.getSommets().get(0)).getCout();
                c.setCout(c.getCout()+cout_ajoute);
                c.getCouts().add(cout_ajoute);
            }
            c.addSommet(last);
            chemins.add(c);
        }
        else{
            if(c!=null && c.getSommets().contains(last))return;
            for(Sommet v:voisinsEntrants){
                Chemin new_c = new Chemin(c);
                if(new_c.getSommets().size()>0){
                    double cout_ajoute = getArete(last, c.getSommets().get(0)).getCout();
                    new_c.setCout(new_c.getCout()+cout_ajoute);
                    new_c.getCouts().add(cout_ajoute);
                }
                new_c.addSommet(last);
                rec(depart,dest,v,new_c);
            }
        }
    }
    //-------- Source -----------
    public Sommet getSource(){
        for (Sommet s : sommets) {
            if(s.getSomEntr().isEmpty())
            	return s;
        }
        return null;
    }
    //----------Puits ------------
    public Sommet getPuits(){
        for (Sommet s : sommets) {
            if(s.getSomSort().isEmpty())
            	return s;
        }
        return null;
    }
    
    public Arete addAreteResiduelle(Sommet s1, Sommet s2, Double epsilone)
    {
    	Arete a = new Arete(s1, s2, epsilone);
    	aretes.add(a);
    	return a;
    }
	// --------------------- Ajouter les elements du graphe --------------------- //
	public void addSommet(Sommet s)
	{
		this.sommets.add(s);
		this.repaint();
	}
	
	public void addArete(Sommet s1, Sommet s2)
	{
		
		if(InfosGraph.simple)
		{
			// -------- Suppression de l'arete s'ilexiste --------
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2) || a.getSommet1().isEqual(s2) && a.getSommet2().isEqual(s1))
				{
					int retour = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir supprimer cette arête ?!", "Attention !", JOptionPane.OK_CANCEL_OPTION);
					if(retour == JOptionPane.OK_OPTION)
					{
						aretes.remove(a);
						aretesCouts.removeIf(cout -> cout == a.getCout());
						unselected();
						repaint();
						Principal.textArc.setText(Integer.toString(aretes.size()));
						Principal.textDens.setText(Integer.toString(2*aretes.size()/(sommets.size()*(sommets.size()-1))));
						
						//------- supprimer voisins ------------
						a.getSommet1().sommetEntrant.remove(a.getSommet2());
						a.getSommet1().sommetSortant.remove(a.getSommet2());
						a.getSommet2().sommetEntrant.remove(a.getSommet1());
						a.getSommet2().sommetSortant.remove(a.getSommet1());
						return;
					}
					else if(retour == JOptionPane.CANCEL_OPTION)
					{
						return;
					}
				}
			}
			if(InfosGraph.pondere)
			{
				// -------- Ajouter arête s'elle n'existe pas --------
				
				try
				{
					String retour = JOptionPane.showInputDialog("Saisir le coût de cette arête : ");
					while(Double.parseDouble(retour) == 0 || retour == null)
					{
						retour = JOptionPane.showInputDialog("Un coût ne peut pas être null !! ");
					}
					aretes.add(new Arete(s1, s2, Double.parseDouble(retour)));
					aretesCouts.add(Double.parseDouble(retour));
					unselected();
					repaint();
					Principal.textArc.setText(Integer.toString(aretes.size()));
					Principal.textDens.setText(Integer.toString(2*aretes.size()/sommets.size()*(sommets.size()-1)));
					
					//------ ajouter des voisins -----------------------
					s1.sommetEntrant.add(s2);
					s1.sommetSortant.add(s2);
					s2.sommetEntrant.add(s1);
					s2.sommetSortant.add(s1);
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(this, "Attention! Impossible de Convertir en double !", "Conversion impossible !", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				aretes.add(new Arete(s1, s2));
				unselected();
				repaint();
				Principal.textArc.setText(Integer.toString(aretes.size()));
				Principal.textDens.setText(Integer.toString(2*aretes.size()/sommets.size()*(sommets.size()-1)));
				
				//------ ajouter des voisins -----------------------
				s1.sommetEntrant.add(s2);
				s1.sommetSortant.add(s2);
				s2.sommetEntrant.add(s1);
				s2.sommetSortant.add(s1);
			}
		}
		if(InfosGraph.oriente)
		{
			// -------- Suppression de l'arc s'ilexiste --------
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2))
				{
					int retour = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir supprimer cette arête ?!", "Attention !", JOptionPane.OK_CANCEL_OPTION);
					if(retour == JOptionPane.OK_OPTION)
					{
						aretes.remove(a);
						aretesCouts.removeIf(cout -> cout == a.getCout());
						unselected();
						repaint();
						// --- remove voisins ---
						a.getSommet1().sommetSortant.remove(a.getSommet2());
						a.getSommet2().sommetEntrant.remove(a.getSommet1());
						return;
					}
					else if(retour == JOptionPane.CANCEL_OPTION)
					{
						return;
					}
				}
			}
			// -------- Ajout de l'arc s'il n'existe pas --------
			if(InfosGraph.pondere)
			{
				try
				{
					String retour = JOptionPane.showInputDialog("Saisir le coût de cette arête : ");
					while(Double.parseDouble(retour) == 0 || retour == null)
					{
						retour = JOptionPane.showInputDialog("Une arête ne peut pas être null ! ");
					}
					aretes.add(new Arete(s1, s2, Double.parseDouble(retour)));
					aretesCouts.add(Double.parseDouble(retour));
					unselected();
					repaint();
					Principal.textArc.setText(Integer.toString(aretes.size()));
					//------- ajouter voisins ------------
					s1.sommetSortant.add(s2);
					s2.sommetEntrant.add(s1);
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(this, "Attention! Impossible de Convertir en double !", "Conversion impossible !", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				aretes.add(new Arete(s1, s2));
				unselected();
				repaint();
				Principal.textArc.setText(Integer.toString(aretes.size()));
				//------- ajouter voisins ------------
				s1.sommetSortant.add(s2);
				s2.sommetEntrant.add(s1);
			}
		}
	}
	
	public void addAreteWareshall(Sommet s1, Sommet s2)
	{
		InfosGraph.wareshall = true;
		if(InfosGraph.simple)
		{
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2) || a.getSommet1().isEqual(s2) && a.getSommet2().isEqual(s1))
					return;
				else
				{
					if(InfosGraph.pondere)
					{
						aretesWareshall.add(new Arete(s1, s2, 0.0));
						repaint();
					}
					else
					{
						aretesWareshall.add(new Arete(s1, s2));
						repaint();
					}
				}
			}
		}
		if(InfosGraph.oriente)
		{
			for(Arete a : aretes)
			{
				if(a.getSommet1().isEqual(s1) && a.getSommet2().isEqual(s2))
					return;
				else
				{
					if(InfosGraph.pondere)
					{
						aretesWareshall.add(new Arete(s1, s2, 0.0));
						repaint();
					}
					else
					{
						aretesWareshall.add(new Arete(s1, s2));
						repaint();
					}
				}
			}
		}
	}
	
	//----------- paint() ------------
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);
		for(Sommet s : sommets)
		{
			s.dessinerSommet(g2d);
		}
		for(Arete a : aretes)
		{
			a.dessinerArete(g2d);
		}
		for(Arete aw : aretesWareshall)
		{
			aw.dessinerWareshall(g2d);
		}
	}
	
	//---------- Action: mouse -------------
	@Override
	public void mouseClicked(MouseEvent e) {
		Sommet s = this.getSommetAtPosition(e.getX(), e.getY());
		
		//------- Bouton : gauche ---------
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			pop.setVisible(false);
			
			//------ pas de sommet -----------
			if(s == null)
			{
				String retour = JOptionPane.showInputDialog("Saisir le nom du sommet : ");
				while(sommetsNames.contains(retour))
				{
					retour = JOptionPane.showInputDialog("Un sommet de même nom existe déjà, Resaisir un autre nom : ");
				}
				while(retour.trim().isBlank())
				{
					retour = JOptionPane.showInputDialog("Il est nécessair de saisir un nom pour le sommet ! ");
				}
				while(sommetsNames.contains(retour))
				{
					retour = JOptionPane.showInputDialog("Un sommet de même nom existe déjà, Resaisir un autre nom : ");
				}
				this.addSommet(new Sommet(e.getX(), e.getY(), 18, retour, Color.blue));
				this.sommetsNames.add(retour);
				Principal.textSommet.setText(Integer.toString(sommets.size()));
				unselected();
			}
			//------- S'il existe un sommet -------
			if(s != null)
			{
				if(selected == null)
				{
					selected = s;
					selected.setSelected(true);
					repaint();
					Principal.btnBFS.setEnabled(true);
					Principal.btnDFS.setEnabled(true);
					if(InfosGraph.pondere)
					{
						Principal.btnDijkstra.setEnabled(true);
						Principal.btnBellmanford.setEnabled(true);
					}
				}
				else
				{
					this.addArete(selected, s);
					unselected();
					repaint();
					Principal.textArc.setText(Integer.toString(aretes.size()));
				}
			}
		}
		// ------- Bouton : droit -------
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			if(s != null && selected == s)
			{
				pop.setLocation(e.getXOnScreen(), e.getYOnScreen());
				pop.setVisible(true);
				repaint();
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		Sommet s = this.getSommetAtPosition(e.getX(), e.getY());
		if(s == selected)
		{
			s.setX(e.getX());
			s.setY(e.getY());
			repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
