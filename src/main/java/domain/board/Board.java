package domain.board;

import domain.ErrorMessage;
import domain.Offset;
import domain.piece.Piece;

import java.util.List;
import java.util.Map;


public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece getPiece(Position position) {
        return pieces.get(position);
    }

    public void move(Position from, Position to) {
        if (!pieces.containsKey(from)) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_SOURCE.getMessage());
        }

        Piece fromPiece = pieces.get(from);
        Piece toPiece = pieces.get(to);

        if (toPiece != null && fromPiece.isSameTeam(toPiece)) {
            throw new IllegalStateException(ErrorMessage.SAME_TEAM_OCCUPIED.getMessage());
        }

        List<Offset> pathPositions = fromPiece.getPathOffset(Offset.of(from, to));
        List<Piece> blockedPieces = getBlockedPieces(from, pathPositions);

        fromPiece.validateMove(blockedPieces, toPiece);
        pieces.put(to, pieces.remove(from));
    }

    public List<Piece> getBlockedPieces(Position from, List<Offset> offsets) {
        return offsets.stream()
                .map(offset -> offset.applyTo(from))
                .filter(pieces::containsKey)
                .map(pieces::get).toList();
    }
}
