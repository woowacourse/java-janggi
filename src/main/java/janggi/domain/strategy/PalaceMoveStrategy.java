package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.PalacePosition;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.List;

public class PalaceMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return Destinations.empty();
        }
        Position target = baseDir.move(current);
        System.out.println(PalacePosition.isPalacePosition(target));
        if (!PalacePosition.isPalacePosition(target) || boardInfo.isAlly(current, target)) {
            return Destinations.empty();
        }
        return Destinations.of(List.of(baseDir.move(current)));
    }
}
