package game_of_life;

/**
 * The Main class is the entry point for the Conway's Game of Life application.
 * This class contains the main method which initializes and displays the main window of the game.
 */
public class Main {
    
	/**
     * The main method that serves as the entry point for the application.
     * It creates an instance of LifeFrame and makes it visible, thereby starting the game.
     *
     * @param args Command-line arguments, not used in this application.
     */
	public static void main(String[] args) {
        LifeFrame frame = new LifeFrame();
        frame.setVisible(true);
    }
}