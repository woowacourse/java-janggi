package factory;

import domain.place.Place;
import domain.place.moveStrategy.CannonMoveStrategy;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceEmptyMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceJumpMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceStraightMoveStrategy;
import domain.place.piece.Cannon;
import domain.place.piece.Chariot;
import domain.place.piece.Elephant;
import domain.place.piece.General;
import domain.place.piece.Guard;
import domain.place.piece.Horse;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceFactory {

    GENERAL("궁", side -> new General(side, new GeneralMoveStrategy(), new PalaceOneStepMoveStrategy())),
    GUARD("사", side -> new Guard(side, new GuardMoveStrategy(), new PalaceOneStepMoveStrategy())),
    HORSE("마", side -> new Horse(side, new HorseMoveStrategy(), new PalaceEmptyMoveStrategy())),
    ELEPHANT("상", side -> new Elephant(side, new ElephantMoveStrategy(), new PalaceEmptyMoveStrategy())),
    CHARIOT("차", side -> new Chariot(side, new ChariotMoveStrategy(), new PalaceStraightMoveStrategy())),
    CANNON("포", side -> new Cannon(side, new CannonMoveStrategy(), new PalaceJumpMoveStrategy())),
    SOLDIER("졸", side -> new Soldier(side, new SoldierMoveStrategy(side), new PalaceSoldierMoveStrategy(side)));

    private final String code;
    private final Function<Side, Place> factory;

    PieceFactory(String code, Function<Side, Place> factory) {
        this.code = code;
        this.factory = factory;
    }

    public static PieceFactory from(String code) {
        return Arrays.stream(PieceFactory.values())
                .filter(pieceFactory -> pieceFactory.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 기물 정보를 찾을 수 없습니다"));
    }

    public String getCode() {
        return code;
    }

    public Place createPlace(Side side) {
        return factory.apply(side);
    }
}