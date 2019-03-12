/**
 * @author Allison Rannels and Zack Watts
 * A class for creating the Sudoku Panel and mouse interactions.
 */

import java.awt.BasicStroke;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private Sudoku puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	
	
	/**
	   * Creates a Sudoku Game Panel
	   */
	public GamePanel() {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.addMouseMotionListener(new MouseMotionEvents());
		this.puzzle = new CreateGame().generateRandomSudoku(SudokuType.NINEBYNINE);
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	/**
	   * Creates a Sudoku GamePanel with a puzzle that's already got defined parameters
	   * @param puzzle
	   */
	public GamePanel(Sudoku puzzle) {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	public void newSudokuPuzzle(Sudoku puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	/**
	   * Paints the Panel and draws the the lines
	   * @param g Graphics
	   */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f));
		
		//sets size of each slot based on how many columns and rows there are
		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		//sets the color of the outlines
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(5));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				//draws the vertical lines
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		//draws the outlines
		g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(3));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);
		
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		
		//sets font when text is entered
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			//sets the current color of the selected box
			//g2d.setColor(Color.RED);
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
		
		//colors the board a in desired layout
		g2d.setColor(new Color(0.3f,0.4f,1.0f,0.3f));
		for (int r = 0; r < 9; r++){
			for (int c = 0; c < 9; c++) {
				if((c<=2 && r>2 && r<6)||(c>2 && r<3 && c<6)||(c>2 && r>5 && c<6)|| (c>=6 &&r>2&&r<6 )){
					g2d.setColor(new Color(0.0f,0.0f,1.0f,0.1f));
					g2d.fillRect(c * slotWidth,r * slotHeight,slotWidth,slotHeight);
				}
		}
		}
	}
	
	public void messageFromNumActionListener(String validValue) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, validValue, true);
			repaint();
		}
	}
	
	/**
	   * Creates an inner class that uses KeyListener
	   */
	public class NumActionListener implements KeyListener {		
		/**
		   * gets key code for backspace and 1-9
		   * @param KeyEvent e
		   */
		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == 8)
				messageFromNumActionListener(" ");
			else
				messageFromNumActionListener(String.valueOf((e.getKeyCode()-48)));
			if(puzzle.boardFull())
				System.out.println("Game Complete!");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	   * Creates an inner class that extends MouseInputAdapter and implements MouseMotionListener
	   */
	private class SudokuPanelMouseAdapter extends MouseInputAdapter implements MouseMotionListener {
		
		/**
		   * Adds a resonse to the mouse being clicked
		   * @param MouseEvent e
		   */
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeight = usedHeight/puzzle.getNumRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
		
		public void mouseEntered(MouseEvent e) {
			setToolTipText("POSSIBLE MOVES:123456789");
			
		}
	}
	
	
	/**
	   * Creates an inner class that implements MouseMotionListener
	   */
	public class MouseMotionEvents implements MouseMotionListener {
		ArrayList<Integer> GoodNums = new ArrayList<Integer>();
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}
		

		public void mouseEntered(MouseEvent e) {
			
		}
		
		/**
		   * determines potential moves for the slot the mouse moves into
		   * @param MouseEvent e
		   */
		@Override            
		public void mouseMoved(MouseEvent e) {
			//get x and y cord of mouse
			int tempX = e.getX();
			int xEnd = tempX;
			
			int tempY = e.getY();
			int yEnd = tempY;
			
				for(int i=1;i<10;i++){
					//gets what tile mouse is in based off coordinates and checks 
					//available moves
					if(puzzle.isValidMove((yEnd/50),(xEnd/60),String.valueOf(i)))
						GoodNums.add(i);                                       
				}
				setToolTipText("Moves: " + GoodNums);
				if(GoodNums.isEmpty()){
					setToolTipText("No correct moves, change other values around");
				}
				GoodNums.clear();
				
				
			}

		}
	}

