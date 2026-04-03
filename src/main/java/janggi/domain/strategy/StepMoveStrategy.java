package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.List;

public class StepMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (baseDir.canMove(current) && !boardInfo.isAlly(current, baseDir.move(current))) {
            return Destinations.of(List.of(baseDir.move(current)));
        }
        return Destinations.empty();
    }
}
