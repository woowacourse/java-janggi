package janggi.domain.piece.movement;

import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.movement.condition.EmptyCondition;
import janggi.domain.piece.movement.condition.OnePieceExistsCondition;
import janggi.domain.piece.movement.strategy.ElephantStrategy;
import janggi.domain.piece.movement.strategy.FriendlyPalaceSingleStepStrategy;
import janggi.domain.piece.movement.strategy.HorseStrategy;
import janggi.domain.piece.movement.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.movement.strategy.SoldierStrategy;

public class MovementFactory {

    private MovementFactory() {
    }

    public static Movement create(Camp camp, PieceType pieceType) {
        return switch (pieceType) {
            case CHARIOT -> new Movement(new MultiStepStraightStrategy(), new EmptyCondition());
            case CANNON -> new Movement(new MultiStepStraightStrategy(), new OnePieceExistsCondition(PieceType.CANNON));
            case HORSE -> new Movement(new HorseStrategy(), new EmptyCondition());
            case ELEPHANT -> new Movement(new ElephantStrategy(), new EmptyCondition());
            case GUARD, GENERAL -> new Movement(new FriendlyPalaceSingleStepStrategy(camp), new EmptyCondition());
            case SOLDIER -> new Movement(new SoldierStrategy(camp), new EmptyCondition());
        };
    }
}
