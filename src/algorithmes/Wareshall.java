package algorithmes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import elements.Arete;
import elements.InfosGraph;
import elements.Sommet;
import elements.Sommets;
import Interface.Principal;

public class Wareshall implements Runnable {
	private Sommets sm = new Sommets();

	private int etape = 0;

	private int[][] matrWar ;
	
	public Wareshall(Sommets sm)
	{
		this.sm = sm;
        InfosGraph.nomAlgo="Wareshall";

	}

	@Override
	public void run() {
		Principal.txtTrace.setText("                   |  Algorithme de Warshall |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "                   ---------------------------------\n\n");
		for(Sommet s1 : sm.getSommets())
		{
			Principal.txtTrace.setText(Principal.txtTrace.getText() + " y = " + s1.getNameSommet() + " :\n ");
			for(Sommet s2 : sm.getSommets())
			{
				try
				{
					Thread.sleep(600);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				if(sm.getMatriceAdjacence()[sm.getSommets().indexOf(s1)][sm.getSommets().indexOf(s2)] == 0)
				{
					Principal.txtTrace.setText(Principal.txtTrace.getText() + "  (" + s1.getNameSommet() + " , " + s2.getNameSommet() + ") not in E  =>  Ajout de (" + s1.getNameSommet() + " , " + s2.getNameSommet() + ")\n");
					sm.addAreteWareshall(s1, s2);
					sm.repaint();
				}
//				else
//					MyWorkspace.txtTrace.setText(MyWorkspace.txtTrace.getText() + "  (" + s1.getNameSommet() + " , " + s2.getNameSommet() + ") not in E  =>  Ajout de (" + s1.getNameSommet() + " , " + s2.getNameSommet() + ")\n");
			}
		}
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               --------------------------");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "\n               | Fin de l'algorithme |\n");
		Principal.txtTrace.setText(Principal.txtTrace.getText() + "               --------------------------\n");
		// --------------------------- Capture du graphe --------------------------- 
		try 
        {
        	BufferedImage screen = new BufferedImage(Principal.sm.getWidth(), Principal.sm.getHeight(), BufferedImage.TYPE_INT_RGB);
        	Principal.sm.printAll(screen.getGraphics());
        	ImageIO.write(screen, "png", new File("src\\ScreenAlgos\\Warshall.png"));
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
		InfosGraph.wareshall = false;
		sm.getAretesWareshall().clear();
		sm.unselected();
		sm.repaint();
	}

}
