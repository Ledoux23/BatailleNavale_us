package batailleNavale;

import java.util.Scanner;


public class JoueurAuto extends JoueurAvecGrille {

	private Scanner sc = new Scanner(System.in);
	
	int res;
	
	int nbTirs = -1;
	int premiereTouche = 0;
	
	Coordonnee [] tirsDonnes = new Coordonnee [this.getTailleGrille() * this.getTailleGrille()];
	int [] resTirs = new int [this.getTailleGrille() * this.getTailleGrille()];
	char [] typeTirs = new char [this.getTailleGrille() * this.getTailleGrille()];
	
	Coordonnee [] casesEliminees = new Coordonnee [this.getTailleGrille()* this.getTailleGrille()];
	int nbConnues = 0;
	
	
	JoueurAuto(GrilleNavale g, String nom) {
		super(g, nom);
	}
	
	public JoueurAuto(GrilleNavale g) {
		super(g);
		
	}
	
	protected void retourAttaque(Coordonnee c, int etat) {
		tirsDonnes[nbTirs] = c;
		resTirs[nbTirs] = etat;
		
		
		if(etat == Joueur.COULE) {
			voisineCoule();			
		}
		
		
	}	
	
	protected void retourDefense(Coordonnee c, int etat) {
		
	}
	
	public Coordonnee choixAttaque() {
		nbTirs ++;
				
		
		if (nbTirs == 0) {
			return tirDir(0, "random");
		}
		else if (nbTirs == 1) {
			if (resTirs[nbTirs-1] == Joueur.COULE || resTirs[nbTirs-1] == Joueur.A_L_EAU)
				return tirDir(0, "random");
			else
				return tirDir(nbTirs-1, "droite");
		}		
		else if (resTirs[nbTirs-1] == Joueur.COULE) {
			return tirDir(0, "random");

		}
		else if (resTirs[nbTirs-1] == Joueur.TOUCHE) {
			if (premiereTouche == 0) {
				premiereTouche = nbTirs-1;
				return tirDir(nbTirs-1, "droite");
			} else {
				return sulfateuse();
			}
			
		}
		
		else if (resTirs[nbTirs-1] == Joueur.A_L_EAU && typeTirs[nbTirs-1] != 'r') { //resTirs[nbTirs-2] == Joueur.TOUCHE) {
			if(typeTirs[nbTirs-1] =='d') {
				return tirDir(premiereTouche, "gauche");
			}
			else if(typeTirs[nbTirs-1] =='g') {
				return tirDir(premiereTouche, "haut");
			}
			else {
				return tirDir(premiereTouche, "bas");
			}
		}
		
		else if (resTirs[nbTirs - 1] == Joueur.A_L_EAU) {
			return tirDir(0, "random");
		}
		
		else
			return tirDir(0, "random");

		
	}
	
	public Coordonnee sulfateuse() {
		if (typeTirs[nbTirs-1] == 'd') {
			return tirDir(nbTirs-1, "droite");
		} else if (typeTirs[nbTirs-1] == 'g') {
			return tirDir(nbTirs-1, "gauche");
		} else if (typeTirs[nbTirs-1] == 'h') {
			return tirDir(nbTirs-1, "haut");
		} else {
			return tirDir(nbTirs-1, "bas");
		}
	}
	
	public boolean contient (Coordonnee c, Coordonnee[] x) {
		for (int i = 0; i < x.length; i++) {
			if (c.equals(x[i]))
				return true;
		}
		return false;
	}
	

	public Coordonnee tirDir (int indiceTouche, String s) {
		
		
		if (s.equals("droite")) {
				Coordonnee x = new Coordonnee (tirsDonnes[indiceTouche].getLigne(), tirsDonnes[indiceTouche].getColonne() + 1);
				if (contient(x, tirsDonnes) || contient(x, casesEliminees) || limite(x))
					return tirDir (premiereTouche, "gauche");
				else {
					typeTirs[nbTirs] = 'd';
					return x;
				}

		}
		else if (s.equals("gauche")) {
			Coordonnee x = new Coordonnee (tirsDonnes[indiceTouche].getLigne(), tirsDonnes[indiceTouche].getColonne() - 1);
			if (contient(x, tirsDonnes) || contient(x, casesEliminees) || limite(x))
				return tirDir(premiereTouche, "haut");
			else {
				typeTirs[nbTirs] = 'g';
				return x;
				}
		}
		else if (s.equals("haut")) {

			Coordonnee x = new Coordonnee (tirsDonnes[indiceTouche].getLigne() - 1, tirsDonnes[indiceTouche].getColonne());
			if (contient(x, tirsDonnes) || contient(x, casesEliminees) || limite(x))
				return tirDir(premiereTouche, "bas");
			else {
				typeTirs[nbTirs] = 'h';
				return x;
				}
		}
		else if (s.equals("bas")) {
			Coordonnee x = new Coordonnee (tirsDonnes[indiceTouche].getLigne() + 1, tirsDonnes[indiceTouche].getColonne());
			if (contient(x, tirsDonnes) || contient(x, casesEliminees) || limite(x))
				return tirDir(0, "random");
			else {
				typeTirs[nbTirs] = 'b';
				return x;
				}
		}
		
		else {
			int ligneRandom = (int)(Math.random() * this.getTailleGrille());
			int colonneRandom = (int)(Math.random() * this.getTailleGrille());
			Coordonnee x = new Coordonnee(ligneRandom, colonneRandom);
			
			if (contient(x, tirsDonnes) || contient(x, casesEliminees)) {
				return tirDir(0, "random");
			}

			else {
			typeTirs[nbTirs] = 'r';
			return x;
			}
		}
	}
	
	public void voisineCoule() {
		
		for (int i = premiereTouche; i <= nbTirs; i++)
			if (resTirs[i]== Joueur.TOUCHE || resTirs[i] == Joueur.COULE) {
				Coordonnee c1 = new Coordonnee(tirsDonnes[i].getLigne(), tirsDonnes[i].getColonne()+1);						
				casesEliminees[nbConnues] = c1;
				nbConnues++;
				
				Coordonnee c2 = new Coordonnee(tirsDonnes[i].getLigne(), tirsDonnes[i].getColonne()-1);						
				casesEliminees[nbConnues] = c2;
				nbConnues++;
				
				Coordonnee c3 = new Coordonnee(tirsDonnes[i].getLigne()+1, tirsDonnes[i].getColonne());						
				casesEliminees[nbConnues] = c3;
				nbConnues++;
				
				Coordonnee c4 = new Coordonnee(tirsDonnes[i].getLigne()-1, tirsDonnes[i].getColonne());						
				casesEliminees[nbConnues] = c4;
				nbConnues++;
			}
		
		premiereTouche = 0;
	}
	
	public void premiereTouche() {
		if(resTirs[nbTirs]==1 && resTirs[nbTirs-1]==2)
			premiereTouche = nbTirs;
		else if (resTirs[nbTirs]==1 && resTirs[nbTirs-1]==3 && resTirs[nbTirs-2]==3)
			premiereTouche = nbTirs;
	}
	
	public boolean limite(Coordonnee c) {
		if(c.getLigne() >= getGrille().getTaille() || c.getLigne() < 0) {
			return true;
		}
		else if (c.getColonne() >= getGrille().getTaille() || c.getColonne() < 0) {
			return true;
		}
		return false;
	}


	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
