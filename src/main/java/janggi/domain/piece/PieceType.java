package janggi.domain.piece;

import janggi.domain.team.TeamType;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {

    GUNG("궁", 0, Gung::new),
    SA("사", 3, Sa::new),
    MA("마", 5, Ma::new),
    SANG("상", 3, Sang::new),
    CHA("차", 13, Cha::new),
    PO("포", 7, Po::new),
    JOL("졸", 2, Jol::new),
    BYEONG("병", 2, Byeong::new),
    ;

    private final String title;
    private final int score;
    private final Function<TeamType, Piece> function;

    PieceType(String title, int score, Function<TeamType, Piece> function) {
        this.title = title;
        this.score = score;
        this.function = function;
    }

    public static PieceType of(String title) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.title.equals(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 정의되지 않은 기물입니다."));
    }

    public Piece createPiece(TeamType teamType) {
        return function.apply(teamType);
    }

    public int getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }
}
