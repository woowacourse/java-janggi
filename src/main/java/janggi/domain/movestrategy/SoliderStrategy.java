package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;

import java.util.List;
import java.util.Map;

public class SoliderStrategy implements MoveStrategy {
    public static final int HAN_DIRECTION = 1;
    public static final int CHO_DIRECTION = -1;

    private final Team team;

    public SoliderStrategy(Team team) {
        this.team = team;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int yDirection = HAN_DIRECTION;
        if (team == Team.CHO) {
            yDirection = CHO_DIRECTION;
        }
        int xDistance = from.calculateX(to);
        int yDistance = from.calculateY(to);
        return (xDistance == 0 && yDistance == yDirection)
                || (Math.abs(xDistance) == 1 && yDistance == 0);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean determineMovingRule(Piece sourcePiece, Map<Position, Piece> positionPieces, Position to) {
        for (Piece piece : positionPieces.values()) {
            if (piece.isSameTeam(sourcePiece)) {
                return false;
            }
        }
        return true;
    }

}
