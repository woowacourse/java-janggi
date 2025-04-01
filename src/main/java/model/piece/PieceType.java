package model.piece;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import model.piece.movement.ByeongDirectionFinder;
import model.piece.movement.CannonDirectionFinder;
import model.piece.movement.ChariotDirectionFinder;
import model.piece.movement.DirectionFinder;
import model.piece.movement.ElephantDirectionFinder;
import model.piece.movement.GeneralDirectionFinder;
import model.piece.movement.GuardDirectionFinder;
import model.piece.movement.HorseDirectionFinder;
import model.piece.movement.JolDirectionFinder;
import model.position.Position;

public enum PieceType {

    CHARIOT("차",13, ChariotDirectionFinder::new),
    JOL("졸", 2, JolDirectionFinder::new),
    BYEONG("병", 2, ByeongDirectionFinder::new),
    CANNON("포", 7, CannonDirectionFinder::new),
    ELEPHANT("상",3, ElephantDirectionFinder::new),
    GENERAL("왕",0, GeneralDirectionFinder::new),
    GUARD("사", 3, GuardDirectionFinder::new),
    HORSE("마", 5, HorseDirectionFinder::new),
    ;

    private final String name;
    private final int score;
    private final Supplier<DirectionFinder> directionFindable;

    PieceType(String name, int score, Supplier<DirectionFinder> directionFindable) {
        this.name = name;
        this.score = score;
        this.directionFindable = directionFindable;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        DirectionFinder directionFinder = directionFindable.get();
        return directionFinder.calculateAllDirection(departure, arrival);
    }

    public static PieceType createPieceBy(String value) {
        return Arrays.stream(PieceType.values()).
            filter(pieceType -> pieceType.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다."));
    }
}
