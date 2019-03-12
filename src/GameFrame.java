/**
 * @author Allison Rannels and Zack Watts
 * A class for building the Sudoku game frame and running it
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements KeyListener{

	private ArrayList<String> game = new ArrayList<String>();
	private GamePanel sPanel;
	private EventHandler eh;
	private Sudoku thisPuzzle;
	private JFileChooser fileDialog;
	
	
	/**
	   * Sets of the frame of the game
	   */
	public GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku Helper");
		this.setMinimumSize(new Dimension(800,600));
		fileDialog = new JFileChooser();
		
		eh = new EventHandler();
		
		//builds menu
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New");
		JMenuItem readGame = new JMenuItem("Read");
		JMenuItem saveGame = new JMenuItem("Save");
		JMenuItem exitGame = new JMenuItem("Exit");
		
		newGame.addActionListener(eh);
		readGame.addActionListener(eh);
		saveGame.addActionListener(eh);
		exitGame.addActionListener(eh);

		file.add(newGame);
		file.add(readGame);
		file.add(saveGame);
		file.add(exitGame);
		menuBar.add(file);
		this.setJMenuBar(menuBar);
		
		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800,600));


		sPanel = new GamePanel();
		
		windowPanel.add(sPanel);
		this.add(windowPanel);
		
		buildInterface(SudokuType.NINEBYNINE, 26);
	}
	
	
	/**
	   * Adds the string puzzle to the game arraylist
	   */
	public void addPuzzle(){
		
		game.add(thisPuzzle.toString());
		
	}
	
	/**
	   * Builds the new interface with a different puzzle each time
	   * @param 9x9 puzzleSize
	   * @param font size
	   */
	public void buildInterface(SudokuType puzzleSize,int fontSize) {
		//builds interface with desired font size
		Sudoku generatedPuzzle = new CreateGame().generateRandomSudoku(puzzleSize);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);
		thisPuzzle = generatedPuzzle;
		addPuzzle();
		addKeyListener(sPanel.new NumActionListener());
		this.addKeyListener(this);
		sPanel.repaint();

	}
	
	
	/**
	   * Inner EventHandler class that implements ActionListener
	   */
	private class EventHandler implements ActionListener {

		/**
		   * adds the actions to the menu items
		   * @param ActionEvent arg0
		   */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Exits the game
			if (arg0.getActionCommand().equals("Exit")) {
				System.exit(0);
			}
			//generates a new game
			if(arg0.getActionCommand().equals("New")){
				game.clear();
				buildInterface(SudokuType.NINEBYNINE, 26);
			}
			//Reads in a saved game
			if (arg0.getActionCommand().equals("Read")){
				
			
			}
			//Saves a game
			if(arg0.getActionCommand().equals("Save")){
				savePuzzle();
			}
		}
	
		
		
//		public void readPuzzle() {
			
//		int returnCode = fileDialog.showOpenDialog(App.this);
//		if (returnCode == JFileChooser.APPROVE_OPTION) {
//			try {
//				File f = fileDialog.getSelectedFile();
//				BufferedReader r = new BufferedReader(new FileReader(f));
//				b.setContents(r.readLine().toCharArray());
//				r.close();
//				displayMessage("Loaded");
//			} catch (IOException e1) {
//				displayMessage("Could not load from file");
//			}
//		}
		
//			String lineString = lines.toString();
//			JFileChooser saveFile = new JFileChooser();
//			saveFile.setCurrentDirectory(new File("/ThisPC/Documents"));
//			int retrieval = saveFile.showSaveDialog(null);
//			if (retrieval == JFileChooser.APPROVE_OPTION){
//				try(FileWriter fw = new FileWriter(saveFile.getSelectedFile())){
//					fw.write(lineString.toString());
//				}
//				catch(Exception ex){
//					ex.printStackTrace();
//				}
//			}
//		}
			
		
//		public String getFile() {
//			File selectedFile = null;
//			JFileChooser openFile = new JFileChooser();
//			openFile.setCurrentDirectory(new File("/ThisPC/Documents"));
//			int retrival = openFile.showOpenDialog(new JFrame());
//			if (retrival == JFileChooser.APPROVE_OPTION) {
//				selectedFile = openFile.getSelectedFile();
//			}
//			return selectedFile.toString();
//		}
//		
//		public void openLines() {
//			Graphics g = drawingPanel.getGraphics();
//			g.setColor(Color.black);
//			g.setXORMode(drawingPanel.getBackground());
//			Scanner scan = null;
//			File infile = new File(getFile());
//			try {
//				scan = new Scanner(infile);
//			}
//			catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			while(scan.hasNext()) {
//				String line = scan.nextLine();
//				line = line.replaceAll("\t", "");
//				Scanner x = new Scanner(line);
//				x.useDelimiter(",");
//				int x1 = x.nextInt();
//				int x2 = x.nextInt();
//				int x3 = x.nextInt();
//				int x4 = x.nextInt();
//				int x5 = x.nextInt();
//				
//				if (x5==1) {
//					g.drawLine(x1,x2,x3,x4);
//					shapes.add(new Line(x1, x2, x3, x4));
//				}
//				if (x5==2) {
//					g.drawRect(x1,x2,(Math.abs(x3-x1)),(Math.abs(x4-x2)));
//					shapes.add(new Rectangle(x1, x2, x3, x4));
//				}
//				repaint();
//			}
//		}
		
		/**
		   * saves the current puzzle to a text file
		   */
		public void savePuzzle() {
			StringBuilder builder = new StringBuilder();
			for (String value : game){
				builder.append(value);
			}
			String lineString = builder.toString();
			
			fileDialog.setCurrentDirectory(new File ("/ThisPC/Documents"));
			int retrival = fileDialog.showSaveDialog(null);
			if (retrival == JFileChooser.APPROVE_OPTION) {
		        try(FileWriter fw = new FileWriter(fileDialog.getSelectedFile()+".txt")) {
		        	fw.write(lineString.toString());
		        }
		        catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		    repaint();
		}
		
	}
		
	/**
	   * the main that runs the game
	   */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//creates the base frame of the game
				GameFrame frame = new GameFrame();
				
				frame.setVisible(true);
			}
		});
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//System.out.println(arg0.getKeyCode());
		
	}
}
