package janggi.domain.piece;

import janggi.domain.status.Team;
import java.util.Map;
import java.util.function.Function;

public enum PieceType {
    CHA,
    PHO,
    MA,
    SANG,
    JANG,
    SA,
    JOL;

    private static final Map<PieceType, Function<Team, Piece>> FACTORY = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.PHO, Pho::new,
            PieceType.MA, Ma::new,
            PieceType.SANG, Sang::new,
            PieceType.SA, Sa::new,
            PieceType.JANG, Jang::new,
            PieceType.JOL, Jol::new
    );

    public static Piece createPiece(Team team, PieceType pieceType) {
        return FACTORY.get(pieceType).apply(team);
    }
}
