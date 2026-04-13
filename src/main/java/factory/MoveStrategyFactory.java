package factory;

import domain.PieceProperty;
import domain.PieceType;
import domain.Team;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.NonMoveableStrategy;
import domain.strategy.PalaceMoveStrategy;
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
            return new GreenSoldierMoveStrategy();
        }
        return new RedSoldierMoveStrategy();
    }

    private MoveStrategy createDefaultMoveStrategy(PieceType pieceType) {
        if (pieceType == PieceType.GUARD) {
            return PalaceMoveStrategy.getInstance();
        }
        if (pieceType == PieceType.GENERAL) {
            return PalaceMoveStrategy.getInstance();
        }
        if (pieceType == PieceType.HORSE) {
            return HorseMoveStrategy.getInstance();
        }
        if (pieceType == PieceType.ELEPHANT) {
            return ElephantMoveStrategy.getInstance();
        }
        if (pieceType == PieceType.CHARIOT) {
            return ChariotMoveStrategy.getInstance();
        }
        if (pieceType == PieceType.CANNON) {
            return CannonMoveStrategy.getInstance();
        }
        return NonMoveableStrategy.getInstance();
    }
}
