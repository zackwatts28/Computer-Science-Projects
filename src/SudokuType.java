/**
 * @author Allison Rannels and Zack Watts
 * A class for setting and getting the correct 9x9 Sudoku Game
 * 
 */

public class SudokuType {
	
	public static final SudokuType NINEBYNINE = new SudokuType(9,9,3,3,new String[] {"1","2","3","4","5","6","7","8","9"},"9 By 9 Game");

	private final int rows;
	private final int columns;
	private final int boxWidth;
	private final int boxHeight;
	private final String [] validValues;
	private final String description;
	
	/**
	   * Creates the parameters for a nine by nine sudoku grid
	   * Initializes the game based of number of rows and columns potentially allowing games other than 9x9
	   * @param rows 
	   * @param columns
	   * @param boxWidth
	   * @param boxHeight 
	   * @param validValues
	   * @param description
	   */
	private SudokuType(int rows,int columns,int boxWidth,int boxHeight,String [] validValues,String description) {
		this.rows = rows;
		this.columns = columns;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.validValues = validValues;
		this.description = description;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getBoxWidth() {
		return boxWidth;
	}
	
	public int getBoxHeight() {
		return boxHeight;
	}
	
	public String [] getValidValues() {
		return validValues;
	}
	
	public String toString() {
		return description;
	}
}
