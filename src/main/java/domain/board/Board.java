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

    public Optional<Piece> getPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    private Piece getRequiredPiece(Position position) {
        return getPiece(position)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.EMPTY_SOURCE.getMessage()));
    }

    public void move(Position from, Position to) {

        Piece fromPiece = getRequiredPiece(from);
        Optional<Piece> toPiece = getPiece(to);

        validateSameTeam(fromPiece, toPiece);

        List<Offset> pathOffset = fromPiece.getPathOffset(Offset.of(from, to));
        List<Piece> blockedPieces = getBlockedPieces(from, pathOffset);

        fromPiece.validateMove(blockedPieces, toPiece);
        pieces.put(to, pieces.remove(from));
    }

    private void validateSameTeam(Piece fromPiece, Optional<Piece> toPiece) {
        if (toPiece.isPresent() && fromPiece.isSameTeam(toPiece.get())) {
            throw new IllegalStateException(ErrorMessage.SAME_TEAM_OCCUPIED.getMessage());
        }
    }

    public List<Piece> getBlockedPieces(Position from, List<Offset> offsets) {
        return offsets.stream()
                .map(offset -> offset.applyTo(from))
                .map(this::getPiece)
                .flatMap(Optional::stream)
                .toList();
    }
}
