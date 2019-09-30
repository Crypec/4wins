package win;

import java.util.*;
import java.util.stream.*;
import org.sk.PrettyTable;

public class Board {

    private Player[][] gameBoard;

    public Board(int xSize, int ySize) {
	this.gameBoard = new Player[ySize][xSize];
	Arrays.stream(gameBoard).forEach(a -> Arrays.fill(a, Player.NONE)); 
    }

    public void setPlayer(Player player, int slotIndex) throws IllegalArgumentException {

	if (slotIndex > getMaxSlotIndex() -1 || slotIndex < 0) {
	    throw new IllegalArgumentException(String.format("You have to chose a slot between 1 and %d!", getMaxSlotIndex()));
	} else if (slotIsFull(slotIndex)) {
	    throw new IllegalArgumentException("The slot you chose is already full!");
	}

	for (int i = gameBoard[slotIndex].length - 1; i >= 0; i--) {
	    if (gameBoard[slotIndex][i] == Player.NONE) {
		gameBoard[slotIndex][i] = player;
		return;
	    }
	}
    }
    
    public boolean hasWinner() {

	boolean hasWinner = false;

	for (int i = 0; i < gameBoard.length; i++) {
	    hasWinner |= slotHasWinner(i);
	}

	for (int i = 0; i < gameBoard[0].length; i++) {
	    hasWinner |= rowHasWinner(i);
	}
	
	return hasWinner || diagUpHasWinner() || diagDownHasWinner();
    }

    public boolean isFull() {
	return !Arrays.stream(gameBoard)
	    .flatMap(Stream::of)
	    .anyMatch(Player.NONE::equals);
    }

    public boolean slotIsFull(int slotIndex) {
	return !Arrays.stream(gameBoard[slotIndex])
	    .anyMatch(Player.NONE::equals);
    }
    
    public boolean slotHasWinner(int slotIndex) {
	Player first = gameBoard[slotIndex][0];
	if (first == Player.NONE) return false;
	return Arrays.stream(gameBoard[slotIndex]).allMatch(first::equals);
    }

    public boolean rowHasWinner(int rowIndex) {
	Player first = gameBoard[0][rowIndex];
	if (first == Player.NONE) return false;   

	for (int i = 0; i < gameBoard.length; i++) {
	    if (gameBoard[i][rowIndex] != first) return false;
	}
	return true;
    }

    public boolean diagDownHasWinner() {
	Player first = gameBoard[0][0];
	if (first == Player.NONE) return false;

	for (int i = 0; i < gameBoard.length; i++) {
	    if (gameBoard[i][i] != first) return false;
	}
	return true;
    }

    public boolean diagUpHasWinner() {

	Player first = gameBoard[0][gameBoard[0].length -1];
	if (first == Player.NONE) return false;

	for (int x = 0; x < gameBoard.length; x++) {
	    int y = (gameBoard.length - 1) - x;
	    if (gameBoard[x][y] != first) return false;
	}
	return true;
    }

    public int getMaxSlotIndex() { return gameBoard.length; }

    public String toString() {
	var headerRow = IntStream.rangeClosed(1, gameBoard.length)
	    .mapToObj(Integer::toString)
	    .toArray(String[]::new);
	var table = new PrettyTable(headerRow);

	for (int y = 0; y < gameBoard[0].length; y++) {
	    var row = new ArrayList<String>();
	    for (int x = 0; x < gameBoard.length; x++) {
		row.add(gameBoard[x][y].toString());
	    }
	    table.addRow(row.toArray(new String[row.size()]));
	}
	return table.toString();
    }
}
