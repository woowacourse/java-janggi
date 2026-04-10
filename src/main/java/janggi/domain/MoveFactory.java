package janggi.domain;

import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.condition.EmptyCondition;
import janggi.domain.piece.condition.OnePieceExistsCondition;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.FriendlyPalaceSingleStepStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;

public class MoveFactory {

    private MoveFactory() {
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
