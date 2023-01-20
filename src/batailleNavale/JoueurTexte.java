package batailleNavale;

import java.util.Scanner;

public class JoueurTexte extends JoueurAvecGrille {

	private Scanner sc;

	public JoueurTexte(GrilleNavale g, String nom) {

		super(g, nom);
		sc = new Scanner(System.in);

	}

	public JoueurTexte(GrilleNavale g) {

		super(g);
		sc = new Scanner(System.in);

	}

	protected void retourAttaque(Coordonnee c, int etat) {
		String res = "";
		if (etat == Joueur.TOUCHE) {
			res = "Beau tir : vous avez touché un navire";
		} else if (etat == Joueur.COULE) {
			res = "Vous avez coulé un navire, ";
		} else if (etat == Joueur.A_L_EAU) {

			res = "Un peu plus de précision la prochaine fois : votre tir est tombé à l'eau";

		} else if (etat == Joueur.GAMEOVER) {

			res = "Vous avez gagné la partie";

		}
		System.out.println("Tir en " + c + " : " + res);
	}

	protected void retourDefense(Coordonnee c, int etat) {
		String res = "";
		if (etat == Joueur.TOUCHE) {
			res = "Aïe, votre navire a été touché";
		} else if (etat == Joueur.COULE) {
			res = "Votre navire a été coulé :(";
		} else if (etat == Joueur.A_L_EAU) {

			res = "Let's gooooo, le tir adverse n'a rien touché !";

		} else if (etat == Joueur.GAMEOVER) {

			res = "Revenez quand vous aurez le niveau matelot : vous avez perdu la partie";

		}
		System.out.println("Tir adverse en " + c + " : " + res);
	}

	public Coordonnee choixAttaque() {
		System.out.println("Grille de " + getNom());
		System.out.println(this.getGrille());
		System.out.println ("Veuillez saisir une coordonnée à attaquer :");
		boolean coordPossible = false;
		Coordonnee c = new Coordonnee("A1");
		while (!coordPossible) {
			String str = sc.nextLine();
			try {
				c = new Coordonnee (str);
				coordPossible = true;
			} catch (IllegalArgumentException e) {
				
			}
		}
		return c ;
	}

	public static void main(String[] args) {
				
	}
}