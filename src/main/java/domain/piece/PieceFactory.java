package domain.piece;

import domain.common.Side;
import domain.movement.strategy.CompositeMovementStrategy;
import domain.movement.strategy.ContinuousStrategy;
import domain.common.Direction;
import domain.movement.strategy.MovementStrategy;
import domain.movement.strategy.OneStepStrategy;
import domain.movement.strategy.PalaceDiagonalContinuousStrategy;
import domain.movement.strategy.PalaceGeneralGuardStrategy;
import domain.movement.strategy.PalaceSoldierDiagonalStrategy;
import domain.movement.strategy.SequenceStrategy;
import java.util.List;
import java.util.Map;

public class PieceFactory {
    private static final MovementStrategy HORSE_STRATEGY = new SequenceStrategy(Direction.horseSequences());
    private static final MovementStrategy ELEPHANT_STRATEGY = new SequenceStrategy(Direction.elephantSequences());
    private static final MovementStrategy LINEAR_CONTINUOUS_STRATEGY = new ContinuousStrategy(Direction.linear());
    private static final MovementStrategy PALACE_DIAGONAL_CONTINUOUS_STRATEGY = new PalaceDiagonalContinuousStrategy();
    private static final Map<Side, MovementStrategy> GENERAL_GUARD_STRATEGIES = Map.of(
            Side.CHO, new PalaceGeneralGuardStrategy(Side.CHO),
            Side.HAN, new PalaceGeneralGuardStrategy(Side.HAN)
    );
    private static final MovementStrategy CHARIOT_STRATEGY = new CompositeMovementStrategy(
            List.of(LINEAR_CONTINUOUS_STRATEGY, PALACE_DIAGONAL_CONTINUOUS_STRATEGY)
    );
    private static final MovementStrategy CANNON_STRATEGY = new CompositeMovementStrategy(
            List.of(LINEAR_CONTINUOUS_STRATEGY, PALACE_DIAGONAL_CONTINUOUS_STRATEGY)
    );

    private static final Map<Side, General> GENERALS = Map.of(
            Side.CHO, new General(Side.CHO, GENERAL_GUARD_STRATEGIES.get(Side.CHO)),
            Side.HAN, new General(Side.HAN, GENERAL_GUARD_STRATEGIES.get(Side.HAN))
    );
    private static final Map<Side, Guard> GUARDS = Map.of(
            Side.CHO, new Guard(Side.CHO, GENERAL_GUARD_STRATEGIES.get(Side.CHO)),
            Side.HAN, new Guard(Side.HAN, GENERAL_GUARD_STRATEGIES.get(Side.HAN))
    );
    private static final Map<Side, Soldier> SOLDIERS = Map.of(
            Side.CHO, new Soldier(Side.CHO, soldierStrategy(Side.CHO)),
            Side.HAN, new Soldier(Side.HAN, soldierStrategy(Side.HAN))
    );
    private static final Map<Side, Horse> HORSES = Map.of(
            Side.CHO, new Horse(Side.CHO, HORSE_STRATEGY),
            Side.HAN, new Horse(Side.HAN, HORSE_STRATEGY)
    );
    private static final Map<Side, Elephant> ELEPHANTS = Map.of(
            Side.CHO, new Elephant(Side.CHO, ELEPHANT_STRATEGY),
            Side.HAN, new Elephant(Side.HAN, ELEPHANT_STRATEGY)
    );
    private static final Map<Side, Chariot> CHARIOTS = Map.of(
            Side.CHO, new Chariot(Side.CHO, CHARIOT_STRATEGY),
            Side.HAN, new Chariot(Side.HAN, CHARIOT_STRATEGY)
    );
    private static final Map<Side, Cannon> CANNONS = Map.of(
            Side.CHO, new Cannon(Side.CHO, CANNON_STRATEGY),
            Side.HAN, new Cannon(Side.HAN, CANNON_STRATEGY)
    );

    private PieceFactory() {}

    public static General createGeneral(Side side) { return GENERALS.get(side); }
    public static Guard createGuard(Side side) { return GUARDS.get(side); }
    public static Horse createHorse(Side side) { return HORSES.get(side); }
    public static Elephant createElephant(Side side) { return ELEPHANTS.get(side); }
    public static Chariot createChariot(Side side) { return CHARIOTS.get(side); }
    public static Cannon createCannon(Side side) { return CANNONS.get(side); }
    public static Soldier createSoldier(Side side) { return SOLDIERS.get(side); }

    private static MovementStrategy soldierStrategy(Side side) {
        return new CompositeMovementStrategy(
                List.of(
                        new OneStepStrategy(Direction.soldier(side)),
                        new PalaceSoldierDiagonalStrategy(side)
                )
        );
    }
}
