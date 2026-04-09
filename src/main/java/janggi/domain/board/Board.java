package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.movement.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private static final List<Direction> DEFAULT_MOVABLE_DIRECTIONS =
        List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    private final Map<Position, Piece> positionPieceMap;

    public Board(final Map<Position, Piece> positionPieceMap) {
        this.positionPieceMap = new LinkedHashMap<>(positionPieceMap);
    }

    public boolean isBlank(final Position position) {
        return !positionPieceMap.containsKey(position);
    }

    public Piece findPieceByPosition(final Position position) {
        if (isBlank(position)) {
            throw new IllegalStateException("요청된 위치에는 기물이 존재하지 않습니다.");
        }
        return positionPieceMap.get(position);
    }

    public Optional<Piece> movePiece(final Position from, final Position to) {
        final boolean existTarget = !isBlank(to);
        final Piece requestedPiece = positionPieceMap.remove(from);
        final Piece targetPiece = positionPieceMap.put(to, requestedPiece);

        if (existTarget) {
            assert targetPiece != null;
            return Optional.of(targetPiece);
        }
        return Optional.empty();
    }

    public boolean isGameOver() {
        final long generalCount = positionPieceMap.values()
            .stream()
            .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
            .count();

        return generalCount != 2;
    }

    public TeamType calculateWinnerTeam() {
        if (!isGameOver()) {
            throw new IllegalStateException("아직 게임이 끝나지 않았습니다.");
        }
        return positionPieceMap.values()
            .stream()
            .filter(piece -> piece.getPieceType() == PieceType.GENERAL)
            .map(Piece::getTeamType)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("아직 게임이 끝나지 않았습니다."));
    }

    public boolean canMove(final Position position, final Direction direction) {
        return DEFAULT_MOVABLE_DIRECTIONS.contains(direction) || Palace.hasDirection(position,
            direction);
    }

    public double calculateScoreByTeam(final TeamType teamType) {
        return positionPieceMap.values()
            .stream()
            .filter(piece -> piece.getTeamType() == teamType)
            .mapToDouble(Piece::getScore)
            .sum() + teamType.getScoreOffset();
    }

    public Map<Position, Piece> getPositionPieceMap() {
        return positionPieceMap;
    }
}
