package domain;

import domain.piece.Cannon;
import domain.piece.General;
import domain.piece.PieceFactory;
import domain.position.Point;
import domain.position.Position;
import java.util.List;

public class Board {

    private final List<Position> positions;

    public Board(final List<Position> positions) {
        this.positions = positions;
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

        final List<Point> pointOnPath = fromPosition.test(toPoint);

        final long matchCount = positions.stream()
                .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                .count();

        if (matchCount == 0) {
            return true;
        }
        final Cannon cannon = PieceFactory.createCannon();
        if (matchCount == 1 && fromPosition.isSamePiece(cannon)) {
            final Position middlePosition = positions.stream()
                    .filter(position -> pointOnPath.stream().anyMatch(position::isSame))
                    .findFirst()
                    .orElseThrow();
            if (middlePosition.isSamePiece(cannon)) {
                return false;
            }

            return !hasPieceAt(toPoint) || !findPositionBy(toPoint).isSamePiece(cannon);
        }
        return false;
    }

    public boolean hasPieceAt(final Point point) {
        return positions.stream()
                .anyMatch(position -> position.isSame(point));
    }

    public void move(final Position prevPosition, final Point newPoint, final Runnable runner) {
        if (!hasPieceAt(newPoint)) {
            positions.remove(prevPosition);
            positions.add(prevPosition.getNextPosition(newPoint));
            return;
        }

        if (isAnotherTeam(prevPosition, newPoint)) {
            captureOtherTeamPiece(newPoint, prevPosition, runner);
            return;
        }
        throw new IllegalArgumentException("해당 위치에 같은 팀 말이 있습니다.");
    }

    private boolean isAnotherTeam(final Position prevPosition, final Point newPoint) {
        return prevPosition.isGreenTeam() != findPositionBy(newPoint).isGreenTeam();
    }

    private void captureOtherTeamPiece(final Point newPoint, final Position prevPosition, final Runnable runner) {
        positions.remove(findPositionBy(newPoint));
        positions.remove(prevPosition);
        positions.add(prevPosition.getNextPosition(newPoint));
        runner.run();
    }

    public boolean hasOnlyOneGeneral() {
        final General general = PieceFactory.createGeneral();
        final long count = positions.stream()
                .filter(position -> position.isSamePiece(general))
                .count();

        return count == 1;
    }

    public Team determineWinTeam() {
        if (!hasOnlyOneGeneral()) {
            throw new IllegalStateException("궁이 1개일 때만 호출이 가능합니다.");
        }

        final General general = PieceFactory.createGeneral();
        final Position position = positions.stream()
                .filter(p -> p.isSamePiece(general))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("궁이 존재하지 않습니다."));

        if (position.isGreenTeam()) {
            return Team.GREEN;
        }
        return Team.RED;
    }
}
