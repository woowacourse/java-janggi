package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;

public class Horse extends Piece {

    private static final List<Integer> ROW_OFFSETS = List.of(1, 2, 2, 1, -1, -2, -2, -1);
    private static final List<Integer> COLUMN_OFFSETS = List.of(2, 1, -1, -2, -2, -1, 1, 2);

    public Horse(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        return java.util.stream.IntStream.range(0, ROW_OFFSETS.size())
                .anyMatch(i -> ROW_OFFSETS.get(i) == rowDiff && COLUMN_OFFSETS.get(i) == colDiff);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        int rowDiff = source.rowDiff(target);
        int colDiff = source.columnDiff(target);
        return List.of(calculateFirstStep(source, rowDiff, colDiff));
    }

    private Position calculateFirstStep(Position source, int rowDiff, int colDiff) {
        if (Math.abs(colDiff) == 2) {
            return source.addPosition(0, -Integer.signum(colDiff));
        }
        return source.addPosition(-Integer.signum(rowDiff), 0);
    }

}
