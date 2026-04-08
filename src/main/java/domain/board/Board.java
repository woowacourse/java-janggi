package domain.board;

import exception.ErrorMessage;
import domain.Offset;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Board {
    private final Map<Position, Piece> pieces;
    private final List<Palace> palaces = List.of(new Palace(new Position(4, 1)), new Palace(new Position(4, 8)));

    public Board(Map<Position, Piece> pieces) {
        if (pieces == null) {
            throw new IllegalArgumentException("pieces는 null값일 수 없습니다.");
        }
        this.pieces = new HashMap<>(pieces);
    }

    private Optional<Palace> findPalace(Position position) {
        return palaces.stream()
                .filter(palace -> palace.isInPalace(position))
                .findFirst();
    }

    public void move(Position from, Position to) {
        validateActualMove(from, to);

        Piece sourcePiece = getRequiredPiece(from);
        Optional<Piece> targetPiece = getPiece(to);
        validateNotSameTeam(sourcePiece, targetPiece);

        Optional<Palace> palace = findPalace(from);
        List<Offset> pathOffset = sourcePiece.getPathOffset(from, to, palace);
        List<Piece> blockedPieces = getBlockedPieces(from, pathOffset);

        sourcePiece.validateMove(blockedPieces);
        sourcePiece.validateTarget(targetPiece);

        pieces.put(to, pieces.remove(from));
    }

    public Optional<Piece> getPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Piece getRequiredPiece(Position position) {
        return getPiece(position)
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.EMPTY_SOURCE.getMessage()));
    }

    private void validateActualMove(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_MOVE.getMessage());
        }
    }

    private void validateNotSameTeam(Piece fromPiece, Optional<Piece> toPiece) {
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

    public double calculateScore(Team team) {
        return team.getScore() + pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::score)
                .sum();
    }

    public boolean isAliveGeneral(Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .anyMatch(piece -> piece.isSameType(PieceType.GENERAL));
    }

    public Map<Position, Piece> getPieces() {
        return Map.copyOf(pieces);
    }
}
