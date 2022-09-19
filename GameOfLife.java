
/***************************************************************  
*  file: GameOfLife.java  
*  author: Omar Jaber
*  class: CS 1400 
*  
*  assignment: program 6  
*  date last modified: 5/21/2022  
*  
*  Purpose: Simulates Conways game of life based off a user inputted file
*  
****************************************************************/
import java.io.*;
import java.util.*;

public class GameOfLife {
	private static int numColumns;
	private static int numRows;
	private static char[][] board;
	private static int numIters = 0; // Only variable here that isn't self explanatory. Number of Iterations

	public static void main(String[] args) { // Default constructor
		Scanner scnr = new Scanner(System.in);
		System.out.print("Please enter the file name: ");
		File userFile = new File(scnr.nextLine()); // Gets file from users
		while (!userFile.exists()) { // Makes sure file exists
			System.out.print("This file does not exist. Please enter a valid file: ");
			userFile = new File(scnr.nextLine());
		}
		Scanner fileScanner = null;
		try { // Try catch for the scanner to satisfy java requirements for a exception
				// handler.
			fileScanner = new Scanner(userFile);
		} catch (FileNotFoundException e) {
		}
		numRows = fileScanner.nextInt();
		numColumns = fileScanner.nextInt(); // First 2 ints of the file dictate the numRows and numColumns
		board = new char[numRows][numColumns]; // Initializes board
		// i is the row, j is the column, basically i controls the Y and j controls the X
		for (int i = 0; i < numRows; i++) { // Fills up the board with data from the user inputted file.
			for (int j = 0; j < numColumns; j++) {
				if (fileScanner.hasNextInt()) {
					if (fileScanner.nextInt() == 1) {
						setCell(j, i, 88);
					} else {
						setCell(j, i, 48);
					}
				}
			}
		}
		System.out.print("Enter number of iterations: ");
		numIters = scnr.nextInt(); // Gets number of iterations desired by the user
		System.out.println("\n");
		computeNextGeneration(numIters); // Starts recursive method

	}

	// Gets the number of columns
	public int getColumns() {
		return numColumns;
	}

	// Gets the number of rows
	public int getRows() {
		return numRows;
	}

	// Gets the data of the cell at a position
	public int getCell(int column, int row) {
		int cellValue;
		try {
			if (board[row][column] == 88) {
				cellValue = 1;
			} else {
				cellValue = 0;
			}
		} catch (Exception e) {
			cellValue = 0;
		}
		return cellValue;
	}

	// Sets the value onto the board
	public static void setCell(int column, int row, int value) {
		board[row][column] = (char) value;
	}

	// Recursive method that calls onto itself until int generation reaches 0 or less
	public static void computeNextGeneration(int generation) {
		char[][] tempBoard = new char[numRows][numColumns]; // Creates a temporary board to put the next generations into
		tempBoard = board; // Transfers board data
		if (!(generation <= 0)) { // If the generation is less than or equal to 0 it stops
			System.out.println("Generation " + (numIters - generation + 1)); // Prints out current generation
			System.out.println("");
			print(); // Calls the print method to print out the board
			System.out.println("");
			// This long list of for loops and if else statements checks each cell and counts its alive neighbors. Refer to the game of life rules
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					int aliveNeighbors = 0; // Number of alive neighbors each cell has, resets for each cell
					if (i > 0 && j > 0 && i < numRows - 1 && j < numColumns - 1) {
						// This particular for loop that has m and n check the 8 cells around the current cell. Found in all the other for loops but slightly modified to
						// suit their needs/
						for (int m = -1; m < 2; m++) {
							for (int n = -1; n < 2; n++) {
								if (!(m == 0 && n == 0)) {
									if (tempBoard[i - m][j - n] == 88 || tempBoard[i - m][j - n] == 100) { // char 88 is X and char 100 is d
										aliveNeighbors++; // Adds 1 to the number of alive neighbors

									}
								}
							}
						}
					} else if (i == 0 && j > 0 && j < numColumns - 1) {
						for (int m = 0; m < 2; m++) {
							for (int n = -1; n < 2; n++) {
								if (!(m == 0 && n == 0)) {

									if (tempBoard[i + m][j - n] == 88 || tempBoard[i + m][j - n] == 100) {
										aliveNeighbors++;
									}
								}
							}
						}
					} else if (i > 0 && j == 0 && i < numRows - 1) {
						for (int m = -1; m < 2; m++) {
							for (int n = 0; n < 2; n++) {
								if (!(m == 0 && n == 0)) {
									if (tempBoard[i - m][j + n] == 88 || tempBoard[i - m][j + n] == 100) {
										aliveNeighbors++;
									}
								}
							}
						}
					} else if (i == 0 && j == 0) {
						for (int m = 0; m < 2; m++) {
							for (int n = 0; n < 2; n++) {
								if (!(m == 0 && n == 0)) {
									if (tempBoard[i + m][j + n] == 88 || tempBoard[i + m][j + n] == 100) {
										aliveNeighbors++;
									}
								}
							}
						}
					} else if (i == numRows - 1 && j > 0) {
						if (j < numColumns - 1) {
							for (int m = 0; m < 2; m++) {
								for (int n = -1; n < 2; n++) {
									if (!(m == 0 && n == 0)) {
										if (tempBoard[i - m][j - n] == 88 || tempBoard[i - m][j - n] == 100) {
											aliveNeighbors++;
										}
									}
								}
							}
						} else {
							for (int m = 0; m < 2; m++) {
								for (int n = 0; n < 2; n++) {
									if (!(m == 0 && n == 0)) {
										if (tempBoard[i - m][j - n] == 88 || tempBoard[i - m][j - n] == 100) {
											aliveNeighbors++;
										}
									}
								}
							}
						}
					} else if (j == numColumns - 1) {
						if (i < numRows - 1 && i > 0) {
							for (int m = -1; m < 2; m++) {
								for (int n = 0; n < 2; n++) {
									if (!(m == 0 && n == 0)) {
										if (tempBoard[i - m][j - n] == 88 || tempBoard[i - m][j - n] == 100) {
											aliveNeighbors++;
										}
									}
								}
							}
						}
					}
					// If a cell is supposed to die, it sets their char to d.
					if ((aliveNeighbors < 2 || aliveNeighbors > 3) && tempBoard[i][j] == 88) {
						tempBoard[i][j] = 'd';
					}
					// If a dead cell has 3 neighbors alive, then its set to a to avoid interference with other cells
					if (tempBoard[i][j] == 48 && aliveNeighbors == 3) {
						tempBoard[i][j] = 'a';
					}
				}

			}
			// Replaces each 'a' with a 'X', 'd' with a '0'
			for (int z = 0; z < numRows; z++) {
				for (int r = 0; r < numColumns; r++) {
					if (tempBoard[z][r] == 97) {
						tempBoard[z][r] = 'X';
					} else if (tempBoard[z][r] == 100) {
						tempBoard[z][r] = '0';
					}

				}
			}
			// Sets the board with the data from the tempBoard
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numColumns; j++) {
					setCell(j, i, tempBoard[i][j]);
				}
			}

			computeNextGeneration(generation - 1); // Recursive step happens here
		}
	}

	public static void print() { // Prints out the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (board[i][j] == 'X') {
					System.out.print("1 "); // Replaces 'X' with "1 "
				} else
					System.out.print(board[i][j] + " "); // Keeps the 0
			}

			System.out.println("");
		}
	}
//	THERE IS NO MAIN METHOD IN THIS FILE. IF YOU WANT TO RUN THE PROGRAM PLEASE UNCOMMENT THE MAIN METHOD AT THE BOTTOM, OTHERWISE IGNORE IT
	/*
	 * public static void main(String[] args) { new GameOfLife(); }
	 */
}
