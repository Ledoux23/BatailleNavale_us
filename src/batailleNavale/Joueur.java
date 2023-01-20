package batailleNavale;

public abstract class Joueur {
	public final static int TOUCHE = 1;
	public final static int COULE = 2;
	public final static int A_L_EAU = 3;
	public final static int GAMEOVER = 4;
	private Joueur adversaire;
	private int tailleGrille;
	private String nom;

	public Joueur(int tailleGrille, String nom) {
		if ( (tailleGrille > 26 ||  tailleGrille <= 0) || nom==null )
			throw new IllegalArgumentException("- La taille de la grille doit être comprises entre 1 et 26\n"
					+ "- Le nom nécessite une valeur non nulle");
		this.tailleGrille = tailleGrille;
		this.nom = nom;
	}

	public Joueur(int tailleGrille) {
		this(tailleGrille, "Joueur");
	}

	public int getTailleGrille() {
		return tailleGrille;
	}

	public String getNom() {
		return nom;
	}

	public void jouerAvec(Joueur j) {
		//try{
			//Définir le joueur adverse du Joueur this (joueur J);
			this.adversaire = j;
			
			//Définir le joueur adverse du joueur j (Joueur This);
			j.adversaire= this;
			
			/*Extension: cas où les 2 joueurs n'ont pas entrés de nom, c'est-à-dire qu'ils ont tous les deux le nom par défaut (i.e. Joueur).
			 * 
			 * Faire en sorte que les 2 joueur ont 2 noms différents et que chaque joueur se voie comme étant le joueur-1 dans sa perspective 
			 * et son joueur adverse est le joueur-2
			 * 
			 * */
			if (this.getNom().equals("Joueur") && j.getNom().equals("Joueur")) {
				this.nom = "Joueur-1";
				this.adversaire.nom = "Joueur-2";
			}
			
			//Lien établi avec succès entre les 2 joueurs
			deroulementJeu(this, j);
			
//		} catch (Exception e) {
//			System.out.println("Le lien entre les 2 joueurs n'a pu être établi.");
//		}
	}

	private static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
		int res = 0;
		
		while (res != GAMEOVER) {
			Coordonnee c = attaquant.choixAttaque();
			res = defenseur.defendre(c);
			attaquant.retourAttaque(c, res);
			defenseur.retourDefense(c, res);
			Joueur x = attaquant;
			attaquant = defenseur;
			defenseur = x;
		}
	}

	protected abstract void retourAttaque(Coordonnee c, int etat);

	protected abstract void retourDefense(Coordonnee c, int etat);

	public abstract Coordonnee choixAttaque();

	public abstract int defendre(Coordonnee c);
	
}