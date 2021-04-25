package algorithmes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.imageio.ImageIO;

import elements.Arete;
import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class BellmanFord implements Runnable {
	private Sommets sm;
	private final ArrayList<Sommet> sommets;
    private final ArrayList<Arete> aretes;
    private Sommet first;
    private HashMap<String,Double> l;
    private HashMap<String,String> p;
    private int etape = 0;

    public BellmanFord(Sommets sm) {
    	this.sm = sm;
        this.sommets = sm.getSommets();
        this.aretes = sm.getAretes();
        this.l = new HashMap<>();
        this.p = new HashMap<>();
        this.first = sm.getSelected();
        InfosGraph.nomAlgo="BellmanFord";
        Collections.sort(sommets, new DegreeCompare());
    }
    
	@Override
	public void run() {
		Principal.txtTrace.setText("            | Algorithme de Bellman-Ford | \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "            ---------------------------------------\n");
		for(Sommet s : sommets)
		{
			if(first == s)
			{
				l.put(s.getNameSommet(), 0.0d);
				p.put(s.getNameSommet(), null);
			}
			else
			{
				l.put(s.getNameSommet(), Double.POSITIVE_INFINITY);
				p.put(s.getNameSommet(), null);
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Initialisation : \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "   L = " + l + "\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "   P = " + p + "\n");
		for(int i = 0; i < sommets.size(); i++)
		{
			try
			{
				Thread.sleep(800);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			etape += 1;
			Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n Etape " + etape + " :\n");
			for(Arete a : aretes)
			{
				double newCout = l.get(a.getSommet1().getNameSommet()) + a.getCout();
				if(l.get(a.getSommet2().getNameSommet()) > newCout)
				{
					l.put(a.getSommet2().getNameSommet(), newCout);
					p.put(a.getSommet2().getNameSommet(), a.getSommet1().getNameSommet());
				}
			}
			Principal.txtTrace.setText(Principal.txtTrace.getText() + "   L = " + l + "\n");
			Principal.txtTrace.setText(Principal.txtTrace.getText() + "   P = " + p + "\n");
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n                 --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n                | Fin de l'algorithme | \n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "                 --------------------------");
		//----- capture du graphe ------------
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\BellmanFord.png"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		//----- Repaint dug graphe -----------
		sm.unselected();
		sm.repaint();
		new Interface.PlusCourtCheminResult(Principal.frameprincipal, first, l, p, "Bellman-Ford").setVisible(true);
	}

}
