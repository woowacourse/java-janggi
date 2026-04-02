package domain.piece;

import domain.Side;
import domain.strategy.ContinuousStrategy;
import domain.strategy.Direction;
import domain.strategy.MovementStrategy;
import domain.strategy.OneStepStrategy;
import domain.strategy.SequenceStrategy;
import java.util.Map;

public class PieceFactory {
    private static final MovementStrategy LINEAR_ONE_STEP = new OneStepStrategy(Direction.linear());
    private static final MovementStrategy CHO_SOLDIER_STRATEGY = new OneStepStrategy(Direction.choSoldier());
    private static final MovementStrategy HAN_SOLDIER_STRATEGY = new OneStepStrategy(Direction.hanSoldier());
    private static final MovementStrategy HORSE_STRATEGY = new SequenceStrategy(Direction.horseSequences());
    private static final MovementStrategy ELEPHANT_STRATEGY = new SequenceStrategy(Direction.elephantSequences());
    private static final MovementStrategy CONTINUOUS_STRATEGY = new ContinuousStrategy(Direction.linear());

    private static final Map<Side, General> GENERALS = Map.of(
            Side.CHO, new General(Side.CHO, LINEAR_ONE_STEP),
            Side.HAN, new General(Side.HAN, LINEAR_ONE_STEP)
    );
    private static final Map<Side, Guard> GUARDS = Map.of(
            Side.CHO, new Guard(Side.CHO, LINEAR_ONE_STEP),
            Side.HAN, new Guard(Side.HAN, LINEAR_ONE_STEP)
    );
    private static final Map<Side, Soldier> SOLDIERS = Map.of(
            Side.CHO, new Soldier(Side.CHO, CHO_SOLDIER_STRATEGY),
            Side.HAN, new Soldier(Side.HAN, HAN_SOLDIER_STRATEGY)
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
            Side.CHO, new Chariot(Side.CHO, CONTINUOUS_STRATEGY),
            Side.HAN, new Chariot(Side.HAN, CONTINUOUS_STRATEGY)
    );
    private static final Map<Side, Cannon> CANNONS = Map.of(
            Side.CHO, new Cannon(Side.CHO, CONTINUOUS_STRATEGY),
            Side.HAN, new Cannon(Side.HAN, CONTINUOUS_STRATEGY)
    );

    private PieceFactory() {}

    public static General createGeneral(Side side) { return GENERALS.get(side); }
    public static Guard createGuard(Side side) { return GUARDS.get(side); }
    public static Horse createHorse(Side side) { return HORSES.get(side); }
    public static Elephant createElephant(Side side) { return ELEPHANTS.get(side); }
    public static Chariot createChariot(Side side) { return CHARIOTS.get(side); }
    public static Cannon createCannon(Side side) { return CANNONS.get(side); }
    public static Soldier createSoldier(Side side) { return SOLDIERS.get(side); }
}
