package domain.game;

import domain.board.Board;
import domain.board.Intersection;

public final class JanggiGame {

    private static final Side FIRST_TURN = Side.CHO;

    private final Board board;
    private Side currentTurn;

    public JanggiGame(Board board) {
        this.board = board;
        this.currentTurn = FIRST_TURN;
    }

    public void movePiece(
            Intersection startIntersection,
            Intersection destination,
            Side requestingSide
    ) {
        if (requestingSide != currentTurn) {
            throw new IllegalArgumentException("지금은 " + currentTurn + "의 차례입니다.");
        }

        board.movePiece(startIntersection, destination, requestingSide);

        currentTurn = currentTurn.nextTurn();
    }
}
