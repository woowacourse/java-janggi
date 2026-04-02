package domain;

import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final Turn turn;

    public Game(Board board) {
        this.board = board;
        this.turn = new Turn(Side.CHO);
    }

    public void move(Position sourcePosition, Position targetPosition) {
        validateMovement(sourcePosition, targetPosition);
        board.movePiece(sourcePosition, targetPosition);
        turn.next();
    }


    private void validateMovement(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = board.getPiece(sourcePosition);
        Piece targetPiece = board.getPiece(targetPosition);
        sourcePiece.validateMovement(turn.current(), targetPiece);
        List<Position> route = sourcePiece.findRoute(sourcePosition, targetPosition);
        List<Piece> pieces = board.findPiecesOnRoute(route, targetPosition);
        sourcePiece.checkRoute(pieces);
    }

    public Side getCurrentTurn() {
        return turn.current();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
