package game_of_life;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The GameMenu class creates the menu bar for the Game of Life application.
 * It contains menus and menu items for controlling the game, including starting
 * and stopping the game, adjusting game settings, and saving or loading the game state.
 */
public class GameMenu extends JMenuBar {
	private GameFunctions gameFunctions;

    /**
     * Constructs a GameMenu object with menus and menu items linked to the provided GameFunctions.
     *
     * @param gameFunctions The GameFunctions object that provides the functionality for each menu item.
     */
    public GameMenu(GameFunctions gameFunctions) {
        this.gameFunctions = gameFunctions;
        
        // Create menus
        JMenu gameMenu1 = new JMenu("Start/Stop");
        JMenu gameMenu2 = new JMenu("Game Settings");
        JMenu gameMenu3 = new JMenu("Save/Load");
        
        // Create and add menu items for game control
        JMenuItem startMenuItem = new JMenuItem("Start");
        startMenuItem.addActionListener(e -> gameFunctions.startGame());
        JMenuItem stopMenuItem = new JMenuItem("Stop");
        stopMenuItem.addActionListener(e -> gameFunctions.stopGame());
        
        // Create and add menu items for game settings
        JMenuItem clearMenuItem = new JMenuItem("Clear Board");
        clearMenuItem.addActionListener(e -> gameFunctions.clearBoard());
        JMenuItem randomizeMenuItem = new JMenuItem("Set Random Board");
        randomizeMenuItem.addActionListener(e -> gameFunctions.randomizeBoard());
        JMenuItem setDelayMenuItem = new JMenuItem("Set Generation Time");
        setDelayMenuItem.addActionListener(e -> gameFunctions.setGenerationTime());
        JMenuItem setGridSizeMenuItem = new JMenuItem("Set Grid Size");
        setGridSizeMenuItem.addActionListener(e -> gameFunctions.setGridSize());
        JMenuItem setRulesMenuItem = new JMenuItem("Set Rules");
        setRulesMenuItem.addActionListener(e -> gameFunctions.setRules());
        
        // Create and add menu items for saving and loading the game state
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(e -> gameFunctions.saveGridToFile());
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(e -> gameFunctions.loadGridFromFile());
        

        // Add menus to the menu bar
        gameMenu1.add(startMenuItem);
        gameMenu1.add(stopMenuItem);

        gameMenu2.add(clearMenuItem);
        gameMenu2.add(randomizeMenuItem);
        gameMenu2.add(setDelayMenuItem);
        gameMenu2.add(setGridSizeMenuItem);
        gameMenu2.add(setRulesMenuItem);
        
        gameMenu3.add(saveMenuItem);
        gameMenu3.add(loadMenuItem);
        
        add(gameMenu1);
        add(gameMenu2);
        add(gameMenu3);
    }
}