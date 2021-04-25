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

public class DFS implements Runnable {
	private Sommets sm;
	private Sommet selected;
	private Stack<Sommet> m;
	private int etape = 0;
	private String mliste = "";
	
	public DFS(Sommets sm)
	{
		this.sm = sm;
		this.selected = sm.getSelected();
		m = new Stack<Sommet>();
		m.push(selected);
		selected.setAlgo(true);
		sm.repaint();
        InfosGraph.nomAlgo="DFS";

	}
	
	public void dfs(Sommet s)
	{
		try 
		{
            Thread.sleep(800);
        } 
		catch (InterruptedException ex) 
		{
            ex.printStackTrace();
        }
		m.push(s);
		s.setAlgo(true);
		sm.repaint();
		for(Sommet v : s.sommetSortant)
		{
			if(!m.contains(v))
			{
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "              - t = " + v.getNameSommet() + " not in M : \n          -> DFS(G , " + v.getNameSommet() + ")\n");
				dfs(v);
			}
		}
//		MyWorkspace.txtTrace.setText(MyWorkspace.txtTrace.getText() + "              - t = null  =>  Rien\n" );
//		mliste = "";
//		for(int i = 0; i < m.size(); i++)
//			mliste += m.get(i).getNameSommet() + " ";
//		MyWorkspace.txtTrace.setText(MyWorkspace.txtTrace.getText() + " -> M = { " + mliste + " } \n");
	}
 
	@Override
	public void run() {
		mliste = "";
		for(int i = 0; i < m.size(); i++)
			mliste += m.get(i).getNameSommet() + " ";
		Principal.txtTrace.setText("                   | Parcours DFS |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "                   ----------------------\n\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " DFS(G , " + selected.getNameSommet() + ") -> M = { " + selected.getNameSommet() + " }\n");
		for(Sommet s : selected.sommetSortant)
		{
			if(!m.contains(s))
			{
				etape += 1;
				Principal.txtTrace.setText(Principal.txtTrace.getText() + " Etape " + etape + " : \n");
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "     - t = " + s.getNameSommet() + " not in M : \n          -> DFS(G , " + s.getNameSommet() + ")\n");
				dfs(s);
				Principal.txtTrace.setText(Principal.txtTrace.getText() + "              - t = null  =>  Rien\n" );
				mliste = "";
				for(int i = 0; i < m.size(); i++)
					mliste += m.get(i).getNameSommet() + " ";
				Principal.txtTrace.setText(Principal.txtTrace.getText() + " -> M = { " + mliste + " } \n");
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               | Fin de l'algorithme |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               --------------------------\n");
		mliste = "";
		for(int i = 0; i < m.size(); i++)
			mliste += m.get(i).getNameSommet() + " ";
		Principal.txtTrace.setText(Principal.txtTrace.getText() + " Conclusion : \n   M = { " + mliste + " }");
		// --------------------------- Capture du graphe --------------------------- 
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\DFS.png"));
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		try 
		{
            Thread.sleep(2000);
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
