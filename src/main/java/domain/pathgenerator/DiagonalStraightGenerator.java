package domain.pathgenerator;

import common.exception.JanggiException;
import domain.direction.Direction;
import domain.position.Position;

public class DiagonalStraightGenerator extends DirectionalPathGenerator {

    @Override
    protected void validateMove(Position source, Position destination) {
        if (source.equals(destination)) {
            throw new JanggiException("이동할 수 있는 대각선 경로가 아닙니다.");
        }

        int rowDifference = Math.abs(destination.row() - source.row());
        int columnDifference = Math.abs(destination.column() - source.column());

        if (rowDifference != columnDifference) {
            throw new JanggiException("이동할 수 있는 대각선 경로가 아닙니다.");
        }
    }

    @Override
    protected Direction determineDirection(Position source, Position destination) {
        int rowDifference = destination.row() - source.row();
        int columnDifference = destination.column() - source.column();
        return Direction.fromDiagonal(rowDifference, columnDifference);
    }
}
