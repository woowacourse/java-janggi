package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class GeneralPiece extends Piece {

    public GeneralPiece(Team team) {
        super(team, Name.GENERAL);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return canMoveOneStep(from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        return canCaptureDestinationPiece(positionPieces, to);
    }
}
