package algorithmes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class BFS implements Runnable {
	private Sommets sm;
	private Sommet selected;
	private Stack<Sommet> f;
	private Stack<Sommet> m;
	private int etape = 0;
	private String fliste = "";
	private String mliste = "";
	
	public BFS(Sommets sm)
	{
		this.sm = sm;
		this.selected = sm.getSelected();
		f = new Stack<Sommet>();
		m = new Stack<Sommet>();
		f.push(selected);
		m.push(selected);
		selected.setAlgo(true);
		sm.repaint();
        InfosGraph.nomAlgo="BFS";
	}

	@Override
	public void run() {
		for(int i = 0; i < f.size(); i++)
			fliste += f.get(i).getNameSommet() + " ";
		for(int i = 0; i < m.size(); i++)
			mliste += m.get(i).getNameSommet() + " ";
		Principal.txtTrace.setText("                   | Parcours BFS |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "                   ----------------------\n\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " F = ] " + fliste + "]  ;  M = { " + mliste + "}\n");
		while(!f.isEmpty())
		{  
			fliste = "";
			for(int i = 0; i < f.size(); i++)
				fliste += f.get(i).getNameSommet() + " ";
			try
			{
				Thread.sleep(800);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			etape += 1;
			Principal.txtTrace.setText(Principal.txtTrace.getText() + " Etape " + etape + " : \n");
			Principal.txtTrace.setText(Principal.txtTrace.getText() + "     - Les voisins de " + selected.getNameSommet() + " :\n");
			selected = f.pop();
			for(Sommet s : selected.sommetSortant)
			{
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "        >> " + s.getNameSommet() + " : \n");
				if(!m.contains(s))
				{
					f.push(s);
					m.push(s);
					s.setAlgo(true);
					sm.repaint();
				}
				sm.repaint();
				fliste = "";
				mliste = "";
				for(int i = 0; i < f.size(); i++)
					fliste += f.get(i).getNameSommet() + " ";
				for(int i = 0; i < m.size(); i++)
					mliste += m.get(i).getNameSommet() + " ";
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "            => F = ] " + fliste +" ]\n");
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "            => M = { " + mliste +" }\n");
			}
			sm.repaint();
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               | Fin de l'algorithme |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               --------------------------\n");
		mliste = "";
		for(int i = 0; i < m.size(); i++)
			mliste += m.get(i).getNameSommet() + " ";
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Conclusion : \n   M = { " + mliste + " }\n");
		// --------------------------- Capture du graphe --------------------------- 
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\BFS.png"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		// --------------------------- Repaint du graphe --------------------------- 
		try 
		{
            Thread.sleep(3000);
        } 
		catch (InterruptedException ex) 
		{
            ex.printStackTrace();
        }
		for(Sommet s : sm.getSommets())
			s.setAlgo(false);
		sm.unselected();
		sm.repaint();
	}

}
