package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public abstract class Soldier extends PieceAbstractInterface {

    public Soldier(Set<List<Direction>> paths, MoveStrategy moveStrategy) {
        super(paths, moveStrategy);
    }
}
