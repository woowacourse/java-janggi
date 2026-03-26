package domain;

import domain.strategy.*;
import domain.vo.Position;

import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void straightMove(final Position from, final Position to) {
        Piece fromPiece = findPieceByPosition(from).get();
        board.put(to, fromPiece);
    }

    public boolean isExistPosition(Position tempPosition) {
        return board.containsKey(tempPosition);
    }

    public Optional<Piece> findPieceByPosition(final Position position) {
        return Optional.ofNullable(board.get(position));
    }

    public boolean isAnotherTeam(Position from, Position to) {
        if (findPieceByPosition(to).isEmpty()) {
            return true;
        }

        Piece targetPiece = findPieceByPosition(to).get();
        Piece currentPiece = findPieceByPosition(from).get();
        return currentPiece.getTeam() != targetPiece.getTeam();
    }
}
