package model.piece;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import model.piece.movement.ByeongRouteFinder;
import model.piece.movement.CannonRouteFinder;
import model.piece.movement.ChariotRouteFinder;
import model.piece.movement.RouteFinder;
import model.piece.movement.ElephantRouteFinder;
import model.piece.movement.GeneralRouteFinder;
import model.piece.movement.GuardRouteFinder;
import model.piece.movement.HorseRouteFinder;
import model.piece.movement.JolRouteFinder;
import model.position.Position;

public enum PieceType {

    CHARIOT("차",13, ChariotRouteFinder::new),
    JOL("졸", 2, JolRouteFinder::new),
    BYEONG("병", 2, ByeongRouteFinder::new),
    CANNON("포", 7, CannonRouteFinder::new),
    ELEPHANT("상",3, ElephantRouteFinder::new),
    GENERAL("왕",0, GeneralRouteFinder::new),
    GUARD("사", 3, GuardRouteFinder::new),
    HORSE("마", 5, HorseRouteFinder::new),
    ;

    private final String name;
    private final int score;
    private final Supplier<RouteFinder> directionFindable;

    PieceType(String name, int score, Supplier<RouteFinder> directionFindable) {
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
        RouteFinder routeFinder = directionFindable.get();
        return routeFinder.calculateAllRoute(departure, arrival);
    }

    public static PieceType createPieceBy(String value) {
        return Arrays.stream(PieceType.values()).
            filter(pieceType -> pieceType.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 타입입니다."));
    }
}
