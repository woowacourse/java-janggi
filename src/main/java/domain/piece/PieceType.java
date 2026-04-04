package domain.piece;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.InsidePalaceMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.OneStepMoveStrategy;
import domain.strategy.StraightMoveStrategy;
import java.util.Arrays;

public enum PieceType {
    SOLDIER("soldier", 2d, new OneStepMoveStrategy()),
    GUARD("guard", 3d, new InsidePalaceMoveStrategy()),
    ELEPHANT("elephant", 3d, new ElephantMoveStrategy()),
    HORSE("horse", 5d, new HorseMoveStrategy()),
    CANNON("cannon", 7d, new CannonMoveStrategy()),
    CHARIOT("chariot", 13d, new StraightMoveStrategy()),
    GENERAL("general", 0d, new InsidePalaceMoveStrategy()),
    ;

    private static final String NOT_FOUND_PIECE_TYPE = "[ERROR] 존재하지 않는 기물입니다.";

    private final String name;
    private final double score;
    private final MoveStrategy moveStrategy;

    PieceType(String name, double score, MoveStrategy moveStrategy) {
        this.name = name;
        this.score = score;
        this.moveStrategy = moveStrategy;
    }

    public PieceType from(String name) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_PIECE_TYPE));
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}
