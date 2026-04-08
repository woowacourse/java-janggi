package domain.piece;

import domain.Direction;
import domain.Offset;
import domain.board.Palace;
import exception.ErrorMessage;
import domain.board.Position;

import java.util.List;
import java.util.Optional;

public final class Elephant extends JumpMovingPiece {

    public Elephant(Team team) {
        super(PieceType.ELEPHANT, team);
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
        Offset start = new Offset(0, 0);
        Offset step1 = start.add(main.unit());
        Offset step2 = step1.add(main.unit()).add(sub.unit());
        return List.of(step1, step2);
    }

    @Override
    protected boolean isValidMove(Offset offset) {
        return (offset.absX() == 3 && offset.absY() == 2) || (offset.absX() == 2 && offset.absY() == 3);
    }
}
