package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public final class JanggiGame {

    private static final Side FIRST_TURN = Side.CHO;

    private final Board board;
    private Side currentTurn;

    public JanggiGame(Board board) {
        this.board = board;
        this.currentTurn = FIRST_TURN;
    }

    public List<Intersection> getMovableIntersections(
            Intersection startIntersection,
            Side requestingSide
    ) {
        validateSide(requestingSide);

        return board.getMovableIntersections(startIntersection, requestingSide);
    }

    public void movePiece(
            Intersection startIntersection,
            Intersection destination,
            Side requestingSide
    ) {
        validateSide(requestingSide);

        board.movePiece(startIntersection, destination, requestingSide);

        currentTurn = currentTurn.nextTurn();
    }

    public Side getCurrentTurn() {
        return currentTurn;
    }

    public Map<Intersection, Piece> getBoard() {
        return board.getPieces();
    }

    private void validateSide(Side requestingSide) {
        if (requestingSide != currentTurn) {
            throw new IllegalArgumentException("지금은 " + currentTurn + "의 차례입니다.");
        }
    }
}
