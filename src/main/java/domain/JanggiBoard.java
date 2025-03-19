package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Move;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    public JanggiBoard(BoardGenerator boardGenerator) {
        this.board = boardGenerator.generateBoard();
    }

    public void move(Position startPosition, Position targetPosition) {
        Piece piece = findPiece(startPosition);
        List<Move> moves = piece.calculatePath(startPosition, targetPosition);
        if (!piece.isCanon()) {
            for (Move move : moves) {
                canMove(startPosition, move);
            }

        }

    }

    public boolean canMove(Position startPosition, Move move) {
        Position movedPosition = startPosition.movePosition(move);
        Piece piece = findPiece(movedPosition);
        if (piece != null) {
            return false;
        }
        return true;
    }

    public Piece findPiece(Position startPosition) {
        return board.get(startPosition);
    }
}
