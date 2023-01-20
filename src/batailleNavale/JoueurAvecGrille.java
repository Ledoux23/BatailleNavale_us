package batailleNavale;

public abstract class JoueurAvecGrille extends Joueur {
	
	private GrilleNavale grille;
	
	//constructeurs
	public JoueurAvecGrille(GrilleNavale g, String nom) {
		super(g.getTaille(), nom);
		this.grille = g;	
	}
	
	public JoueurAvecGrille(GrilleNavale g) {
		this(g, "Joueur");
	}
	
	//acceder à la grille
	public GrilleNavale getGrille() {
		return grille;
	}

	//méthode défendre
	public int defendre(Coordonnee c) {
		grille.recoitTir(c);

		if (grille.perdu()) {
			return GAMEOVER;
		}
		if (grille.estCoule(c)) {
			PlaySound.coule();
			return COULE;
		}
		if (grille.estTouche(c)) {
			PlaySound.boom();
			return TOUCHE;
		} else {
			//PlaySound.plouf();
			return A_L_EAU;
		}
	}
	
}
