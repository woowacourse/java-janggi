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
        if (!isElephantMove(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generateRoute(Direction main, Direction sub) {
        Offset start = new Offset(0, 0);
        Offset step1 = start.move(main);
        Offset step2 = step1.move(main).move(sub);
        return List.of(step1, step2);
    }

    public boolean isElephantMove(Offset offset) {
        return (offset.absX() == 3 && offset.absY() == 2) || (offset.absX() == 2 && offset.absY() == 3);
    }
}
