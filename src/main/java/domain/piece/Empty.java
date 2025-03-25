package domain.piece;

import domain.board.Position;
import domain.rule.DefaultMoveRule;
import java.util.List;

public class Empty extends Piece {

    private static final Empty INSTANCE = new Empty();

    public Empty() {
        super(PieceType.EMPTY, PieceColor.NONE, DefaultMoveRule.getInstance());
    }

    public static Empty getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValidMovement(Position source, Position destination) {
        return false;
    }

    @Override
    public List<Position> findAllRoute(Position source, Position destination) {
        return List.of();
    }
}
