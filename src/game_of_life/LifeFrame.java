package game_of_life;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The LifeFrame class extends JFrame and serves as the main window for the Conway's Game of Life application.
 * It contains the game panel, menu, and status bar.
 */
public class LifeFrame extends JFrame {
	private JLabel statusLabel;
	
    /**
     * Constructs a LifeFrame which sets up the main window, including the game panel, menu, and status bar.
     */
    public LifeFrame() {
    	// Create a status bar panel and add it to the frame
    	JPanel statusBar = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status: ");
        statusBar.add(statusLabel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        // Create the game panel and add it to the frame
        LifePanel lifePanel = new LifePanel();
        add(lifePanel);

        // Set up game functions and menu bar
        GameFunctions gameFunctions = new GameFunctions(lifePanel, this);
        setJMenuBar(new GameMenu(gameFunctions));

        // Configure frame settings
        setSize(lifePanel.xPanel - 5, lifePanel.yPanel + 17);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Updates the text displayed in the status bar.
     *
     * @param text The text to display in the status bar.
     */
    public void updateStatus(String text) {
        statusLabel.setText("Status: " + text);
    }
    
    /**
     * Retrieves the current text displayed in the status bar.
     *
     * @return The current status text.
     */
    public String getStatusText() {
        return statusLabel.getText();
    }
}