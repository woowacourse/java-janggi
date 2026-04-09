package domain.pathgenerator;

import common.exception.JanggiException;
import domain.position.Palace;
import domain.position.Path;
import domain.position.Position;

public class PalaceBoundPathGenerator implements PathGenerator {

    private final PathGenerator delegate;

    public PalaceBoundPathGenerator(PathGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        Path path = delegate.calculatePath(source, destination);
        Palace palace = Palace.findBy(source)
                .filter(found -> found.contains(destination))
                .orElseThrow(() -> new JanggiException("이동할 수 있는 직선/대각선 경로가 아닙니다."));

        if (!isDiagonalMove(source, destination)) {
            return path;
        }

        if (palace.isDiagonalReachable(source, destination)) {
            return path;
        }

        throw new JanggiException("이동할 수 있는 직선/대각선 경로가 아닙니다.");
    }

    private boolean isDiagonalMove(Position source, Position destination) {
        int rowDiff = Math.abs(source.row() - destination.row());
        int columnDiff = Math.abs(source.column() - destination.column());
        return rowDiff > 0 && rowDiff == columnDiff;
    }
}
