package board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import movepolicy.rule.MoveTrace;
import participant.Score;
import participant.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

public record Board(Map<Position, Piece> pieces) {

    public Board {
        pieces = Map.copyOf(pieces);
    }

    public Score calculateScoreOf(final Side side) {
        return pieces.values().stream()
            .filter(piece -> piece.isSameSide(side))
            .map(Piece::getScore)
            .reduce(Score.zero(), Score::add);
    }

    public Optional<PieceType> getPieceTypeAt(final Position position) {
        final Piece piece = pieceAt(position);
        if (piece == null) {
            return Optional.empty();
        }
        return Optional.of(piece.type());
    }

    public void validatePositions(final Position departure, final Position destination, final Turn turn) {
        if (departure.equals(destination)) {
            throw new IllegalArgumentException("출발지와 도착지는 동일할 수 없습니다.");
        }
        final Piece movingPiece = requirePieceAt(departure);
        if (!movingPiece.isSameSide(turn.getSide())) {
            throw new IllegalArgumentException("본인 진영의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Board move(final Position departure, final Position destination) {
        final Piece movingPiece = requirePieceAt(departure);
        final MoveTrace moveTrace = findMoveTrace(movingPiece, departure, destination);

        movingPiece.validate(departure, destination, moveTrace);

        return replace(departure, destination, movingPiece);
    }

    private MoveTrace findMoveTrace(final Piece movingPiece, final Position departure, final Position destination) {
        final List<Position> pathPositions = movingPiece.findPathPositions(departure, destination);
        final List<Piece> pathPieces = findPathPieces(pathPositions);
        final Piece targetPiece = pieceAt(destination);

        return new MoveTrace(movingPiece, pathPieces, targetPiece);
    }

    private List<Piece> findPathPieces(final List<Position> pathPieces) {
        return pathPieces.stream()
            .map(this::pieceAt)
            .filter(Objects::nonNull)
            .toList();
    }

    private Board replace(final Position departure, final Position destination, final Piece movingPiece) {
        final Map<Position, Piece> moved = new HashMap<>(pieces);
        moved.remove(departure);
        moved.put(destination, movingPiece);
        return new Board(moved);
    }

    public Piece pieceAt(final Position position) {
        return pieces.get(position);
    }

    private Piece requirePieceAt(final Position position) {
        final Piece piece = pieceAt(position);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return piece;
    }
}