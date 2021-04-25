package Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elements.InfosGraph;
import elements.Sommets;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class InfoGraph extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static String type;
	public static Sommets sm;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			if(InfosGraph.simple)
//				type = "Simple";
//			if(InfosGraph.oriente)
//				type = "Orienté";
//			InfoGraph dialog = new InfoGraph(sm);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public InfoGraph(Sommets sm) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoGraph.class.getResource("/icon/first4.png")));
		InfoGraph.sm = sm;
		this.setLocationRelativeTo(null); 
		setTitle("Informations sur le graphe");
		setBounds(100, 100, 450, 533);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 0, 139));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblInfosGraph = new JLabel("Informations sur le graphe");
			lblInfosGraph.setBackground(new Color(255, 255, 0));
			lblInfosGraph.setForeground(new Color(255, 255, 0));
			lblInfosGraph.setFont(new Font("Adobe Garamond Pro Bold", Font.BOLD, 24));
			lblInfosGraph.setHorizontalAlignment(SwingConstants.CENTER);
			lblInfosGraph.setBounds(10, 10, 416, 35);
			contentPanel.add(lblInfosGraph);
		}
		{
			JLabel lblg = new JLabel("Type du graphe   : ");
			lblg.setForeground(new Color(255, 255, 224));
			lblg.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblg.setBounds(10, 55, 156, 33);
			contentPanel.add(lblg);
		}
		{
			JLabel lbls = new JLabel("Les sommets        : ");
			lbls.setForeground(new Color(255, 255, 224));
			lbls.setFont(new Font("Tahoma", Font.BOLD, 15));
			lbls.setBounds(10, 98, 156, 33);
			contentPanel.add(lbls);
		}
		{
			JLabel lbla = new JLabel("Les arêtes             : ");
			lbla.setForeground(new Color(255, 255, 224));
			lbla.setFont(new Font("Tahoma", Font.BOLD, 15));
			lbla.setBounds(10, 184, 156, 33);
			contentPanel.add(lbla);
		}
		{
			JLabel lblSimpleOriente = new JLabel("");
			lblSimpleOriente.setForeground(new Color(240, 255, 255));
			lblSimpleOriente.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSimpleOriente.setBounds(179, 55, 90, 33);
			contentPanel.add(lblSimpleOriente);
			if(InfosGraph.simple)
				lblSimpleOriente.setText("Non orienté");
			if(InfosGraph.oriente)
				lblSimpleOriente.setText("Orienté");
		}
		{
			JLabel lblPondere = new JLabel("Pondéré");
			lblPondere.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblPondere.setBounds(176, 55, 69, 33);
			contentPanel.add(lblPondere);
			if(InfosGraph.pondere)
				lblPondere.setVisible(true);
			else
				lblPondere.setVisible(false);
			{
				JLabel lblSommets = new JLabel("");
				lblSommets.setForeground(new Color(240, 255, 255));
				lblSommets.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblSommets.setBounds(175, 98, 236, 33);
				contentPanel.add(lblSommets);
				lblSommets.setText(sm.getSommetsNames());
			}
			{
				JLabel lblAretes = new JLabel("");
				lblAretes.setForeground(new Color(240, 255, 255));
				lblAretes.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblAretes.setBounds(176, 184, 250, 33);
				contentPanel.add(lblAretes);
				lblAretes.setText(sm.getAretesNames());
			}
			{
				JLabel lblOr = new JLabel("Ordre du graphe  :");
				lblOr.setForeground(new Color(255, 255, 224));
				lblOr.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblOr.setBounds(10, 141, 156, 33);
				contentPanel.add(lblOr);
			}
			{
				JLabel lblOrdreGraph = new JLabel(Integer.toString(sm.getSommets().size()));
				lblOrdreGraph.setForeground(new Color(240, 255, 255));
				lblOrdreGraph.setHorizontalAlignment(SwingConstants.LEFT);
				lblOrdreGraph.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblOrdreGraph.setBounds(176, 141, 60, 33);
				contentPanel.add(lblOrdreGraph);
			}
			{
				JLabel lblTai = new JLabel("Taille du graphe  : ");
				lblTai.setForeground(new Color(255, 255, 224));
				lblTai.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblTai.setBounds(10, 227, 147, 33);
				contentPanel.add(lblTai);
			}
			{
				JLabel lblTailleGraph = new JLabel(Integer.toString(sm.getAretes().size()));
				lblTailleGraph.setForeground(new Color(240, 255, 255));
				lblTailleGraph.setHorizontalAlignment(SwingConstants.LEFT);
				lblTailleGraph.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblTailleGraph.setBounds(176, 227, 60, 33);
				contentPanel.add(lblTailleGraph);
			}
		}
		{
			JLabel lblComp = new JLabel("Graphe complet  ?");
			lblComp.setForeground(new Color(255, 255, 224));
			lblComp.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblComp.setBounds(10, 270, 147, 33);
			contentPanel.add(lblComp);
		}
		
		JCheckBox chckbxOuiComplet = new JCheckBox("Oui");
		chckbxOuiComplet.setForeground(new Color(255, 255, 240));
		chckbxOuiComplet.setEnabled(false);
		chckbxOuiComplet.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxOuiComplet.setBackground(new Color(255, 255, 255));
		chckbxOuiComplet.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxOuiComplet.setBounds(167, 270, 60, 33);
		contentPanel.add(chckbxOuiComplet);
		
		JCheckBox chckbxNonComplet = new JCheckBox("Non");
		chckbxNonComplet.setForeground(new Color(255, 255, 240));
		chckbxNonComplet.setEnabled(false);
		chckbxNonComplet.setBackground(new Color(255, 255, 255));
		chckbxNonComplet.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxNonComplet.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxNonComplet.setBounds(246, 270, 60, 33);
		contentPanel.add(chckbxNonComplet);
		
		ButtonGroup typeGraph = new ButtonGroup();
		typeGraph.add(chckbxOuiComplet);
		typeGraph.add(chckbxNonComplet);
		
		JLabel lblEul = new JLabel("Graphe eulérien  ?");
		lblEul.setForeground(new Color(255, 255, 224));
		lblEul.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblEul.setBounds(10, 313, 147, 33);
		contentPanel.add(lblEul);
		
		JCheckBox chckbxOuiEulerien = new JCheckBox("Oui");
		chckbxOuiEulerien.setForeground(new Color(255, 255, 240));
		chckbxOuiEulerien.setEnabled(false);
		chckbxOuiEulerien.setBackground(new Color(255, 255, 255));
		chckbxOuiEulerien.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxOuiEulerien.setBounds(167, 313, 60, 33);
		contentPanel.add(chckbxOuiEulerien);
		
		JCheckBox chckbxNonEulerien = new JCheckBox("Non");
		chckbxNonEulerien.setForeground(new Color(255, 255, 240));
		chckbxNonEulerien.setEnabled(false);
		chckbxNonEulerien.setBackground(new Color(255, 255, 255));
		chckbxNonEulerien.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxNonEulerien.setBounds(246, 313, 60, 33);
		contentPanel.add(chckbxNonEulerien);
		
		ButtonGroup eulerien = new ButtonGroup();
		eulerien.add(chckbxOuiEulerien);
		eulerien.add(chckbxNonEulerien);
		
		JLabel lblChaine = new JLabel("Admet une cha\u00EEne eul\u00E9rienne");
		lblChaine.setForeground(new Color(0, 250, 154));
		lblChaine.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChaine.setBounds(167, 352, 211, 31);
		contentPanel.add(lblChaine);
		
		JLabel lblRegulier = new JLabel("Graphe r\u00E9gulier ?");
		lblRegulier.setForeground(new Color(255, 255, 224));
		lblRegulier.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lblRegulier.setBounds(10, 391, 147, 33);
		contentPanel.add(lblRegulier);
		
		JCheckBox chckbxOuiRegulier = new JCheckBox("Oui");
		chckbxOuiRegulier.setForeground(new Color(255, 255, 240));
		chckbxOuiRegulier.setEnabled(false);
		chckbxOuiRegulier.setBackground(new Color(255, 255, 255));
		chckbxOuiRegulier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxOuiRegulier.setBounds(167, 391, 60, 33);
		contentPanel.add(chckbxOuiRegulier);
		
		JCheckBox chckbxNonRegulier = new JCheckBox("Non");
		chckbxNonRegulier.setForeground(new Color(255, 255, 240));
		chckbxNonRegulier.setEnabled(false);
		chckbxNonRegulier.setBackground(new Color(255, 255, 255));
		chckbxNonRegulier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxNonRegulier.setBounds(246, 391, 60, 33);
		contentPanel.add(chckbxNonRegulier);
		
		ButtonGroup regulier = new ButtonGroup();
		regulier.add(chckbxOuiRegulier);
		regulier.add(chckbxNonRegulier);
		sm.isRegulier();
		if(InfosGraph.eulerien)
		{
			chckbxOuiRegulier.setSelected(true);
			chckbxNonRegulier.setSelected(false);
		}
		else
		{
			chckbxNonRegulier.setSelected(true);
			chckbxOuiRegulier.setSelected(false);
		}
		
		JLabel lblKRegulier = new JLabel(Integer.toString(sm.getSommets().get(0).getDegree()));
		lblKRegulier.setForeground(new Color(0, 255, 127));
		lblKRegulier.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKRegulier.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKRegulier.setBounds(312, 391, 26, 33);
		contentPanel.add(lblKRegulier);
		sm.isRegulier();
		if(InfosGraph.regulier)
			lblKRegulier.setVisible(true);
		else
			lblKRegulier.setVisible(false);
		
		JLabel lblReguliertxt = new JLabel("- R\u00E9gulier");
		lblReguliertxt.setForeground(new Color(0, 255, 127));
		lblReguliertxt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReguliertxt.setBounds(342, 391, 69, 33);
		contentPanel.add(lblReguliertxt);
		sm.isRegulier();
		if(InfosGraph.regulier)
			lblReguliertxt.setVisible(true);
		else
			lblReguliertxt.setVisible(false);
		
		sm.isEulerien();
		if(InfosGraph.chaineEulerienne || InfosGraph.eulerien)
			lblChaine.setVisible(true);
		else
			lblChaine.setVisible(false);
		
		sm.isComplet();
		if(InfosGraph.complet)
		{
			chckbxOuiComplet.setSelected(true);
			chckbxNonComplet.setSelected(false);
		}
		else
		{
			chckbxNonComplet.setSelected(true);
			chckbxOuiComplet.setSelected(false);
		}
		
		if(InfosGraph.eulerien)
		{
			chckbxOuiEulerien.setSelected(true);
			chckbxNonEulerien.setSelected(false);
		}
		else
		{
			chckbxNonEulerien.setSelected(true);
			chckbxOuiEulerien.setSelected(false);
		}
	}
}
