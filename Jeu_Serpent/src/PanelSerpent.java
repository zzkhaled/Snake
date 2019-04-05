import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelSerpent extends JPanel implements KeyListener , ActionListener {
	
	private static final long serialVersionUID = 1L;

	private Serpent serpent;
	private Pomme pomme ;
	private int tailleCell ;
	private int tailleBord = 150;
	private int pixelGrille ;
	private int time = 0 ; 
	private boolean toucheEnAttente = false ; 
	private int decalY = 100 ;
	private int score = 0 ;
	Timer timerCligne ;
	Timer vitesseSerpent ;
	Timer timer ;
	JButton bouton = new JButton("Rejouer");
	private int tic = 0 ;
	private boolean pause = false;
	
	
	Font titleFont = new Font("Comic Sans MS",Font.BOLD , 40) ;
	Font hudFont = new Font("arial", Font.PLAIN, 20 ) ; 
	Font pauseFont = new Font("AppleGothic" , Font.BOLD , 40) ; 
	
	Image snake = Toolkit.getDefaultToolkit().createImage("funSnake.png");
	Image img = Toolkit.getDefaultToolkit().createImage("herbe.jpg");


	public PanelSerpent()  {
		setBackground(Color.white.darker());
		
		
		this.add(bouton);
		bouton.addActionListener(this);

		serpent = new Serpent(30);	
		tiragePomme() ; 
		
		tailleCell = (800 - 2 * tailleBord ) / serpent.getTailleGrille() ;
		pixelGrille = serpent.getTailleGrille()*tailleCell ; 
		
		ActionListener mouvement = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serpent.avance();
				toucheEnAttente = false ; 
				repaint();
			}
		};
		vitesseSerpent = new Timer(100,mouvement);
		vitesseSerpent.start();
		

		ActionListener clock = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				time++ ; 
				repaint() ; 
			}
		};
		timer = new Timer(vitesseSerpent.getDelay()/10 , clock);
		timer.start() ; 

		ActionListener mange = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(serpent.teteEn(pomme.getLigne(), pomme.getColonne())) {
					serpent.mange(pomme.getLigne(), pomme.getColonne()); 
					score += 50 + bonusTime() ; 
					tiragePomme() ; 
					time = 0 ; 
					timer.restart() ; 
					repaint();
				}		
			}
		};
		Timer check = new Timer(40,mange);
		check.start();
		
		ActionListener mord = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(serpent.mord()) {

					vitesseSerpent.stop() ; 
					timerCligne.start() ; 
				}	
			}
		};
		Timer check2 = new Timer(40,mord);
		check2.start();
		
		ActionListener clignoter = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				tic++ ; 
			}
		};
		timerCligne = new Timer(400 , clignoter);
		


		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		
	} 
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode() ;
		
		
		int value = serpent.getDirection().getValue() ;

		if(!toucheEnAttente) {
			if(key == KeyEvent.VK_UP && value != Direction.S)
				value = Direction.N;
			if(key == KeyEvent.VK_RIGHT && value != Direction.O)
				value = Direction.E;
			if(key == KeyEvent.VK_DOWN && value != Direction.N)
				value = Direction.S;
			if(key == KeyEvent.VK_LEFT && value != Direction.E)
				value = Direction.O;
			
			toucheEnAttente = true ; 
			serpent.setDirection(new Direction(value));	
			
		}
		
		if(key == 32) {
			if(pause) {
				pause = false ; 
				vitesseSerpent.start();
				timer.start() ;
				repaint() ; 
			}
				
			else {
				pause = true ; 
				vitesseSerpent.stop();
				timer.stop() ; 
				repaint() ; 
			}
		}
		
	}


	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		graphics.drawImage(img, tailleBord, tailleBord + decalY , pixelGrille , pixelGrille ,  null);
		graphics.drawImage(snake , 250 ,20 , -100 , 100 , null);
		graphics.drawImage(snake , 550 ,20 , 100 , 100 , null);

		graphics.setColor(Color.darkGray) ;
		graphics.setFont(titleFont);
		graphics.drawString("SERPENT CCI", 265, 75);
		
		
		
		dessineSerpent(graphics) ; 
		dessinePomme(pomme,graphics);
		dessineHUD(graphics) ; 
		
	}

	private void dessineHUD(Graphics graphics) {
		
		int barreBonus = 150 * bonusTime() / 400 ; 
		
		graphics.setFont(hudFont) ; 
		
		if(tic %2 == 0)
			graphics.setColor(Color.RED);
		else
			graphics.setColor(Color.YELLOW);
			
		graphics.drawString("SCORE : " + score , 350 , 150 );
		
		graphics.setColor(Color.GREEN);
		graphics.fillRect(328 , 170 , barreBonus , 15) ; 
		graphics.drawString("Bonus "+bonusTime() , 498 , 184 );
		graphics.setColor(Color.BLACK);
		graphics.drawRect(328 , 170 , 150 , 15) ; 
		
		if(pause) {
			graphics.setColor(Color.BLACK) ;
			graphics.setFont(pauseFont) ;
			graphics.drawString("PAUSE" , tailleBord + pixelGrille / 2 - 50 , tailleBord + decalY + pixelGrille / 2) ;
			
		}
	}

	private void dessineSerpent(Graphics graphics) {
	
		for (BoutDeSerpent bout : serpent.getListeDeBouts()) {
			int x = getX(bout.getColonne());
			int y = getY(bout.getLigne());
			
			if(tic %2 == 0)
				graphics.setColor(Color.YELLOW);
			else
				graphics.setColor(Color.BLACK);
					
			graphics.fillRect(x, y, tailleCell, tailleCell) ;
			graphics.setColor(Color.darkGray);
			graphics.drawRect(x, y, tailleCell, tailleCell) ;
			
			 
			
			if(bout.equals(serpent.getListeDeBouts().firstElement())) {
				graphics.setColor(Color.BLACK);
				graphics.fillOval(x+tailleCell/2, y, tailleCell/2, tailleCell/2);
				
			}
		}
	}

	int getX(int colonne) {
		return tailleBord + tailleCell * colonne ; 
	}

	int getY(int ligne) {
		return tailleBord + decalY + tailleCell * ligne ;
	}

	
	void dessinePomme(Pomme pomme , Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.fillOval(getX(pomme.getColonne()),getY(pomme.getLigne()),tailleCell,tailleCell);
		
	}
	
	void tiragePomme() {
		
		Random random = new Random();
		int ligne = random.nextInt(serpent.getTailleGrille()) ;
		int colonne = random.nextInt(serpent.getTailleGrille()) ;
		
	    while (serpent.boutEn(ligne , colonne)) {
	    	ligne = random.nextInt(serpent.getTailleGrille()) ;
			colonne = random.nextInt(serpent.getTailleGrille()) ;
	    }
		pomme = new Pomme(ligne , colonne);
	}
	
	private int bonusTime() {
		if(time > 400)
			return 0;
		return 400 - time ; 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		serpent = new Serpent(30) ; 
		time = 0 ; 
		tic = 0 ; 
		score = 0 ; 
		timerCligne.stop() ; 
		vitesseSerpent.restart() ; 
		this.setFocusable(true);
		this.requestFocus();
	}
	
	
}
