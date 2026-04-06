package domain.piece;

import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.EmptyMoveStrategy;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public final class PieceFactory {
    private static final PalaceMoveRule RULE = new PalaceMoveRule();
    private static final Map<PieceType, BiFunction<Long, Team, Piece>> CREATORS = new EnumMap<>(PieceType.class);

    static {
        CREATORS.put(PieceType.JANG, (id, team) -> new Jang(id, new SingleStepMoveStrategy(RULE), team));
        CREATORS.put(PieceType.SA, (id, team) -> new Sa(id, new SingleStepMoveStrategy(RULE), team));
        CREATORS.put(PieceType.CHA, (id, team) -> new Cha(id, new SlidingMoveStrategy(RULE), team));
        CREATORS.put(PieceType.PO, (id, team) -> new Po(id, new SlidingMoveStrategy(RULE), team));
        CREATORS.put(PieceType.MA, (id, team) -> new Ma(id, new MaMoveStrategy(), team));
        CREATORS.put(PieceType.SANG, (id, team) -> new Sang(id, new SangMoveStrategy(), team));
        CREATORS.put(PieceType.JOL, (id, team) -> new Jol(id, new JolMoveStrategy(RULE), team));
        CREATORS.put(PieceType.BYEONG, (id, team) -> new Byeong(id, new ByeongMoveStrategy(RULE), team));
        CREATORS.put(PieceType.EMPTY, (id, team) -> new EmptyPiece(id, new EmptyMoveStrategy(), team));
    }

    private PieceFactory() {
    }

    public static Piece create(PieceType pieceType, Team team) {
        return Optional.ofNullable(CREATORS.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 타입: " + pieceType))
                .apply(null, team);
    }

    public static Piece createWithId(Long id, PieceType pieceType, Team team) {
        return Optional.ofNullable(CREATORS.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 타입: " + pieceType))
                .apply(id, team);
    }
}
