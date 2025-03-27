package domain.piece;

import domain.board.MovePath;
import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Empty extends Piece {

    private static final Empty INSTANCE = new Empty();

    private Empty() {
        super(PieceType.EMPTY, PieceColor.NONE, DefaultMoveRule.getInstance());
    }

    public static Empty getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidMovement(MovePath movePath) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(MovePath movePath) {
        return List.of();
    }
}
