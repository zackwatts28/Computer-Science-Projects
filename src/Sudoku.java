import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Allison Rannels and Zack Watts
 * A class for setting up the Sudoku Board.
 * Also oversees functions relating to game like making a move
 */

public class Sudoku{

	protected String [][] board;
	// mutable array keeps track of what slots can be changed
	protected boolean [][] mutable;
	//final doesn't allow numbers to be changed
	private final int ROWS;
	private final int COLUMNS;
	private final int BOXWIDTH;
	private final int BOXHEIGHT;
	private final String [] VALIDVALUES;
	
	/**
	   * Creates the parameters for the Sudoku board and initializes it
	   * @param rows 
	   * @param columns
	   * @param boxWidth
	   * @param boxHeight 
	   * @param validValues
	   */
	public Sudoku(int rows,int columns,int boxWidth,int boxHeight,String [] validValues) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.BOXWIDTH = boxWidth;
		this.BOXHEIGHT = boxHeight;
		this.VALIDVALUES = validValues;
		this.board = new String[ROWS][COLUMNS];
		this.mutable = new boolean[ROWS][COLUMNS];
		//fills board with blank spaces
		initializeBoard();
		//makes every slot mutable by default
		initializeMutableSlots();
	}
	
	/**
	   * Creates a sudoku puzzle that already has defined parameters.
	   * @param rows 
	   * @param columns
	   * @param boxWidth
	   * @param boxHeight 
	   * @param validValues
	   * @param description
	   */
	public Sudoku(Sudoku puzzle) {
		this.ROWS = puzzle.ROWS;
		this.COLUMNS = puzzle.COLUMNS;
		this.BOXWIDTH = puzzle.BOXWIDTH;
		this.BOXHEIGHT = puzzle.BOXHEIGHT;
		this.VALIDVALUES = puzzle.VALIDVALUES;
		this.board = new String[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				board[r][c] = puzzle.board[r][c];
			}
		}
		this.mutable = new boolean[ROWS][COLUMNS];
		for(int r = 0;r < ROWS;r++) {
			for(int c = 0;c < COLUMNS;c++) {
				this.mutable[r][c] = puzzle.mutable[r][c];
			}
		}
	}
	
	public int getNumRows() {
		return this.ROWS;
	}
	
	public int getNumColumns() {
		return this.COLUMNS;
	}
	
	public int getBoxWidth() {
		return this.BOXWIDTH;
	}
	
	public int getBoxHeight() {
		return this.BOXHEIGHT;
	}
	
	public String [] getValidValues() {
		return this.VALIDVALUES;
	}
	
	/**
	   * User is allowed to enter number if it is valid
	   * @param row
	   * @param col
	   * @param value
	   * @param isMutable
	   */
	public void makeMove(int row,int col,String value,boolean isMutable) {
		//checks if value is valid and if the move can be made and if the slot can be changed
		if(this.isValidValue(value) && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)) {
			this.board[row][col] = value;
			this.mutable[row][col] = isMutable;
		}
		//allows user to delete on backspace press
		else if(value==" " && this.isValidMove(row,col,value) && this.isSlotMutable(row, col)){
			this.board[row][col]= " ";
		}
	}
	
	/**
	   * determines if it is a valid move
	   * @param row
	   * @param col
	   * @param value
	   * @return true||false
	   */
	public boolean isValidMove(int row,int col,String value) {
		if(this.inRange(row,col)) {
			//checks if number is either in row, column, or box
			if(!this.numInCol(col,value) && !this.numInRow(row,value) && !this.numInBox(row,col,value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	   * determines if the number exists in that column
	   * @param col
	   * @param value
	   * @return true||false
	   */
	public boolean numInCol(int col,String value) {
		if(col <= this.COLUMNS) {
			for(int row=0;row < this.ROWS;row++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	   * determines if the number exists in that row
	   * @param row 
	   * @param value
	   * @return true||false
	   */
	public boolean numInRow(int row,String value) {
		if(row <= this.ROWS) {
			for(int col=0;col < this.COLUMNS;col++) {
				if(this.board[row][col].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	   * determines if the number is in the same 3x3 box
	   * @param row 
	   * @param col
	   * @param value
	   * @return true||false
	   */
	public boolean numInBox(int row,int col,String value) {
		if(this.inRange(row, col)) {
			int boxRow = row / this.BOXHEIGHT;
			int boxCol = col / this.BOXWIDTH;
			
			int startingRow = (boxRow*this.BOXHEIGHT);
			int startingCol = (boxCol*this.BOXWIDTH);
			
			for(int r = startingRow;r <= (startingRow+this.BOXHEIGHT)-1;r++) {
				for(int c = startingCol;c <= (startingCol+this.BOXWIDTH)-1;c++) {
					if(this.board[r][c].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	   * checks if slot is empty
	   * @param row 
	   * @param col
	   * @return true||false
	   */
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
	}
	
	/**
	   * checks if slot can be changed
	   * @param row 
	   * @param col
	   * @return true||false
	   */
	public boolean isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	
	/**
	   * gets value on board
	   * @param row 
	   * @param col
	   * @return String
	   */
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col];
		}
		return "";
	}
	
	public String [][] getBoard() {
		return this.board;
	}
	
	/**
	   * checks of value is a valid number
	   * @param value
	   * @return true||false
	   */
	private boolean isValidValue(String value) {
		for(String str : this.VALIDVALUES) {
			if(str.equals(value)) return true;
		}
		return false;
	}
	
	/**
	   * Checks if number is in the correct range
	   * @param row 
	   * @param col
	   * @return true||false
	   */
	public boolean inRange(int row,int col) {
		return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
	}
	
	/**
	   * Checks if board is full
	   * @return true||false
	   */
	public boolean boardFull() {
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLUMNS;c++) {
				if(this.board[r][c].equals("")) return false;
			}
		}
		return true;
	}
	
	/**
	   * makes an empty slot
	   * @param row 
	   * @param col
	   */
	public void makeSlotEmpty(int row,int col) {
		this.board[row][col] = "";
	}
	
	
	/**
	   * makes a string of the board
	   * @return String
	   */
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < this.ROWS;row++) {
			for(int col=0;col < this.COLUMNS;col++) {
				str += this.board[row][col]+ " ";
			}
			str += "\n";
		}

		return str+"\n";
		
	}
	
	
	/**
	   * Activates the board
	   */
	private void initializeBoard() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.board[row][col] = "";
			}
		}
	}
	
	
	/**
	   * Activates the changeable slots
	   */
	private void initializeMutableSlots() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLUMNS;col++) {
				this.mutable[row][col] = true;
			}
		}
	}
	
}
