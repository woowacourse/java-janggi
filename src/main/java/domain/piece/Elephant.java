package domain.piece;

import domain.position.Position;
import domain.game.Team;
import java.util.List;
import java.util.stream.IntStream;

public class Elephant extends Piece {

    private static final List<Integer> ROW_OFFSETS = List.of(2, 3, -2, -3, -3, -2, 2, 3);
    private static final List<Integer> COLUMN_OFFSETS = List.of(3, 2, 3, 2, -2, -3, -3, -2);

    public Elephant(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        int rowDiff = target.rowDiff(source);
        int colDiff = target.columnDiff(source);

        return IntStream.range(0, ROW_OFFSETS.size())
                .anyMatch(i -> ROW_OFFSETS.get(i) == rowDiff && COLUMN_OFFSETS.get(i) == colDiff);
    }

    @Override
    public List<Position> calculateRoute(Position source, Position target) {
        int rowDiff = source.rowDiff(target);
        int colDiff = source.columnDiff(target);
        Position firstStep = calculateFirstStep(source, rowDiff, colDiff);
        return List.of(firstStep, firstStep.middlePosition(target));
    }

    private Position calculateFirstStep(Position source, int rowDiff, int colDiff) {
        if (Math.abs(colDiff) == 3) {
            return source.addPosition(0, -Integer.signum(colDiff));
        }
        return source.addPosition(-Integer.signum(rowDiff), 0);
    }

}
