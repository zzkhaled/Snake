import java.util.Vector;

public class Serpent {
	
	private Vector<BoutDeSerpent> listeDeBouts ;
	private int tailleGrille ; 
	private Direction direction ;
	
	public Serpent(int taille) {
		this.tailleGrille = taille ;
		this.direction = new Direction(Direction.E) ;
		initialiser();
	}
		
	private void initialiser() {
		int ligneCentre = getTailleGrille() /2 ;
		int colonneCentre = getTailleGrille() /2 ;
		listeDeBouts = new Vector<BoutDeSerpent>() ;
		
		for(int index = 0; index < 3; index++) {
			BoutDeSerpent bout = new BoutDeSerpent(ligneCentre, colonneCentre - index) ;
			listeDeBouts.add(bout);	
		}
	}
	
	public int getTailleGrille() {
		return tailleGrille;
	}
		
	public void avance() {
		BoutDeSerpent nouvTete = listeDeBouts.get(0).avance(direction) ;

		nouvTete.corige(tailleGrille,tailleGrille) ;
		
		for(int b = listeDeBouts.size() -1 ; b > 0 ; b--) {
			BoutDeSerpent bout = listeDeBouts.get(b-1);
			listeDeBouts.set(b, bout);
		}
		
		listeDeBouts.set(0, nouvTete) ;					
	}
				
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Vector<BoutDeSerpent> getListeDeBouts() {
		return listeDeBouts ;
	}
	
	private String chaineEn(int ligne , int colonne) {
		
		for(BoutDeSerpent bout : listeDeBouts) 
			if(bout.getLigne() == ligne && bout.getColonne() == colonne)
				return "*" ;
		
		return "0" ;	
	}
	
	@Override
	public String toString() {
		
		String chaine = "" ;
		for(int ligne = 0 ; ligne < tailleGrille ; ligne++)
		{	
			for(int colonne = 0 ; colonne < tailleGrille ; colonne++)
				chaine += chaineEn(ligne,colonne);
			chaine += '\n' ;
		}		
		return chaine;
	}
	
	public boolean boutEn(int ligne , int colonne) {
		for(BoutDeSerpent bout : listeDeBouts) {
			if(bout.getLigne() == ligne  && bout.getColonne() == colonne)
				return true ; 
		}
		return false ; 
		
	}
	
	public boolean teteEn(int ligne , int colonne) {
		if(listeDeBouts.firstElement().getLigne() == ligne  && listeDeBouts.firstElement().getColonne() == colonne)
				return true ; 
		
		return false ; 
	}

	public void mange(int ligne , int colonne) {
		listeDeBouts.add(new BoutDeSerpent(ligne,colonne)) ; 
		
	}

	public boolean mord() {
		
		BoutDeSerpent tete = listeDeBouts.get(0) ; 
		
		for(int i = 1 ; i < listeDeBouts.size()-1 ; i++) {
			
			if(tete.equals(listeDeBouts.get(i)))
				return true ; 
		}

		return false ;
	}
	
}
