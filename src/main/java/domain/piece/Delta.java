package domain.piece;

public record Delta(int column, int row) {

    public static final Delta UP = new Delta(-1, 0);
    public static final Delta RIGHT_UP = new Delta(-1, 1);
    public static final Delta RIGHT = new Delta(0, 1);
    public static final Delta RIGHT_DOWN = new Delta(1, 1);
    public static final Delta DOWN = new Delta(1, 0);
    public static final Delta LEFT_DOWN = new Delta(1, -1);
    public static final Delta LEFT = new Delta(0, -1);
    public static final Delta LEFT_UP = new Delta(-1, -1);
}
