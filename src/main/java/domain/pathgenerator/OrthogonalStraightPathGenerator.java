package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Position;

public class OrthogonalStraightPathGenerator extends DirectionalPathGenerator {

    @Override
    protected void validateMove(Position source, Position destination) {
        if (source.equals(destination)) {
            throw new JanggiException("이동할 수 있는 직선 경로가 아닙니다.");
        }

        if (source.row() != destination.row() && source.column() != destination.column()) {
            throw new JanggiException("이동할 수 있는 직선 경로가 아닙니다.");
        }
    }

    @Override
    protected Direction determineDirection(Position source, Position destination) {
        int rowDifference = destination.row() - source.row();
        int columnDifference = destination.column() - source.column();
        return Direction.fromStraight(rowDifference, columnDifference);
    }
}
