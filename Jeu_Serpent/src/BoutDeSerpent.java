
public class BoutDeSerpent {
	private int ligne;
	private int colonne;
	
	public BoutDeSerpent(int ligne , int colonne) {
		this.ligne = ligne ;
		this.colonne = colonne ;
		
	}

	public BoutDeSerpent() {
		ligne = -1 ;
		colonne = -1;
	}
	
	public BoutDeSerpent avance(Direction direction) {
		
		BoutDeSerpent bout = new BoutDeSerpent(ligne,colonne) ;
		
		if(direction.value == Direction.N)
			bout.ligne--;
		if(direction.value == Direction.S)
			bout.ligne++;
		if(direction.value == Direction.E)
			bout.colonne++;
		if(direction.value == Direction.O)
			bout.colonne--;
		
		return bout ;
	}
	
	public void corige(int nbLignes , int nbcolonnes) {
	
		if(ligne < 0)
			ligne = nbLignes - 1 ;
		if(ligne == nbLignes)
			ligne = 0 ;
		if(colonne < 0)
			colonne = nbcolonnes - 1 ;
		if(colonne == nbcolonnes)
			colonne = 0 ;		
	}
	
	public int getLigne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}
	
	public boolean equals(BoutDeSerpent b) {
		
		if(this.ligne == b.ligne && this.colonne == b.colonne)
			return true ; 
		
		return false ; 
	}
}
