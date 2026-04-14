package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.*;

import java.util.Map;
import java.util.function.Function;

public class PieceFactory {

    private PieceFactory() {
    }

    private static final Map<PieceDisplayName, Function<Camp, Piece>> PIECE_CREATORS = Map.of(
            PieceDisplayName.GENERAL, camp -> new General(camp, new GeneralStrategy()),
            PieceDisplayName.ADVISOR, camp -> new Advisor(camp, new AdvisorStrategy()),
            PieceDisplayName.CHARIOT, camp -> new Chariot(camp, new ChariotStrategy()),
            PieceDisplayName.CANNON, camp -> new Cannon(camp, new CannonStrategy()),
            PieceDisplayName.HORSE, camp -> new Horse(camp, new HorseStrategy()),
            PieceDisplayName.ELEPHANT, camp -> new Elephant(camp, new ElephantStrategy()),
            PieceDisplayName.SOLDIER, camp -> new Soldier(camp, new SoldierStrategy(camp.forward()))
    );

    public static Piece create(String pieceType, Camp camp) {
        try {
            PieceDisplayName typeName = PieceDisplayName.valueOf(pieceType);
            return PIECE_CREATORS.get(typeName).apply(camp);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 기물 타입입니다: " + pieceType);
        }
    }
}
