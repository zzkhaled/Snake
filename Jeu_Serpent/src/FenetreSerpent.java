import javax.swing.JFrame;

public class FenetreSerpent extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public FenetreSerpent() {
		setTitle("Serpent");
		setSize(800, 800);
		add(new PanelSerpent());
	}
}
