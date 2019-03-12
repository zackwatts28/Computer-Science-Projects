/**
 * @author Allison Rannels and Zack Watts
 * A class for creating a correct Sudoku game.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateGame {

	/**
	   * Creates the sudoku puzzle values
	   * @param ninebynine puzzle parameters
	   * @return Sudoku randomly generated
	   */
	public Sudoku generateRandomSudoku(SudokuType puzzleSize) {
		Sudoku puzzle = new Sudoku(puzzleSize.getRows(), puzzleSize.getColumns(), puzzleSize.getBoxWidth(), puzzleSize.getBoxHeight(), puzzleSize.getValidValues());
		//creates a copy of the game just generated
		Sudoku copy = new Sudoku(puzzle);
		
		Random randomGenerator = new Random();
		
		//stores a list of valid moves that aren't used
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
		
		for(int r = 0;r < copy.getNumRows();r++) {
			//draws a random value between 0 and size of not used values (9)
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			//places the move onto the board
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), true);
			//removes the move from the list of not used values
			notUsedValidValues.remove(randomValue);
		}
		
		SudokuSolver(0, 0, copy);
		
		//controls starting number of predetermined numbers
		int numberOfValuesToKeep = (79);
		//fills in board based on how many numbers to keep
		for(int i = 0; i < numberOfValuesToKeep;) {
			int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
			int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());
			
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) {
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
				i++;
			}
		}
		
		return puzzle;
	}
	
	/**
	 * Solves the sudoku puzzle
	 * Starts with r = 0 and c = 0
	 * @param r the current row
	 * @param c the current column
	 * @return true or false
	 */
    private boolean SudokuSolver(int r,int c,Sudoku puzzle) {
    	//returns false if invalid move
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		
		//if the slot is empty
		if(puzzle.isSlotAvailable(r, c)) {
			
			//loop to find the right value for the slot
			for(int i = 0;i < puzzle.getValidValues().length;i++) {
				
				//if the entered number works in the slot
				if(!puzzle.numInRow(r, puzzle.getValidValues()[i]) && !puzzle.numInCol(c,puzzle.getValidValues()[i]) && !puzzle.numInBox(r,c,puzzle.getValidValues()[i])) {
					
					//make move
					puzzle.makeMove(r, c, puzzle.getValidValues()[i], true);
					
					//if puzzle finished return true
					if(puzzle.boardFull()) {
						
						return true;
						
					}
					
					//go to the next move
					if(r == puzzle.getNumRows() - 1) {
						if(SudokuSolver(0,c + 1,puzzle)) return true;
					} else {
						if(SudokuSolver(r + 1,c,puzzle)) return true;
					}
				}
			}
		}
		
		//if the current slot is full
		else {
			//next move
			if(r == puzzle.getNumRows() - 1) {
				return SudokuSolver(0,c + 1,puzzle);
			} else {
				return SudokuSolver(r + 1,c,puzzle);
			}
		}
		
		//undo last move
		puzzle.makeSlotEmpty(r, c);
		
		
		return false;
	}
}
