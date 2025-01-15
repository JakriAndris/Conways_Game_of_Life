package game_of_life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Timer;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The GameFunctions class contains the core functionality for managing the game state, 
 * including starting and stopping the game, manipulating the game board, and handling 
 * game settings like rules and grid size.
 */
public class GameFunctions {
    private LifePanel lifePanel;
    private LifeFrame lifeFrame;
    
    /**
     * Constructs a new GameFunctions object with references to the LifePanel and LifeFrame.
     *
     * @param lifePanel The panel where the game is drawn.
     * @param lifeFrame The frame that contains the panel.
     */
    public GameFunctions(LifePanel lifePanel, LifeFrame lifeFrame) {
        this.lifePanel = lifePanel;
        this.lifeFrame = lifeFrame;
        
        lifePanel.gameTimer = new Timer(500, lifePanel);
        updateStatusLine();
    }
    
    /**
     * Starts the game by setting the game running state to true and starting the game timer.
     */
    public void startGame() {
        lifePanel.gameRunning = true;
        lifePanel.gameTimer.start();
        updateStatusLine();
    }
    
    /**
     * Stops the game by setting the game running state to false and stopping the game timer.
     */
    public void stopGame() {
    	lifePanel.gameRunning = false;
        if (lifePanel.gameTimer != null && lifePanel.gameTimer.isRunning()) {
            lifePanel.gameTimer.stop();
        }
        updateStatusLine();    
    }

    /**
     * Clears the game board, setting all cells to a dead state.
     */
    public void clearBoard() {
        for (int i = 0; i < lifePanel.xWidth; i++) {
            for (int j = 0; j < lifePanel.yHeight; j++) {
                lifePanel.life[i][j] = false;
                lifePanel.beforeLife[i][j] = false;
            }
        }
        lifePanel.repaint();
    }

    /**
     * Randomizes the board by setting each cell to a random state.
     */
    public void randomizeBoard() {
        for (int i = 0; i < lifePanel.xWidth; i++) {
            for (int j = 0; j < lifePanel.yHeight; j++) {
                lifePanel.beforeLife[i][j] = (Math.random() < 0.2);
            }
        }
        lifePanel.copyArray();
        lifePanel.repaint();
    }
    
    /**
     * Saves the current state of the game board to a file.
     */
    public void saveGridToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (!filename.endsWith(".ser")) {
                filename += ".ser";
            }
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(lifePanel.beforeLife);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Loads the game board state from a file.
     */
    public void loadGridFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                boolean[][] loadedLife = (boolean[][]) ois.readObject();
                if (loadedLife.length == lifePanel.beforeLife.length && loadedLife[0].length == lifePanel.beforeLife[0].length) {
                	lifePanel.beforeLife = loadedLife;
                	lifePanel.copyArray();
                	lifePanel.repaint();
                } else {
                	boolean sizeMatched = false;
                	int option = JOptionPane.showConfirmDialog(null, "The loaded grid size does not match. Do you want to resize the current grid to match the loaded file?", "Resize Grid?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    	if (option == JOptionPane.YES_OPTION) {
                    		for (int predefinedSize : new int[]{64, 32, 16, 8, 4}) {
                    			if (loadedLife.length == lifePanel.xPanel / predefinedSize && loadedLife[0].length == lifePanel.yPanel / predefinedSize) {
                    				sizeMatched = true;
                    				lifePanel.resizeGrid(predefinedSize);
                    				lifePanel.beforeLife = loadedLife;
                    				lifePanel.copyArray();
                    				lifePanel.repaint();
                    				break;
                    			}
                    		}
                    	}
                    if (!sizeMatched) {
                        JOptionPane.showMessageDialog(null, "The loaded grid size does not match any predefined grid sizes.", "Error Loading File", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Sets the time between each generation in the game.
     */
    public void setGenerationTime() {
        String time = JOptionPane.showInputDialog(null, "Enter time between generations (ms):", "Set Time", JOptionPane.QUESTION_MESSAGE);
        if (time != null) {
            try {
                int delay = Integer.parseInt(time);
                setTimerDelay(delay);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sets the timer delay for the game.
     *
     * @param delay The delay in milliseconds.
     */
    public void setTimerDelay(int delay) {
        if (lifePanel.gameTimer != null) {
            lifePanel.gameTimer.setDelay(delay);
        }
        updateStatusLine();
    }
    
    /**
     * Allows the user to select the grid size for the game.
     */
    public void setGridSize() {
        Object[] options = {"Extra Small", "Small", "Medium", "Large", "Extra Large"};
        int response = JOptionPane.showOptionDialog(null, "Choose grid size:", "Set Grid Size", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        switch (response) {
            case 0: lifePanel.size = 64; break;
            case 1: lifePanel.size = 32; break;
            case 2: lifePanel.size = 16; break;
            case 3: lifePanel.size = 8; break;
            case 4: lifePanel.size = 4; break;
            default: return;
        }
        updateGridSize();
    }
    
    /**
     * Updates the grid size based on the user's selection.
     */
	public void updateGridSize() {
        lifePanel.xWidth = lifePanel.xPanel / lifePanel.size;
        lifePanel.yHeight = lifePanel.yPanel / lifePanel.size;
        lifePanel.life = new boolean[lifePanel.xWidth][lifePanel.yHeight];
        lifePanel.beforeLife = new boolean[lifePanel.xWidth][lifePanel.yHeight];
        lifePanel.repaint();
    }
	
    /**
     * Sets the birth and survival rules for the game.
     */
    public void setRules() {
        JTextField birthField = new JTextField(5);
        JTextField survivalField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("B"));
        myPanel.add(birthField);
        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("S"));
        myPanel.add(survivalField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Birth and Survival Rules", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String birthRules = birthField.getText();
            String survivalRules = survivalField.getText();
            
        	parseAndSetRules(birthRules, survivalRules);
        	
        	if (isValidRule(birthRules) && isValidRule(survivalRules)) {
                parseAndSetRules(birthRules, survivalRules);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter only numbers 0-9.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        	
        	updateStatusLine();
        }
    }

    /**
     * Parses and sets the birth and survival rules for the game.
     *
     * @param birthRules     A string representing the birth rules.
     * @param survivalRules  A string representing the survival rules.
     */
    public void parseAndSetRules(String birthRules, String survivalRules) {
        lifePanel.birthRules.clear();
        lifePanel.survivalRules.clear();

        for (char c : birthRules.toCharArray()) {
            lifePanel.birthRules.add(Character.getNumericValue(c));
        }
        for (char c : survivalRules.toCharArray()) {
            lifePanel.survivalRules.add(Character.getNumericValue(c));
        }
    }

    /**
     * Validates that the rule string contains only numbers 0-9.
     *
     * @param rule The rule string to validate.
     * @return true if the rule is valid, false otherwise.
     */
    private boolean isValidRule(String rule) {
        return rule.matches("[0-9]*");
    }
    
    /**
     * Updates the status line.
     */
    public void updateStatusLine() {
        if(lifePanel.gameRunning)
            lifeFrame.updateStatus("Game running. || Current rules: "
            						+ "B" + lifePanel.getBirthRulesAsString() + "/S" + lifePanel.getSurvivalRulesAsString() +
            						" || Time between generations: " + lifePanel.gameTimer.getDelay() + " ms");
        if(!lifePanel.gameRunning)
            lifeFrame.updateStatus("Game is not running. || Current rules: "
            						+ "B" + lifePanel.getBirthRulesAsString() + "/S" + lifePanel.getSurvivalRulesAsString() +
            						" || Time between generations: " + lifePanel.gameTimer.getDelay() + " ms");
    }
}