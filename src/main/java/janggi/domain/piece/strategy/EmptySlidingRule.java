package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import java.util.List;

public class EmptySlidingRule extends SlidingRule {

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        List<Position> path = findPath(source, directionInformation);
        validateEmptyPath(path, board);
    }
}
