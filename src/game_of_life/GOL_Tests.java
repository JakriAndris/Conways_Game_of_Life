package game_of_life;

import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
import static org.junit.Assert.*;

public class GOL_Tests {

    private LifePanel lifePanel;
    private GameFunctions gameFunctions;
    private LifeFrame lifeFrame;

    @Before
    public void setUp() {
        lifePanel = new LifePanel();
        lifeFrame = new LifeFrame();
        gameFunctions = new GameFunctions(lifePanel, lifeFrame);
    }

    @Test
    public void testResizeGrid() {
        lifePanel.resizeGrid(20);
        assertEquals(lifePanel.xWidth, lifePanel.xPanel / 20);
        assertEquals(lifePanel.yHeight, lifePanel.yPanel / 20);
    }

    @Test
    public void testGetBirthRulesAsString() {
        String rules = lifePanel.getBirthRulesAsString();
        assertNotNull(rules);
        assertTrue(rules.contains("3"));
    }

    @Test
    public void testGetSurvivalRulesAsString() {
        String rules = lifePanel.getSurvivalRulesAsString();
        assertNotNull(rules);
        assertTrue(rules.contains("23"));
    }

    @Test
    public void testCheck() {
    	gameFunctions.clearBoard();

        lifePanel.life[0][0] = true;
        lifePanel.life[0][1] = true;
        lifePanel.life[1][0] = true;
        assertEquals(3, lifePanel.check(1, 1));
    }

    @Test
    public void testCopyArray() {
    	gameFunctions.clearBoard();

        lifePanel.beforeLife[0][0] = true;
        lifePanel.copyArray();
        assertTrue(lifePanel.life[0][0]);
    }

    @Test
    public void testClearBoard() {
    	gameFunctions.clearBoard();

        lifePanel.beforeLife[0][0] = true;
        gameFunctions.clearBoard();
        assertFalse(lifePanel.life[0][0]);
    }

    @Test
    public void testRandomizeBoard() {
        gameFunctions.randomizeBoard();
        boolean anyAlive = false;
        for (int i = 0; i < lifePanel.xWidth; i++) {
            for (int j = 0; j < lifePanel.yHeight; j++) {
                if (lifePanel.beforeLife[i][j]) {
                    anyAlive = true;
                    break;
                }
            }
        }
        assertTrue(anyAlive);
    }

    @Test
    public void testSetTimerDelay() {
        gameFunctions.setTimerDelay(100);
        assertEquals(100, lifePanel.gameTimer.getDelay());
    }
    
    @Test
    public void testActionPerformed() {
    	gameFunctions.clearBoard();
    	
        lifePanel.life[0][0] = true;
        lifePanel.life[0][1] = true;
        lifePanel.life[1][0] = true;

        lifePanel.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "command"));

        assertTrue("Cell (1,1) should be alive", lifePanel.beforeLife[1][1]);
    }
    
    @Test
    public void testStartGameUpdatesStatus() {
        gameFunctions.startGame();
        
        String statusText = lifeFrame.getStatusText();
        
        String expectedStatus = "Status: Game running. || Current rules: B3/S23 || Time between generations: 500 ms";
        assertEquals(expectedStatus, statusText);
    }

}