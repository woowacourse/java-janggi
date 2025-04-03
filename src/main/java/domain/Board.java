package domain;

import domain.piece.PieceType;
import domain.position.Point;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Board {

    private final List<Position> positions;

    public Board(final List<Position> positions) {
        this.positions = new ArrayList<>(positions);
    }

    public int countPieces() {
        return positions.size();
    }

    public Position findPositionBy(final Point point) {
        return positions.stream()
                .filter(p -> p.isSame(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 말이 없습니다."));
    }

    public boolean canMoveOnPath(final Position fromPosition, final Point toPoint) {
        final List<Point> pointOnPath = fromPosition.calculatePossiblePoint(toPoint);
        final long matchCount = countPieceOnPath(pointOnPath);

        if (matchCount == 0) {
            return true;
        }
        if (matchCount != 1) {
            return false;
        }
        return canPassOverPiece(fromPosition, toPoint, getMiddlePosition(pointOnPath));
    }

    private boolean canPassOverPiece(final Position fromPosition, final Point toPoint, final Position middlePosition) {
        if (hasPieceAt(toPoint)) {
            final Position toPosition = findPositionBy(toPoint);
            return fromPosition.canPassOverPiece(middlePosition, toPosition, PieceType.CANNON);
        }
        return fromPosition.canPassOverPiece(middlePosition, PieceType.CANNON);
    }

    private long countPieceOnPath(final List<Point> pointOnPath) {
        return positions.stream()
                .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                .count();
    }

    private Position getMiddlePosition(final List<Point> pointOnPath) {
        return positions.stream()
                .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                .findFirst()
                .orElseThrow();
    }

    public void move(final Position prevPosition, final Point newPoint, final Runnable noticeRunner) {
        if (hasPieceAt(newPoint)) {
            moveAndCapture(newPoint, prevPosition, noticeRunner);
            return;
        }
        movePiece(prevPosition, newPoint);
    }

    public boolean hasPieceAt(final Point point) {
        return positions.stream()
                .anyMatch(position -> position.isSame(point));
    }

    private void movePiece(final Position prevPosition, final Point newPoint) {
        positions.remove(prevPosition);
        positions.add(prevPosition.getNextPosition(newPoint));
    }

    private boolean isOtherTeam(final Position prevPosition, final Point newPoint) {
        return prevPosition.isGreenTeam() != findPositionBy(newPoint).isGreenTeam();
    }

    private void moveAndCapture(final Point newPoint, final Position prevPosition, final Runnable noticeRunner) {
        if (isOtherTeam(prevPosition, newPoint)) {
            positions.remove(findPositionBy(newPoint));
            movePiece(prevPosition, newPoint);
            noticeRunner.run();
            return;
        }
        throw new IllegalArgumentException("해당 위치에 같은 팀 말이 있습니다.");
    }

    public boolean hasOnlyOneGeneral() {
        final long count = positions.stream()
                .filter(position -> position.isSamePieceType(PieceType.GENERAL))
                .count();

        return count == 1;
    }

    public Team determineWinTeam() {
        if (!hasOnlyOneGeneral()) {
            throw new IllegalStateException("궁이 1개일 때만 호출이 가능합니다.");
        }

        final Position position = positions.stream()
                .filter(p -> p.isSamePieceType(PieceType.GENERAL))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("궁이 존재하지 않습니다."));

        if (position.isGreenTeam()) {
            return Team.GREEN;
        }
        return Team.RED;
    }

    public Map<PieceType, Integer> countPieces(final Team team) {
        if (team.isGreenTeam()) {
            return countPieceType(Position::isGreenTeam);
        }
        return countPieceType(position -> !position.isGreenTeam());
    }

    private Map<PieceType, Integer> countPieceType(final Function<Position, Boolean> positionPredicate) {
        return positions.stream()
                .filter(positionPredicate::apply)
                .map(Position::getPieceType)
                .collect(Collectors.toMap(
                        pieceType -> pieceType,
                        pieceType -> 1,
                        Integer::sum
                ));
    }
}
