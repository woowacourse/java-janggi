package domain.piece;

import domain.Direction;
import domain.ErrorMessage;
import domain.Offset;

import java.util.List;

public final class Horse extends JumpMovingPiece {

    public Horse(Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    protected void validateMoveRule(Offset offset) {
        if (!offset.isHorseMove()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generatePaths(Direction main, Direction sub) {
        Offset step1 = new Offset(0, 0).move(main);
        return List.of(step1);
    }
}
