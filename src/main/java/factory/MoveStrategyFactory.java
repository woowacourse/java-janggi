package factory;

import domain.PieceProperty;
import domain.PieceType;
import domain.Team;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.GreendSoldierMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.NonMoveableStrategy;
import domain.strategy.RedSoldierMoveStrategy;

public class MoveStrategyFactory {

    public MoveStrategy createMoveStrategy(PieceProperty pieceProperty) {
        PieceType pieceType = pieceProperty.pieceType();

        if (pieceType == PieceType.SOLDIER) {
            return createSoldierMoveStrategy(pieceProperty.team());
        }

        return createDefaultMoveStrategy(pieceType);
    }

    private MoveStrategy createSoldierMoveStrategy(Team team) {
        if (team == Team.GREEN) {
            return new GreendSoldierMoveStrategy();
        }
        return new RedSoldierMoveStrategy();
    }

    private MoveStrategy createDefaultMoveStrategy(PieceType pieceType) {
        if (pieceType == PieceType.GUARD) {
            return new GuardMoveStrategy();
        }
        if (pieceType == PieceType.GENERAL) {
            return new GeneralMoveStrategy();
        }
        if (pieceType == PieceType.HORSE) {
            return new HorseMoveStrategy();
        }
        if (pieceType == PieceType.ELEPHANT) {
            return new ElephantMoveStrategy();
        }
        if (pieceType == PieceType.CHARIOT) {
            return new ChariotMoveStrategy();
        }
        if (pieceType == PieceType.CANNON) {
            return new CannonMoveStrategy();
        }
        return new NonMoveableStrategy();
    }
}
