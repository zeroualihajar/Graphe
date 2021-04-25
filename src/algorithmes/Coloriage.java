package algorithmes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class Coloriage implements Runnable {
	private Sommets sm; // Le sommet
	private ArrayList<Sommet> mesSommets; // Les sommets 
	private ArrayList<Sommet> colored = new ArrayList<Sommet>(); // Les sommets deja colories
	private ArrayList<Sommet> nonColored = new ArrayList<Sommet>(); // Les sommets non pas encore colories
	private ArrayList<Color> colors = new ArrayList<Color>(); // Liste des couleurs
	private Sommet first; // Le sommet ayant le plus grand degre

	private int etape = 0;
	private String dg = "";
	private int nbreColors = 0;
	
	// ------------------------ Constructeur ------------------------
	public Coloriage(Sommets sm)
	{
		this.sm = sm;
		mesSommets = sm.getSommets(); // Recuperation des sommets du graphe
		nonColored.addAll(mesSommets); // On commence par tous les sommets (aucun sommet n'est colorie)
		Collections.sort(mesSommets, Collections.reverseOrder(new DegreeCompare())); // Trie des degres des sommets par ordre decroissant
		first = mesSommets.get(0); // On recupere le sommet ayant le plus grand degre
		//sm.repaint();
		colored.add(first); // Ajout du sommet first � la table des sommets colories
		nonColored.removeAll(colored); // On enlevee chaque fois les sommets colories de la table des sommets non colories
		// ----------------- Ajout des couleurs à utiliser -----------------
		colors.add(Color.GREEN);
		colors.add(Color.WHITE);
		colors.add(Color.BLUE);
		colors.add(Color.CYAN.darker());
		colors.add(Color.GRAY);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
		colors.add(Color.MAGENTA);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN.darker());
		colors.add(Color.BLACK);
		colors.add(new Color(192, 0, 255));
        InfosGraph.nomAlgo="WelchPowell";

	}
	
	// -------------- Fonction de repition ----------
	public void col(Sommet s, int i)
	{
		s.setBackground(colors.get(i));// Coloriage du sommet
		sm.repaint(); 
		for(Sommet v : mesSommets) 
		{
			if(nonColored.contains(v)) // S'il n'est pas encore colorie
			{
				if(!s.getSomSort().contains(v)) // S'il n'appartient pas aux voisins du sommet s
				{
					Principal.txtTrace.setText(Principal.txtTrace.getText() + v.getNameSommet() + "  ");
					v.setBackground(s.getBackground()); // On colorie ce sommet par la meme couleur du sommet s
					sm.repaint();
					colored.add(v); // Et on l'ajoute a la liste des sommets colories
				}
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n");
		nonColored.removeAll(colored); // enlever sommet deja colories
		
		try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private String getNameColor(Color c)
	{
		HashMap<Color, String> col = new HashMap<Color, String>();
		col.put(Color.GREEN, "Vert");
		col.put(Color.WHITE, "Blanc");
		col.put(Color.BLUE, "Bleu");
		col.put(Color.CYAN.darker(), "Cyan fonc�");
		col.put(Color.GRAY, "Gris");
		col.put(Color.ORANGE, "Orange");
		col.put(Color.RED, "Rouge");
		col.put(Color.MAGENTA, "Rose fonc�");
		col.put(Color.YELLOW, "Jaune");
		col.put(Color.GREEN.darker(), "Vert fonc�");
		col.put(Color.BLACK, "Noir");
		col.put(new Color(192, 0, 255), "Violet");
		
		return (String) col.get(c);
	}
	
	// ------- run() --------
	@Override
	public void run() {
		for(Sommet s : mesSommets)
			dg += s.getDegree() + " ";
		Principal.txtTrace.setText("               |  Algorithme de Welch et Powell |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               -------------------------------------------------\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Initialisation : \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "   Liste des degés des sommets dans l'ordre décroissant : " + dg + "\n");
		int i = 0;
		etape += 1;
		nbreColors += 1;
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " - Etape " + etape + " : \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "     Avec la couleur " + etape + " (" + getNameColor(colors.get(i)/*first.getBackground()*/) + "), on colorie successivement (et dans l'ordre) les sommets : " + first.getNameSommet() + " ");
		col(first, i); // On commence par le premier sommet
		while(colored.size() < mesSommets.size()) // On boucle jusqu'a ce qu'on colorie tous les sommets
		{
			for(Sommet s : mesSommets)
			{

				i += 1; // S'incremente pour changer de couleur
				if(nonColored.contains(s))
				{
					etape += 1;
					Principal.txtTrace.setText(Principal.txtTrace.getText() + " - Etape " + etape + " : \n");
					Principal.txtTrace.setText(Principal.txtTrace.getText() + "     Avec la couleur " + etape + " (" + getNameColor(colors.get(i)/*s.getBackground()*/) + "), on colorie successivement (et dans l'ordre) les sommets : ");
					col(s, i);
					nbreColors += 1;
				}
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               | Fin de l'algorithme |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               --------------------------\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n Solution : \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "  Liste des degrés : (" + dg + ")\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "    -> Nombre de couleur minimum : " + nbreColors);
		// ------ Capture du graphe ----------
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\Coloriage.png"));
 		} 
		catch (IOException e) 
		{
 			e.printStackTrace();
 		}
		// --------- Un peu de temps pour effacer la trace du graphe colorie
		try
		{
			Thread.sleep(5000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		
		sm.unselected();
		for(Sommet s : sm.getSommets())
			s.setBackground(Color.blue);
		sm.unselected();
		sm.repaint();
	}

}
