package batailleNavale;

public class JoueurRandom extends JoueurAvecGrille {
	
	int nbTirs = -1;
	Coordonnee [] tirsDonnes = new Coordonnee [this.getTailleGrille() * this.getTailleGrille()];
	
	
	public JoueurRandom (GrilleNavale g, String nom) {
		super(g, nom);
	}
	
	public JoueurRandom(GrilleNavale g) {
		super(g);
		
	}
	
	protected void retourAttaque(Coordonnee c, int etat) {
		tirsDonnes[nbTirs] = c;
		
	}	
	
	protected void retourDefense(Coordonnee c, int etat) {
		
	}
	
	public Coordonnee choixAttaque() {
		nbTirs ++;
		
		return tirRandom();
	}
		

		
	public Coordonnee tirRandom() {
		int ligneRandom = (int)(Math.random() * this.getTailleGrille());
		int colonneRandom = (int)(Math.random() * this.getTailleGrille());
		Coordonnee x = new Coordonnee(ligneRandom, colonneRandom);
		
		if (contient(x, tirsDonnes)) {
			return tirRandom();
		}
		else {
		return x;
		}
	}
	
	public boolean contient (Coordonnee c, Coordonnee[] x) {
		for (int i = 0; i < x.length; i++) {
			if (c.equals(x[i]))
				return true;
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
