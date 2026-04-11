package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.*;

import java.util.Map;
import java.util.function.Function;

public class PieceFactory {

    private PieceFactory() {
    }

    private static final Map<String, Function<Camp, Piece>> PIECE_CREATORS = Map.of(
            "GENERAL", camp -> new General(camp, new GeneralStrategy()),
            "ADVISOR", camp -> new Advisor(camp, new AdvisorStrategy()),
            "CHARIOT", camp -> new Chariot(camp, new ChariotStrategy()),
            "CANNON", camp -> new Cannon(camp, new CannonStrategy()),
            "HORSE", camp -> new Horse(camp, new HorseStrategy()),
            "ELEPHANT", camp -> new Elephant(camp, new ElephantStrategy()),
            "SOLDIER", camp -> new Soldier(camp, new SoldierStrategy(camp.forward()))
    );

    public static Piece create(String pieceType, Camp camp) {
        validatePieceType(pieceType);
        return PIECE_CREATORS.get(pieceType).apply(camp);
    }

    private static void validatePieceType(String pieceType) {
        if (!PIECE_CREATORS.containsKey(pieceType)) {
            throw new IllegalArgumentException("존재하지 않는 기물 타입입니다: " + pieceType);
        }
    }
}
