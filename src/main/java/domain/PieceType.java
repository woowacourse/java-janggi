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

    GENERAL("將", PalacePieceMoveStrategy::of),
    GUARD("士", PalacePieceMoveStrategy::of),

    HORSE("馬", HorseMoveStrategy::of),
    ELEPHANT("象", ElephantMoveStrategy::of),
    CHARIOT("車", ChariotMoveStrategy::of),
    CANNON("包", CannonMoveStrategy::of),

    RED_SOLDIER("卒", RedSoldierMoveStrategy::of),
    GREEN_SOLDIER("卒", GreenSoldierMoveStrategy::of),

    EMPTY_VALUE("＋", NoneMoveableStrategy::of),
    ;

    private final String description;
    private final Function<Position, MoveStrategy> strategyOfPosition;

    PieceType(String description, Function<Position, MoveStrategy> strategyOfPosition) {
        this.description = description;
        this.strategyOfPosition = strategyOfPosition;
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
}
