package domain.pathgenerator;

import common.exception.JanggiException;
import domain.position.Path;
import domain.position.Palace;
import domain.position.Position;

public class PalaceConstrainedPathGenerator implements PathGenerator {

    private final PathGenerator delegate;

    public PalaceConstrainedPathGenerator(PathGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        Path path = delegate.calculatePath(source, destination);

        if (!isDiagonalMove(source, destination)) {
            return path;
        }

        if (isPalaceDiagonalReachable(source, destination)) {
            return path;
        }

        throw new JanggiException("이동할 수 있는 직선/대각선 경로가 아닙니다.");
    }

    private boolean isPalaceDiagonalReachable(Position source, Position destination) {
        return Palace.findBy(source)
                .filter(palace -> palace.contains(destination))
                .map(palace -> palace.isDiagonalReachable(source, destination))
                .orElse(false);
    }

    private boolean isDiagonalMove(Position source, Position destination) {
        int rowDiff = Math.abs(source.row() - destination.row());
        int columnDiff = Math.abs(source.column() - destination.column());
        return rowDiff > 0 && rowDiff == columnDiff;
    }
}
