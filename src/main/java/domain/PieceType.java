package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.NoneMoveableStrategy;
import domain.strategy.PalacePieceMoveStrategy;
import domain.strategy.RedSoldierMoveStrategy;
import exception.JanggiDataException;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {

    GENERAL("將", PalacePieceMoveStrategy::of, 0),
    GUARD("士", PalacePieceMoveStrategy::of, 7),

    HORSE("馬", HorseMoveStrategy::of, 5),
    ELEPHANT("象", ElephantMoveStrategy::of, 3),
    CHARIOT("車", ChariotMoveStrategy::of, 13),
    CANNON("包", CannonMoveStrategy::of, 7),

    RED_SOLDIER("卒", RedSoldierMoveStrategy::of, 2),
    GREEN_SOLDIER("卒", GreenSoldierMoveStrategy::of, 2),

    EMPTY_VALUE("＋", NoneMoveableStrategy::of, 0),
    ;

    private final String description;
    private final Function<Position, MoveStrategy> strategyOfPosition;
    private final int point;

    PieceType(String description, Function<Position, MoveStrategy> strategyOfPosition, int point) {
        this.description = description;
        this.strategyOfPosition = strategyOfPosition;
        this.point = point;
    }

    public static PieceType fromDescription(String description) {
        return Arrays.stream(values())
                .filter(type -> type.description.equals(description)
                        && type != GREEN_SOLDIER
                        && type != RED_SOLDIER)
                .findFirst()
                .orElseThrow(() -> new JanggiDataException("알 수 없는 기물 이름: " + description));
    }

    public String description() {
        return this.description;
    }

    public MoveStrategy createStrategy(Position position) {
        return strategyOfPosition.apply(position);
    }

    public int point() {
        return this.point;
    }
}
