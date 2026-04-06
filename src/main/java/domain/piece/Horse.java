package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Horse extends Piece {
    private static final double SCORE = 5.0;
    private static final List<Integer> ROW_OFFSETS = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private static final List<Integer> COLUMN_OFFSETS = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    public Horse(Team team) {
        super(team);
    }

    @Override
    public double score() {
        return SCORE;
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDifference = target.rowDifference(source);
        int columnDifference = target.columnDifference(source);

        return java.util.stream.IntStream.range(0, ROW_OFFSETS.size())
                .anyMatch(i -> ROW_OFFSETS.get(i) == rowDifference && COLUMN_OFFSETS.get(i) == columnDifference);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        int rowDifference = source.rowDifference(target);
        int columnDifference = source.columnDifference(target);
        return List.of(calculateFirstStep(source, rowDifference, columnDifference));
    }

    private Position calculateFirstStep(Position source, int rowDifference, int columnDifference) {
        if (Math.abs(columnDifference) == 2) {
            return source.addPosition(0, -Integer.signum(columnDifference));
        }
        return source.addPosition(-Integer.signum(rowDifference), 0);
    }

}
