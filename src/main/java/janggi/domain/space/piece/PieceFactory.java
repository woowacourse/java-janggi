package janggi.domain.space.piece;

import java.util.Map;
import java.util.function.Function;

public final class PieceFactory {

    private static final Map<PieceType, Function<Team, Piece>> FACTORY = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.PHO, Pho::new,
            PieceType.MA, Ma::new,
            PieceType.SANG, Sang::new,
            PieceType.SA, Sa::new,
            PieceType.KING, King::new,
            PieceType.BYEONG, Byeong::new
    );

    public static Piece createPiece(Team team, PieceType pieceType) {
        return FACTORY.get(pieceType).apply(team);
    }
}
