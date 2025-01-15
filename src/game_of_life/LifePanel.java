package game_of_life;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.HashSet;
import java.util.Set;

/**
 * The LifePanel class is responsible for rendering the grid of the Game of Life,
 * handling user interactions, and managing the state of each cell in the grid.
 */
public class LifePanel extends JPanel implements ActionListener {
	protected int xPanel = 1300;
	protected int yPanel = 700;
	protected int size = 16;
	protected int xWidth = xPanel/size;
	protected int yHeight = yPanel/size;
	protected boolean[][] life = new boolean[xWidth][yHeight];
	protected boolean[][] beforeLife = new boolean[xWidth][yHeight];
	protected Timer gameTimer;
	protected Set<Integer> birthRules = new HashSet<>();
	protected Set<Integer> survivalRules = new HashSet<>();
	protected boolean gameRunning = false;
	boolean start = true;
	Boolean dragState = null;
    
    /**
     * Constructs a new LifePanel, setting up the initial state and configuring mouse interactions.
     */
	public LifePanel() {
		setSize(xPanel, yPanel);
		setLayout(null); 
		setBackground(Color.BLACK);
		
		addMouseListener(new MouseAdapter() {
		    @Override
		    public void mousePressed(MouseEvent e) {
		        int x = e.getX() / size;
		        int y = e.getY() / size;

		        if (x < xWidth && y < yHeight) {
		            dragState = !beforeLife[x][y];
		            beforeLife[x][y] = dragState;
		            repaint();
		        }
		    }
		    
		    @Override
		    public void mouseReleased(MouseEvent e) {
		        dragState = null;
		    }
		});

		addMouseMotionListener(new MouseMotionAdapter() {
		    @Override
		    public void mouseDragged(MouseEvent e) {
		        if (dragState != null) {
		            handleMouseDrag(e);
		        }
		    }
		});
		
		birthRules.add(3);
        survivalRules.add(2);
        survivalRules.add(3);
	}

    /**
     * Handles mouse drag events to allow dynamic editing of the grid state.
     *
     * @param e The mouse event.
     */
	private void handleMouseDrag(MouseEvent e) {
	    int x = e.getX() / size;
	    int y = e.getY() / size;
	    
	    if (x < xWidth && y < yHeight && beforeLife[x][y] != dragState) {
	        beforeLife[x][y] = dragState;
	        repaint();
	    }
	}
	
    /**
     * Paints the components of the grid, including cells and grid lines.
     *
     * @param g The Graphics object used for drawing.
     */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		grid(g);
		display(g);
	}
	
    /**
     * Draws the grid lines on the panel.
     *
     * @param g The Graphics object used for drawing.
     */
	private void grid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		for(int i = 0; i < xWidth; i++) {
			g.drawLine(0, i*size, xPanel, i * size);
			g.drawLine(i*size, 0, i * size, yPanel);
		}
	}
	
    /**
     * Draws the cells on the grid, coloring alive cells.
     *
     * @param g The Graphics object used for drawing.
     */
	private void display(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		copyArray();
		for (int i = 0; i < xWidth; i++) {
			for (int j = 0; j < yHeight; j++) {
				if (life[i][j])
				    g.fillRect(i * size, j * size, size, size);
			}
		}
	}
	
    /**
     * Checks the number of alive neighbors around a specific cell.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The number of alive neighbors.
     */
	public int check(int x, int y) {
		int alive = 0;

	    if (life[(x + xWidth - 1) % xWidth][(y + yHeight - 1) % yHeight]) alive++;
	    if (life[(x + xWidth) % xWidth][(y + yHeight - 1) % yHeight]) alive++;
	    if (life[(x + xWidth + 1) % xWidth][(y + yHeight - 1) % yHeight]) alive++;
	    if (life[(x + xWidth - 1) % xWidth][(y + yHeight) % yHeight]) alive++;
	    if (life[(x + xWidth + 1) % xWidth][(y + yHeight) % yHeight]) alive++;
	    if (life[(x + xWidth - 1) % xWidth][(y + yHeight + 1) % yHeight]) alive++;
	    if (life[(x + xWidth) % xWidth][(y + yHeight + 1) % yHeight]) alive++;
	    if (life[(x + xWidth + 1) % xWidth][(y + yHeight + 1) % yHeight]) alive++;

	    return alive;
	}
	
    /**
     * Copies the state of the cells from the beforeLife array to the life array.
     */
	public void copyArray() {
		for (int i = 0; i < xWidth; i++) {
			for (int j = 0; j < yHeight; j++) {
				life[i][j] = beforeLife[i][j];
			}
		}
	}

    /**
     * Updates the state of the grid based on the current rules of the game.
     *
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {
        int aliveNeighbours;

        for (int i = 0; i < xWidth; i++) {
            for (int j = 0; j < yHeight; j++) {
                aliveNeighbours = check(i, j);

                boolean currentlyAlive = life[i][j];
                boolean shouldBeAlive = (currentlyAlive && survivalRules.contains(aliveNeighbours)) || (!currentlyAlive && birthRules.contains(aliveNeighbours));

                beforeLife[i][j] = shouldBeAlive;
            }
        }
        repaint();
    }
    
    /**
     * Returns a string representation of the birth rules.
     *
     * @return A string representing the birth rules.
     */
    public String getBirthRulesAsString() {
        return rulesToString(birthRules);
    }

    /**
     * Returns a string representation of the survival rules.
     *
     * @return A string representing the survival rules.
     */
    public String getSurvivalRulesAsString() {
        return rulesToString(survivalRules);
    }
    
    /**
     * Converts a set of rules into a string format.
     *
     * @param rules The set of rules.
     * @return A string representation of the rules.
     */
    private String rulesToString(Set<Integer> rules) {
        StringBuilder sb = new StringBuilder();
        for (Integer rule : rules) {
            sb.append(rule);
        }
        return sb.toString();
    }
    
    /**
     * Resizes the grid to the new specified size.
     *
     * @param newSize The new size for each cell in the grid.
     */
    public void resizeGrid(int newSize) {
        this.size = newSize;
        this.xWidth = xPanel / size;
        this.yHeight = yPanel / size;

        life = new boolean[xWidth][yHeight];
        beforeLife = new boolean[xWidth][yHeight];

        for (int i = 0; i < xWidth; i++) {
            for (int j = 0; j < yHeight; j++) {
                life[i][j] = false;
                beforeLife[i][j] = false;
            }
        }

        repaint();
    }
}