package janggi.domain.piece;

import janggi.domain.status.Team;
import java.util.Map;
import java.util.function.Function;

public enum PieceType {
    CHA("차"),
    PHO("포"),
    MA("마"),
    SANG("상"),
    JANG("장"),
    SA("사"),
    JOL("졸");

    private static final Map<PieceType, Function<Team, Piece>> FACTORY = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.PHO, Pho::new,
            PieceType.MA, Ma::new,
            PieceType.SANG, Sang::new,
            PieceType.SA, Sa::new,
            PieceType.JANG, Jang::new,
            PieceType.JOL, Jol::new
    );

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public static Piece createPiece(Team team, PieceType pieceType) {
        return FACTORY.get(pieceType).apply(team);
    }

    public String getName() {
        return name;
    }
}
