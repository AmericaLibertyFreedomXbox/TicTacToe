package tictactoe;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//Shortcut [ctrl + shift + o] brings in the necessary class
public class GameGUI extends JFrame{
	private Container pane;
	private String currentPlayer;
	private JButton [][] board;
	private boolean isWinner;
	private boolean isSpaceEqual8;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem quit;
	private JMenuItem newGame;

	//Constructor
	public GameGUI() {
		super(); //calls the parent class JFrames default constructor
		pane = this.getContentPane();
		pane.setLayout(new GridLayout(3,3));
		setTitle("A Simple Game of Tic Tac Toe");
		setSize(600,600);
		currentPlayer = "x";
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		board = new JButton [3][3];
		isSpaceEqual8 = false;
		isWinner = false;
		initBoard();
		initMenuBar();
	}

	//Initialize drop-down MenuBar 
	private void initMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		
		newGame = new JMenuItem("New Game");

		//newGame resets the board
		newGame.addActionListener(new ActionListener() { //ctrl + space
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boardReset();
				
			}
		});

		quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

	//Add and set menu bar
		menu.add(quit);
		menu.add(newGame);
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	//boardReset() resets the fields
	private void boardReset() {
		currentPlayer = "x";
		isWinner = false;
		isSpaceEqual8 = false;
		//for each JButton, set text to the empty string
		for(int p = 0; p < 3; p++) {
			for(int q = 0; q < 3; q++) {
				board[p][q].setText("");
			}
		}
	}

	//use a nested for loop, since the board is a 2 dimensional array
	private void initBoard() {
		for(int p = 0; p < 3; p++) {
			for(int q = 0; q < 3; q++) {
				JButton btn = new JButton("");
				btn.setFont(new Font(Font.DIALOG, Font.BOLD,120));
				board[p][q] = btn; //pronounced: 'i_j' or 'i sub j'
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (((JButton)e.getSource()).getText().equals("") &&
						isWinner == false && isSpaceEqual8 == false) {
							btn.setText(currentPlayer);
							isWinner();
							checkSpaceLeft();
							togglePlayer();
						}
					}
				});
				pane.add(btn);
			}
		}
	}

	//We need a method to select the next player in our code: either 'x' or 'o'.
	private void togglePlayer() {
		if(isSpaceEqual8 == false) {
			cpuNextMove();
			currentPlayer = "x";
		}
		else if (isWinner == false)
			noWinner();
	}
/*------------------------------------------------------------*/
	private void cpuNextMove() {
		currentPlayer = "o";
	//after player, place 'o' in bottom right corner
		int x = getRNG();
		int y = getRNG();


		if(isWinner == false && board[x][y].getText().equals("")) {
			 board[x][y].setText("o");
		}

		else if (isWinner == false) {
	        while (board[x][y].getText().equals("x") || board[x][y].getText().equals("o")) {
				x = getRNG();
				y = getRNG();
	        }
	        board[x][y].setText("o");
		}
		isWinner();
}

	private static int getRNG() {
		int min = 0;
		int max = 2;
		int x = ThreadLocalRandom.current().nextInt(min, max + 1);
		return x;
	}
/*------------------------------------------------------------*/
	private void isWinner() {
		//Set win condition for columns
		if(board [0][0].getText().equals(currentPlayer) && board[1][0].getText().equals(currentPlayer) && board[2][0].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins"); //passing in null ==> no parent component
			isWinner = true;
		}
		else if(board [0][1].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][1].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		else if(board [0][2].getText().equals(currentPlayer) && board[1][2].getText().equals(currentPlayer) && board[2][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		//Set win condition for rows
		else if(board [0][0].getText().equals(currentPlayer) && board[0][1].getText().equals(currentPlayer) && board[0][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		else if(board [1][0].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[1][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		else if(board [2][0].getText().equals(currentPlayer) && board[2][1].getText().equals(currentPlayer) && board[2][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		//Set win condition for diagonal
		else if(board [0][0].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
		else if(board [2][0].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[0][2].getText().equals(currentPlayer)){
			JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins");
			isWinner = true;
		}
	}

	private void checkSpaceLeft(){
		int space = 0;
		for(int p = 0; p < 3; p++) {
			for(int q = 0; q < 3; q++) {
				if (board[p][q].getText().equals("o") || board[p][q].getText().equals("x")){
					space = space + 1;
					if(space == 8) {
						System.out.println("space full: " + space);
						isSpaceEqual8 = true;
					}
				}
			}
		}
	}

	private void noWinner() {
		JOptionPane.showMessageDialog(null, "No Winner");
		isWinner = true;
	}
}
