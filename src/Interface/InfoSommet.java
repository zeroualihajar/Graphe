package Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elements.InfosGraph;
import elements.Sommets;
import elements.Sommet;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;

public class InfoSommet extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JPanel parent;
	public static Sommet s;
	public static JTextField txtNom;
	public static JTextField txtDegree;
	public static JTextField txtSommetEntr;
	public static JTextField txtSommetSort;
	public static int n;
	//private int degree;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			InfoSommet dialog = new InfoSommet(s, n);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public InfoSommet(Sommet s, int n) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(InfoSommet.class.getResource("/icon/first4.png")));
		InfoSommet.s = s;
		this.n = n;
		/*if(InfosGraph.simple)
			degree = s.sommetEntrant.size();
		if(InfosGraph.oriente)
			degree = s.sommetEntrant.size() + s.sommetSortant.size();*/
		this.setLocationRelativeTo(null);
		setTitle("Informations sur le sommet");
		setBounds(100, 100, 546, 429);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBackground(new Color(0, 0, 139));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		

		JLabel lblInfos = new JLabel("Informations sur le sommet");
		lblInfos.setForeground(new Color(255, 255, 0));
		lblInfos.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblInfos.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfos.setBounds(84, 10, 317, 51);
		contentPanel.add(lblInfos);
		
		JLabel lblNom = new JLabel("Nom du sommet : ");
		lblNom.setForeground(new Color(255, 255, 224));
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNom.setBounds(20, 96, 145, 33);
		contentPanel.add(lblNom);
		
		JLabel lblDegree = new JLabel("Degré                    :");
		lblDegree.setForeground(new Color(255, 255, 224));
		lblDegree.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDegree.setBounds(20, 172, 287, 33);
		contentPanel.add(lblDegree);
		
		JLabel lblSommetEntr = new JLabel("Prédécesseurs (sommets entrants) : ");
		lblSommetEntr.setForeground(new Color(255, 255, 224));
		lblSommetEntr.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSommetEntr.setBounds(20, 240, 287, 33);
		contentPanel.add(lblSommetEntr);
		
		JLabel lblSommetSort = new JLabel("Successeurs (sommets sortants)     : ");
		lblSommetSort.setForeground(new Color(255, 255, 224));
		lblSommetSort.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSommetSort.setBounds(20, 306, 287, 33);
		contentPanel.add(lblSommetSort);
		
		txtNom = new JTextField(s.getNameSommet());
		txtNom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNom.setHorizontalAlignment(SwingConstants.CENTER);
		txtNom.setBackground(new Color(255, 255, 240));
		txtNom.setEditable(false);
		txtNom.setBounds(317, 96, 162, 33);
		contentPanel.add(txtNom);
		txtNom.setColumns(10);
		
		txtDegree = new JTextField(Integer.toString(s.getDegree()));
		txtDegree.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDegree.setHorizontalAlignment(SwingConstants.CENTER);
		txtDegree.setBackground(new Color(255, 255, 240));
		txtDegree.setEditable(false);
		txtDegree.setBounds(317, 172, 162, 33);
		contentPanel.add(txtDegree);
		txtDegree.setColumns(10);
		
		txtSommetEntr = new JTextField(s.getEntr());//Integer.toString(s.getNbreSommetEnt()));
		txtSommetEntr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSommetEntr.setHorizontalAlignment(SwingConstants.CENTER);
		txtSommetEntr.setBackground(new Color(255, 255, 240));
		txtSommetEntr.setBounds(317, 240, 162, 33);
		contentPanel.add(txtSommetEntr);
		txtSommetEntr.setColumns(10);
		
		txtSommetSort = new JTextField(s.getSort());//Integer.toString(s.getNbreSommetSort()));
		txtSommetSort.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSommetSort.setHorizontalAlignment(SwingConstants.CENTER);
		txtSommetSort.setBackground(new Color(255, 255, 240));
		txtSommetSort.setEditable(false);
		txtSommetSort.setBounds(317, 306, 162, 33);
		contentPanel.add(txtSommetSort);
		txtSommetSort.setColumns(10);
		
		JLabel lblTypeDegre = new JLabel();
		lblTypeDegre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeDegre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTypeDegre.setBounds(367, 96, 112, 33);
		contentPanel.add(lblTypeDegre);
		if(s.getDegree() == 0)
			lblTypeDegre.setText("(Isol�)");
		if(s.getDegree() == 1)
			lblTypeDegre.setText("(Pendant(Feuille))");
		if(s.getDegree() == n - 1)
			lblTypeDegre.setText("(Dominant)");
	}
}
