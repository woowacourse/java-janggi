package domain;

public class JanggiGame {
    private final JanggiBoard board;

    public JanggiGame() {
        board = new JanggiBoard(PieceInitializer.init());
    }

}
