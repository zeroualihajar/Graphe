package algorithmes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import javax.imageio.ImageIO;

import elements.Arete;
import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class Kruskal implements Runnable {
	private Sommets sm;
	private ArrayList<Arete> mesAretesTrie;
	// ------------ new Graph -----------
	private ArrayList<Sommet> newSg = new ArrayList<Sommet>();
	private ArrayList<Arete> newAg = new ArrayList<Arete>();
	private int[][] newMatrAdj;
	private double coutACM = 0;
	
	public Kruskal(Sommets sm)
	{
		this.sm = sm;
		newSg.addAll(sm.getSommets());
		Collections.sort(sm.getAretes(), new CoutCompare()); // Trie des couts des aretes
		mesAretesTrie = sm.getAretes();
		newMatrAdj = new int[sm.getSommets().size()][sm.getSommets().size()];
		InfosGraph.chooseColorArete = true;
        InfosGraph.nomAlgo="Kruskal";

	}
	
	public boolean existChemin(int[][] matrAdj, Sommet depart, Sommet arrivee)
	{
		int n = matrAdj.length;
		Stack<Sommet> file = new Stack<Sommet>();
		boolean[] visites = new boolean[n];
		for(int i = 0; i < n; i++)
		{
			visites[i] = false;
		}
		file.push(depart);
		while(!file.isEmpty())
		{
			Sommet courant = file.pop();
			visites[sm.getSommets().indexOf(courant)] = true;
			for(Sommet s : sm.getSommets())
			{
				if(matrAdj[sm.getSommets().indexOf(courant)][sm.getSommets().indexOf(s)] > 0 && visites[sm.getSommets().indexOf(s)] == false)
				{
					file.push(s);
					visites[sm.getSommets().indexOf(s)] = true;
				}
				else if(matrAdj[sm.getSommets().indexOf(courant)][sm.getSommets().indexOf(s)] > 0 && s == arrivee)
					return true;
			}
		}
		return false;
	}
	
	public boolean estCycle(int[][] matrAdj, Sommet depart)
	{
		int n = matrAdj.length;
		Sommet[] parent = new Sommet[n];
		Stack<Sommet> file = new Stack<Sommet>();
		boolean[] visites = new boolean[n];
		for(int i = 0; i < n; i++)
		{
			visites[i] = false;
		}
		visites[sm.getSommets().indexOf(depart)] = true;
		for(int i = 0; i < n; i++)
		{
			parent[i] = null;
		}
		parent[sm.getSommets().indexOf(depart)] = depart;
		file.push(depart);
		while(!file.isEmpty())
		{
			Sommet courant = file.pop();
			visites[sm.getSommets().indexOf(courant)] = true;
			for(Sommet s : sm.getSommets())
			{
				if(matrAdj[sm.getSommets().indexOf(courant)][sm.getSommets().indexOf(s)] > 0 && visites[sm.getSommets().indexOf(s)] == false)
				{
					file.push(s);
					visites[sm.getSommets().indexOf(s)] = true;
					parent[sm.getSommets().indexOf(s)] = courant;
				}
				else if(matrAdj[sm.getSommets().indexOf(courant)][sm.getSommets().indexOf(s)] > 0 && visites[sm.getSommets().indexOf(s)] == true && parent[sm.getSommets().indexOf(courant)] != s)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public void run() 
	{
		Principal.txtTrace.setText("               | Algorithme de Kruskal |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               -------------------------------\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Le déroulement de l'algorithme : \n\n");
		for(Arete a : mesAretesTrie)
		{
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			// Avant d'ajouter une arete on doit verifier l'existence d'une boucle
			Sommet s1 = a.getSommet1();
			Sommet s2 = a.getSommet2();
			Principal.txtTrace.setText(Principal.txtTrace.getText() + "  (" + s1.getNameSommet() + "," + s2.getNameSommet() + ") : " + a.getCout() + " -> ");
			newMatrAdj[sm.getSommets().indexOf(s1)][sm.getSommets().indexOf(s2)] = 1;
			newMatrAdj[sm.getSommets().indexOf(s2)][sm.getSommets().indexOf(s1)] = 1;
			if(estCycle(newMatrAdj, s1) || estCycle(newMatrAdj, s2))
			{
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "NOK ! \n");
			}
			else
			{
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "OK \n");
				newAg.add(a);
				a.setColor(Color.RED);
				sm.repaint();
				coutACM += a.getCout();
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "   => Coût de l'ACM : " + coutACM);
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               | Fin de l'algorithme |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               --------------------------\n");
		// --------------------------- Capture du graphe --------------------------- 
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\Kruskal.png"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		// ---------- Un peu de temps pour effacer la trace du graphe colorie ---------- 
		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
				for(Sommet s : sm.getSommets())
			s.setBackground(Color.PINK);
		for(Arete a : sm.getAretes())
			a.setColor(Color.GREEN);
		InfosGraph.chooseColorArete = false;
		InfosGraph.chooseColorSommet = false;
		sm.unselected();
		sm.repaint();
	}
}
