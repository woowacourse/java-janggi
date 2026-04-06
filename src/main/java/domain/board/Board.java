package domain.board;

import exception.ErrorMessage;
import domain.Offset;
import domain.piece.Cannon;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

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
        validateActualMove(from, to);

        Piece fromPiece = getRequiredPiece(from);
        Optional<Piece> toPiece = getPiece(to);
        validateSameTeam(fromPiece, toPiece);

        List<Offset> pathOffset = fromPiece.getPathOffset(from, to);
        List<Piece> blockedPieces = getBlockedPieces(from, pathOffset);

        if (fromPiece instanceof Cannon cannon) {
            cannon.validateTarget(toPiece);
        }

        fromPiece.validateMove(blockedPieces);

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

    public double calculateScore(Team team) {
        return team.getScore() + pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(piece -> piece.getPieceType().getScore())
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
