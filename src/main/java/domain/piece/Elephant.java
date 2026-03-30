package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public final class Elephant extends JumpMovingPiece {

    public Elephant(Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    protected void validateMoveRule(Offset offset) {
        int dx = Math.abs(offset.dx());
        int dy = Math.abs(offset.dy());

        if (!((dx == 3 && dy == 2) || (dx == 2 && dy == 3))) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generatePaths(Direction main, Direction sub) {
        Offset start = new Offset(0, 0);

        Offset step1 = start.move(main);
        Offset step2 = step1.move(main).move(sub);

        return List.of(step1, step2);
    }
}
