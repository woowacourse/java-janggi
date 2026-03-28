package janggi.domain.piece;

import janggi.domain.status.Team;
import java.util.Map;
import java.util.function.Function;

public class PieceFactory {

    private static final Map<PieceType, Function<Team, Piece>> FACTORY = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.MA, Ma::new,
            PieceType.SANG, Sang::new,
            PieceType.PHO, Pho::new,
            PieceType.SA, Sa::new,
            PieceType.JANG, Jang::new,
            PieceType.JOL, Jol::new
    );

    public static Piece initPiece(Team team, PieceType pieceType) {
        if (!FACTORY.containsKey(pieceType)) {
            throw new IllegalArgumentException("존재하지 않는 기물입니다.");
        }
        return FACTORY.get(pieceType).apply(team);
    }
}
