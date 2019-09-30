/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package win;

public class App {

    public static void main(String... args) {

	var board = new Board(3, 3);
	var currentPlayer = Player.CROSS;

	while (!board.hasWinner() && !board.isFull()) {

	    clearScreen();
	    System.out.println(board);

	    int slotIndex = IOTools.readInteger("Choose a slot: ");
	    try {
		board.setPlayer(currentPlayer, slotIndex -1);
	    } catch (IllegalArgumentException e) {
		System.out.printf("ERROR :: Failed to place Player in slot: %d %n", slotIndex);
		System.out.println(e.getMessage());
		IOTools.readLine("Press [ENTER] to continue!");
		continue;
	     } 

	     currentPlayer = Player.swap(currentPlayer);
	 }

	 clearScreen();
	 System.out.println(board);

	 if (board.hasWinner()) {
	     System.out.printf("Win :: Congrats player %s!", Player.swap(currentPlayer));
	 } else if (board.isFull()) {
	     System.out.println("TIE :: No one has won, board is full!");
	 }
    }

    public static void clearScreen() {
	System.out.print("\033[H\033[2J"); // ANSI escape code -> make sure your terminal supports these
	System.out.flush();  
    }
}
