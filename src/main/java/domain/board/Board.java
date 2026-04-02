package domain.board;

import domain.ErrorMessage;
import domain.Offset;
import domain.piece.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        if (pieces == null) {
            throw new IllegalArgumentException("pieces는 null값일 수 없습니다.");
        }
        this.pieces = new HashMap<>(pieces);
    }

    public void move(Position from, Position to) {
        Piece fromPiece = getRequiredPiece(from);
        Optional<Piece> toPiece = getPiece(to);
        Offset offset = Offset.of(from, to);

        validateActualMove(offset);
        validateSameTeam(fromPiece, toPiece);

        List<Offset> pathOffset = fromPiece.getPathOffset(offset);
        List<Piece> blockedPieces = getBlockedPieces(from, pathOffset);

        fromPiece.validateMove(blockedPieces, toPiece);
        pieces.put(to, pieces.remove(from));
    }

    public Optional<Piece> getPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    private Piece getRequiredPiece(Position position) {
        return getPiece(position)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.EMPTY_SOURCE.getMessage()));
    }

    private void validateActualMove(Offset offset) {
        if(offset.dx() == 0 && offset.dy() == 0) {
            throw new IllegalArgumentException(ErrorMessage.NOT_MOVE.getMessage());
        }
    }

    private void validateSameTeam(Piece fromPiece, Optional<Piece> toPiece) {
        if (toPiece.isPresent() && fromPiece.isSameTeam(toPiece.get())) {
            throw new IllegalStateException(ErrorMessage.SAME_TEAM_OCCUPIED.getMessage());
        }
    }

    private List<Piece> getBlockedPieces(Position from, List<Offset> offsets) {
        return offsets.stream()
                .map(offset -> offset.applyTo(from))
                .map(this::getPiece)
                .flatMap(Optional::stream)
                .toList();
    }
}
