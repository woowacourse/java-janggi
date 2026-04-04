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
import java.util.function.Function;

public final class PieceFactory {
    private static final PalaceMoveRule RULE = new PalaceMoveRule();
    private static final Map<PieceType, Function<Team, Piece>> CREATORS = new EnumMap<>(PieceType.class);

    static {
        CREATORS.put(PieceType.JANG, team -> new Jang(new SingleStepMoveStrategy(RULE), team));
        CREATORS.put(PieceType.SA, team -> new Sa(new SingleStepMoveStrategy(RULE), team));
        CREATORS.put(PieceType.CHA, team -> new Cha(new SlidingMoveStrategy(RULE), team));
        CREATORS.put(PieceType.PO, team -> new Po(new SlidingMoveStrategy(RULE), team));
        CREATORS.put(PieceType.MA, team -> new Ma(new MaMoveStrategy(), team));
        CREATORS.put(PieceType.SANG, team -> new Sang(new SangMoveStrategy(), team));
        CREATORS.put(PieceType.JOL, team -> new Jol(new JolMoveStrategy(RULE), team));
        CREATORS.put(PieceType.BYEONG, team -> new Byeong(new ByeongMoveStrategy(RULE), team));
        CREATORS.put(PieceType.EMPTY, team -> new EmptyPiece(new EmptyMoveStrategy(), team));
    }

    private PieceFactory() {
    }

    public static Piece create(PieceType pieceType, Team team) {
        return Optional.ofNullable(CREATORS.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 타입: " + pieceType))
                .apply(team);
    }
}
