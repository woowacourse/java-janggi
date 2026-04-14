package janggi.domain.piece;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class PieceFactory {
    private static final Map<PieceType, Function<Team, Piece>> CREATORS = new EnumMap<>(PieceType.class);

    static {
        CREATORS.put(PieceType.KING, King::new);
        CREATORS.put(PieceType.TANK, Tank::new);
        CREATORS.put(PieceType.CANNON, Cannon::new);
        CREATORS.put(PieceType.HORSE, Horse::new);
        CREATORS.put(PieceType.ELEPHANT, Elephant::new);
        CREATORS.put(PieceType.ADVISOR, Advisor::new);
        CREATORS.put(PieceType.SOLDIER, Soldier::new);
    }

    public static Piece create(PieceType type, Team team) {
        Function<Team, Piece> creator = CREATORS.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("생성할 수 없는 기물 타입입니다: " + type);
        }
        return creator.apply(team);
    }
}
