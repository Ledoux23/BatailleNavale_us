package batailleNavale;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class BatailleNavale {

	private Joueur joueur1, joueur2;
	private JRadioButton radioGraphiqueJ1, radioTexteJ1, radioAutoJ1;
	private JRadioButton radioGraphiqueJ2, radioTexteJ2, radioAutoJ2;
	private int tailleGrille;
	private JCheckBox checkPlacementAuto, checkNavireGen;

	private JFrame frmBataillenavale;
	private JTextField fieldTailleGrille;
	private JTextField fieldNomJoueur1;
	private JTextField fieldNomJoueur2;
	private final ButtonGroup choixJoueurUn = new ButtonGroup();
	private final ButtonGroup choixJoueurDeux = new ButtonGroup();
	private JButton startButton;
	private boolean placementPossible = false;
	private boolean placementVertical = false;
	private boolean placementManuelFini = false;

	public int[] navireGen(int tailleGrille) {
		int placementReserverNavire = (int) (Math.pow(tailleGrille, 2) * 0.2); //Paramètre réglable.
		
		
		
		int[] tailleNaviresBase = new int[] { 2, 2, 3, 3, 4, 5 };
		int lastValidIndex = 5;//5 default
		int tailleRestante = placementReserverNavire - 19; // 19 = somme des tailles de base
		// remplire le tableau de navire des tailles de base.
		int[] tailleNaviresPost = Arrays.copyOf(tailleNaviresBase, placementReserverNavire/2);
		System.out.println(tailleRestante);
		while (tailleRestante > 1) {
			int tailleGen = (int) ((Math.random() * (6 - 2)) + 2); // taille random entre 2 et 6 exclus
			for (int i = 0; i < placementReserverNavire/2; i++)
				if (tailleNaviresPost[i] == 0) {
					tailleNaviresPost[i] = tailleGen;
					tailleRestante -=tailleGen;
					lastValidIndex = i;
					break;
				}
		}

		return Arrays.copyOfRange(tailleNaviresPost, 0, lastValidIndex+1);

	}

	private void demarrerPartie() {
		//joueur1.jouerAvec(joueur2);
		
		String nomJoueurUn = fieldNomJoueur1.getText();
		String nomJoueurDeux = fieldNomJoueur2.getText();
		Random commencePremierJ1 = new Random();
		
		if (radioGraphiqueJ1.isSelected() && !checkPlacementAuto.isSelected()) {
			JOptionPane.showMessageDialog(null, nomJoueurUn + " commence en premier.", "Premier joueur", JOptionPane.INFORMATION_MESSAGE);
			joueur1.jouerAvec(joueur2);
		} else if (radioGraphiqueJ2.isSelected() && !checkPlacementAuto.isSelected()) {
			JOptionPane.showMessageDialog(null, nomJoueurDeux + " commence en premier.", "Premier joueur", JOptionPane.INFORMATION_MESSAGE);
			joueur2.jouerAvec(joueur1);
		} else {
			if ((commencePremierJ1.nextBoolean())) {
				JOptionPane.showMessageDialog(null, nomJoueurUn + " commence en premier.", "Premier joueur", JOptionPane.INFORMATION_MESSAGE);
				joueur1.jouerAvec(joueur2);
			} else {
				JOptionPane.showMessageDialog(null, nomJoueurDeux + " commence en premier.", "Premier joueur", JOptionPane.INFORMATION_MESSAGE);
				joueur2.jouerAvec(joueur1);
			}
		}
		

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatailleNavale window = new BatailleNavale();
					window.frmBataillenavale.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BatailleNavale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBataillenavale = new JFrame();
		frmBataillenavale.setTitle("Bataille Navale");
		frmBataillenavale.setBounds(100, 100, 450, 380);
		frmBataillenavale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBataillenavale.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel mainNorth = new JPanel();
		frmBataillenavale.getContentPane().add(mainNorth, BorderLayout.NORTH);
		mainNorth.setLayout(new BorderLayout(0, 0));

		JPanel innerNorth = new JPanel();
		innerNorth.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mainNorth.add(innerNorth, BorderLayout.NORTH);
		innerNorth.setLayout(new BoxLayout(innerNorth, BoxLayout.X_AXIS));
		
		JPanel parametres = new JPanel();
		innerNorth.add(parametres);
		parametres.setLayout(new GridLayout(2,0,0,0));
		
		JPanel param1 = new JPanel();
		parametres.add(param1);
		param1.setLayout(new BoxLayout(param1, BoxLayout.X_AXIS));

		JLabel labelTailleGrille = new JLabel("Taille de grille : ");
		param1.add(labelTailleGrille);

		fieldTailleGrille = new JTextField();
		fieldTailleGrille.setText("10");
		param1.add(fieldTailleGrille);
		fieldTailleGrille.setColumns(10);

		checkPlacementAuto = new JCheckBox("Placement automatique ?");
		checkPlacementAuto.setToolTipText("Si coché, place les navires automatiquement. Sinon, vous devrez placer les navires manuellement.");
		checkPlacementAuto.setSelected(true);
		param1.add(checkPlacementAuto);
		
		checkNavireGen = new JCheckBox("Distribution dynamique ?");
		checkNavireGen.setToolTipText("Nombre de navires varie en fonction de la taille de la grille");
		parametres.add(checkNavireGen);

		JPanel innerCenter = new JPanel();
		mainNorth.add(innerCenter);
		innerCenter.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel joueurUnGrille = new JPanel();
		joueurUnGrille
				.setBorder(new TitledBorder(null, "Joueur 1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		innerCenter.add(joueurUnGrille);
		joueurUnGrille.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel jUnNomLayout = new JPanel();
		joueurUnGrille.add(jUnNomLayout);
		jUnNomLayout.setLayout(new BoxLayout(jUnNomLayout, BoxLayout.X_AXIS));

		JLabel labelNomJoueur1 = new JLabel("Nom : ");
		labelNomJoueur1.setHorizontalAlignment(SwingConstants.LEFT);
		jUnNomLayout.add(labelNomJoueur1);

		fieldNomJoueur1 = new JTextField();
		fieldNomJoueur1.setText("Joueur 1");

		jUnNomLayout.add(fieldNomJoueur1);
		fieldNomJoueur1.setColumns(10);

		radioGraphiqueJ1 = new JRadioButton("Joueur Graphique");
		radioGraphiqueJ1.setSelected(true);
		choixJoueurUn.add(radioGraphiqueJ1);
		joueurUnGrille.add(radioGraphiqueJ1);

		radioTexteJ1 = new JRadioButton("Joueur Texte");
		choixJoueurUn.add(radioTexteJ1);
		joueurUnGrille.add(radioTexteJ1);

		radioAutoJ1 = new JRadioButton("Joueur Auto");
		choixJoueurUn.add(radioAutoJ1);
		joueurUnGrille.add(radioAutoJ1);

		JPanel joueurDeuxGrille = new JPanel();
		joueurDeuxGrille
				.setBorder(new TitledBorder(null, "Joueur 2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		innerCenter.add(joueurDeuxGrille);
		joueurDeuxGrille.setLayout(new GridLayout(4, 0, 0, 0));

		JPanel jDeuxNomLayout = new JPanel();
		joueurDeuxGrille.add(jDeuxNomLayout);
		jDeuxNomLayout.setLayout(new BoxLayout(jDeuxNomLayout, BoxLayout.X_AXIS));

		JLabel labelNomJoueur2 = new JLabel("Nom : ");
		jDeuxNomLayout.add(labelNomJoueur2);

		fieldNomJoueur2 = new JTextField();
		fieldNomJoueur2.setText("Joueur 2");

		jDeuxNomLayout.add(fieldNomJoueur2);
		fieldNomJoueur2.setColumns(10);

		radioGraphiqueJ2 = new JRadioButton("Joueur Graphique");
		choixJoueurDeux.add(radioGraphiqueJ2);
		joueurDeuxGrille.add(radioGraphiqueJ2);

		radioTexteJ2 = new JRadioButton("Joueur Texte");
		choixJoueurDeux.add(radioTexteJ2);
		joueurDeuxGrille.add(radioTexteJ2);

		radioAutoJ2 = new JRadioButton("Joueur Auto");
		radioAutoJ2.setSelected(true);
		choixJoueurDeux.add(radioAutoJ2);
		joueurDeuxGrille.add(radioAutoJ2);

		JPanel startLayout = new JPanel();
		mainNorth.add(startLayout, BorderLayout.SOUTH);

		startButton = new JButton("Lancer la partie");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fieldTailleGrille.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Il ne faut pas de taille vide",
							"Taille vide", JOptionPane.ERROR_MESSAGE);
				}
				
				tailleGrille = Integer.parseInt(fieldTailleGrille.getText());
				String nomJoueurUn = fieldNomJoueur1.getText();
				String nomJoueurDeux = fieldNomJoueur2.getText();
				/*
				 * bug général, lorsque qu'on tire sur une coord déjà tiré, on peu toujours
				 * tirer dessus, dans ce cas, le tour passe directement au joueur suivant et ne
				 * donne pas une alerte d'info disant que la coord a déjà été tiré dessu et
				 * redonne une autre chance pour que le joueur séléctionne une autre coord libre
				 */
				
				int[] navires = creerNavires(tailleGrille);
				
				if (navires[0] == 0) {
					return;
				}
				
				//int[] navires = new int[] { 2, 2, 3, 3, 4, 5 };
				/*
				if (tailleGrille >= 10 && tailleGrille <= 26) {
					if (checkNavireGen.isSelected()) {
						navires = navireGen(tailleGrille);
					}
				} else if (tailleGrille >= 6 && tailleGrille < 10) {
					navires = new int[] {2,2,3};
				} else {
					JOptionPane.showMessageDialog(null,"La taille de la grille doit être comprise entre 6 et 26 inclus !");
					return;
				}
				*/
				
				if (radioGraphiqueJ1.isSelected()) {
					FenetreJoueur fenetreJoueur1 = new FenetreJoueur(nomJoueurUn, tailleGrille);
//					fenetreJoueur1.setVisible(true);

					GrilleNavaleGraphique gng1 = fenetreJoueur1.getGrilleDefense();
					GrilleGraphique gg1 = fenetreJoueur1.getGrilleTirs();

					joueur1 = new JoueurGraphique(gng1, gg1, nomJoueurUn);

					int hz = fenetreJoueur1.getWidth() / 2;
					int vert = fenetreJoueur1.getHeight() / 2;
					int x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - hz;
					int y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - vert;
					fenetreJoueur1.setLocation((int) (x - (x * 0.5)), y);

					if (checkPlacementAuto.isSelected()) {
						placementManuelFini = true;
						gng1.placementAuto(navires);
						frmBataillenavale.setVisible(false);
						fenetreJoueur1.setVisible(true);
					} else {
						fenetreJoueur1.setVisible(true);
//						gg1.setClicActive(false);
						new Thread() {
							public void run() {
//								gg1.setClicActive(false);
								boolean placementPossible;
								for (int i = 0; i < navires.length; i++) {
									placementPossible = false;
									for (; !placementPossible;) {
										Coordonnee debut = gng1.getGrilleGraphique().getCoordonneeSelectionnee();
										placementPossible = gng1.placementManuel(debut, navires[i], gng1.getGrilleGraphique().getPlacementVertical());
									}
									
								}
//								gg1.setClicActive(true);
								placementManuelFini = true;
							}
						}.start();
//						placementManuelFini = true;
					}
					
				}
				
				if (radioGraphiqueJ2.isSelected()) {
					FenetreJoueur fenetreJoueur2 = new FenetreJoueur(nomJoueurDeux, tailleGrille);

					GrilleNavaleGraphique gng2 = fenetreJoueur2.getGrilleDefense();
					GrilleGraphique gg2 = fenetreJoueur2.getGrilleTirs();
					joueur2 = new JoueurGraphique(gng2, gg2, nomJoueurDeux);

					int hz = fenetreJoueur2.getWidth() / 2;
					int vert = fenetreJoueur2.getHeight() / 2;
					int x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - hz;
					int y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - vert;
					fenetreJoueur2.setLocation((int) (x + (x * 0.5)), y);

					if (checkPlacementAuto.isSelected()) {
						gng2.placementAuto(navires);
						frmBataillenavale.setVisible(false);
						fenetreJoueur2.setVisible(true);
					} else {
						fenetreJoueur2.setVisible(true);
						new Thread() {
							public void run() {
								boolean placementPossible;
								for (int i = 0; i < navires.length; i++) {
									placementPossible = false;
									for (; !placementPossible;) {
										Coordonnee debut = gng2.getGrilleGraphique().getCoordonneeSelectionnee();
										placementPossible = gng2.placementManuel(debut, navires[i], gng2.getGrilleGraphique().getPlacementVertical());
									}
								}
							}
						}.start();
						
					}
					
				}
				
				if (radioTexteJ1.isSelected()) {
					GrilleNavale grilleNavaleJ1 = new GrilleNavale(tailleGrille, navires);
					joueur1 = new JoueurTexte(grilleNavaleJ1, nomJoueurUn);
				}
				
				
				
				if (radioTexteJ2.isSelected()) {
					GrilleNavale grilleNavaleJ2 = new GrilleNavale(tailleGrille, navires);
					joueur2 = new JoueurTexte(grilleNavaleJ2, nomJoueurDeux);
				}
				if (radioAutoJ1.isSelected() && radioAutoJ2.isSelected()) {
					JOptionPane.showMessageDialog(null, "Il faudrait au moins un joueur ordinaire!",
							"Alerte Terminator VS Skynet", JOptionPane.ERROR_MESSAGE);
					throw new IllegalArgumentException("2 Joueurs auto non permis!");
				}

				if (radioAutoJ1.isSelected() && !radioAutoJ2.isSelected()) {
					GrilleNavale grilleNavaleJ1 = new GrilleNavale(tailleGrille, navires);

					joueur1 = new JoueurAuto(grilleNavaleJ1, nomJoueurUn);
				}

				if (!radioAutoJ1.isSelected() && radioAutoJ2.isSelected()) {
					GrilleNavale grilleNavaleJ2 = new GrilleNavale(tailleGrille, navires);
					joueur2 = new JoueurAuto(grilleNavaleJ2, nomJoueurDeux);
				}
				
				
				new Thread() {
					public void run() {
						demarrerPartie();
					}
				}.start();
				
			
				
//			demarrerPartie();
			}
		});
		startLayout.add(startButton);

	}
	
	public int[] creerNavires(int tailleGrille) {
		
		int[] navires;
		if (tailleGrille >= 10 && tailleGrille <= 26) {
			if (checkNavireGen.isSelected()) {
				return navires = navireGen(tailleGrille);
			}
			return navires = new int[] {2,2,3,3,4,5};
		} else if (tailleGrille >= 6 && tailleGrille < 10) {
			return navires = new int[] {2,2,3};
		} else {
			JOptionPane.showMessageDialog(null,"La taille de la grille doit être comprise entre 6 et 26 inclus !");
			return navires = new int[] {0};
		}
	}

}
