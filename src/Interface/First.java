package Interface;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.awt.Toolkit;

public class First {

	public static JFrame frame;
	public String test = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					First window = new First();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public First() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// -------- frame principal de la premiere page ---------
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(First.class.getResource("/icon/first4.png")));
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.getContentPane().setLayout(null);
		frame.setTitle("Bienvenu");

		// -------- images ---------------------------------------
		JLabel firs = new JLabel("");
		firs.setBackground(new Color(245, 245, 245));
		Image img4 = new ImageIcon("src/icon/first10.png").getImage();
		firs.setIcon(new ImageIcon(img4));
		firs.setBounds(773, -101, 227, 245);
		frame.getContentPane().add(firs);

		JLabel firs1 = new JLabel("");
		firs1.setBackground(new Color(245, 245, 245));
		Image img7 = new ImageIcon("src/icon/first4.png").getImage();
		firs1.setIcon(new ImageIcon(First.class.getResource("/icon/first4.png")));
		firs1.setBounds(-271, 68, 511, 369);
		frame.getContentPane().add(firs1);
		Image img6 = new ImageIcon("src/icon/first10.png").getImage();

		JLabel firs3 = new JLabel("");
		firs3.setBackground(new Color(245, 245, 245));
		Image img8 = new ImageIcon("src/icon/1.png").getImage();
		firs3.setIcon(new ImageIcon(First.class.getResource("/icon/1.png")));
		firs3.setBounds(381, 121, 209, 276);
		frame.getContentPane().add(firs3);

		// --------- btn : start ------------------------------------
		JButton start = new JButton("  Commencer");
		start.setForeground(new Color(255, 255, 255));
		start.setFont(new Font("Times New Roman", Font.BOLD, 20));
		start.setBackground(new Color(220, 20, 60));
		start.setBounds(464, 445, 448, 67);
		start.setIcon(new ImageIcon(First.class.getResource("/icon/10.png")));
		frame.getContentPane().add(start);

		// --------- btn : guide d'utilisation------------------------
		JButton btnUtil = new JButton("  Guide d'utilisation");
		btnUtil.setToolTipText("");
		btnUtil.setForeground(new Color(255, 255, 255));
		btnUtil.setFont(new Font("Traditional Arabic", Font.BOLD | Font.ITALIC, 20));
		btnUtil.setBackground(new Color(135, 206, 250));
		btnUtil.setBounds(0, 445, 466, 68);
		btnUtil.setIcon(new ImageIcon(First.class.getResource("/icon/13.png")));
		frame.getContentPane().add(btnUtil);

		btnUtil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (Desktop.isDesktopSupported()) {
					try {
						Platform.startup(() -> {
							JFrame web = new JFrame("Guide d'utilisation");
							WebView webView = new WebView();

							URL url = this.getClass().getResource("guide/index.html");
							webView.getEngine().load(url.toString());

							VBox vBox = new VBox(webView);
							Scene scene = new Scene(vBox, 1200, 600);

							JFXPanel pan = new JFXPanel();
							pan.setScene(scene);

							ImageIcon ic = new ImageIcon("src/icon/first10.png");
							Image im = ic.getImage();
							web.setIconImage(im);
							web.add(pan);
							web.setVisible(true);
							web.pack();
						});
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
			}
		});

		// ----------- panel principal ----------------------
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(65, 105, 225), 3));
		panel.setBackground(new Color(0, 191, 255));
		panel.setBounds(541, 121, 326, 292);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// ---------- Radio btn -----------------------------
		JRadioButton nonpd = new JRadioButton("Non Orienté et Pondéré");
		nonpd.setBounds(77, 36, 173, 20);
		panel.add(nonpd);
		nonpd.setForeground(new Color(165, 42, 42));
		nonpd.setFont(new Font("Bodoni MT", Font.PLAIN, 14));
		nonpd.setBackground(new Color(250, 250, 250));

		JRadioButton ort = new JRadioButton("Orienté");
		ort.setBounds(80, 184, 83, 20);
		panel.add(ort);
		ort.setForeground(new Color(165, 42, 42));
		ort.setFont(new Font("Bodoni MT", Font.PLAIN, 14));
		ort.setBackground(new Color(252, 252, 252));

		JRadioButton ortpd = new JRadioButton("Orienté et Pondéré");
		ortpd.setBounds(77, 82, 150, 25);
		panel.add(ortpd);
		ortpd.setForeground(new Color(165, 42, 42));
		ortpd.setFont(new Font("Bodoni MT", Font.PLAIN, 14));
		ortpd.setBackground(new Color(252, 252, 252));

		JRadioButton nonort = new JRadioButton("Non Orienté");
		nonort.setBounds(77, 133, 107, 20);
		panel.add(nonort);
		nonort.setForeground(new Color(165, 42, 42));
		nonort.setFont(new Font("Bodoni MT", Font.PLAIN, 14));
		nonort.setBackground(new Color(252, 252, 252));

		// ---------- image : check ----------------------
		JLabel check = new JLabel("");
		check.setBackground(new Color(211, 211, 211));
		check.setBounds(67, 21, 200, 249);
		panel.add(check);

		Image img1 = new ImageIcon("src/icon/2.png").getImage();
		check.setIcon(new ImageIcon(First.class.getResource("/icon/2.png")));

		ButtonGroup typegroup = new ButtonGroup();
		typegroup.add(nonpd);
		typegroup.add(ort);
		typegroup.add(ortpd);
		typegroup.add(nonort);

		// -------- Title --------------------------------
		JLabel lblNewLabel = new JLabel("BIENVENUE");
		lblNewLabel.setBackground(new Color(60, 179, 113));
		lblNewLabel.setFont(new Font("Calisto MT", Font.BOLD, 62));
		lblNewLabel.setForeground(new Color(0, 0, 205));
		lblNewLabel.setBounds(247, 22, 376, 75);
		frame.getContentPane().add(lblNewLabel);

		// ------------- action de Button : start ----------------
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// ----------- Récupération du type de graphe ------------
				for (Enumeration<AbstractButton> buttons = typegroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					if (button.isSelected()) {
						if (button.getText() == null) {
							JOptionPane.showMessageDialog(frame, "Il Faut choisir un type du graphe s'il vous plaît !",
									"Type du graphe !", JOptionPane.WARNING_MESSAGE);
						} else {
							frame.setVisible(false);
							Principal principal = new Principal(button.getText());
							principal.frameprincipal.setVisible(true);
						}
					}

				}

			}
		});

		frame.setBounds(150, 100, 926, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
