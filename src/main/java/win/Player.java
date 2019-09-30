package win;

public enum Player {
    CROSS,
    CIRCLE,
    NONE;

    public static Player swap(Player current) {
	return current == Player.CROSS ? Player.CIRCLE : Player.CROSS;
    }
    
    @Override
    public String toString() {
	return switch (this) {
	case CROSS -> "X";
	case CIRCLE -> "O";
	case NONE -> "";
	};
    }
}
