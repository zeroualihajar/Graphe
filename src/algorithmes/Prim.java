package algorithmes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import elements.Arete;
import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class Prim implements Runnable {
	private Sommets sm;
	private ArrayList<Sommet> newSg = new ArrayList<Sommet>();
	private ArrayList<Arete> newAg = new ArrayList<Arete>();
	private double coutACM = 0;
	private String listA = "";
	private String listS = "";
	
	public Prim(Sommets sm)
	{
		InfosGraph.chooseColorSommet = true;
		InfosGraph.chooseColorArete = true;
		
		this.sm = sm;
		Collections.sort(sm.getSommets(), Collections.reverseOrder(new DegreeCompare()));
		newSg.add(sm.getSommets().get(0));
		sm.getSommets().get(0).setBackground(Color.BLUE);
		sm.repaint();
        InfosGraph.nomAlgo="Prim";

	}

	@Override
	public void run() {
		Principal.txtTrace.setText("               | Algorithme de Prim |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               -----------------------------\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Le déroulement de l'algorithme : \n\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " A = (" + sm.getSommets().get(0).getNameSommet() + ", inf)\n");
		listS += sm.getSommets().get(0).getNameSommet();
		while(newSg.size() < sm.getSommets().size())
		{
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			ArrayList<Arete> mesAretes = new ArrayList<Arete>();
			Principal.txtTrace.setText(Principal.txtTrace.getText() + " A = (");
			for(Sommet s : newSg)
			{
				listS = s.getNameSommet() + " ";
				Principal.txtTrace.setText(Principal.txtTrace.getText() + listS + ", ");
				for(Sommet v : s.getSomSort())
				{
					if(!newAg.contains(sm.getArete(s, v)) && !newSg.contains(v) || !newAg.contains(sm.getArete(v, s)) && !newSg.contains(v) )
					{
						mesAretes.add(sm.getArete(s, v));
					}
				}
			}
			Collections.sort(mesAretes, new CoutCompare()); // Trie des aretes possibles
			Sommet suivant = mesAretes.get(0).getSommet2(); // Sommet suivant � ajouter
			newSg.add(suivant); // Ajout de ce sommet � notre graphe
			listS = suivant.getNameSommet();
			Principal.txtTrace.setText(Principal.txtTrace.getText() + listS + ", ");
			for(Sommet s : newSg)
				s.setBackground(Color.BLUE);
			
			sm.repaint();
			Arete plus = mesAretes.get(0); // L'ar�te possible
			coutACM += plus.getCout();
			listA += "(" + plus.getSommet1().getNameSommet() + "," + plus.getSommet2().getNameSommet() + ") ";
			Principal.txtTrace.setText(Principal.txtTrace.getText() + listA + ")\n");
			newAg.add(plus); // Ajout a la liste des nouvels aretes
			plus.setColor(Color.BLACK); // Coloriage de cette arete
			sm.repaint();
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
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\Prim.png"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		// ---------- Un peu de temps pour effacer la trace de l'algorithme ----------
		try
		{
			Thread.sleep(3000);
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
