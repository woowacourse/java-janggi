package domain.piece;

import domain.Direction;
import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.List;
import java.util.Optional;

public final class Horse extends JumpMovingPiece {

    public Horse(Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    protected void validateMoveRule(Position from, Position to, Optional<Palace> palace) {
        Offset offset = Offset.of(from, to);
        if (!isValidMove(offset)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_MOVE_RULE.getMessage());
        }
    }

    @Override
    protected List<Offset> generateRoute(Direction main, Direction sub) {
        Offset step1 = new Offset(0, 0).add(main.unit());
        return List.of(step1);
    }

    @Override
    protected boolean isValidMove(Offset offset) {
        return (offset.absX() == 2 && offset.absY() == 1) || (offset.absX() == 1 && offset.absY() == 2);
    }
}
