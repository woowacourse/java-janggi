package janggi.domain.piece;

import janggi.domain.team.TeamType;

import java.util.function.Function;

public enum PieceType {
    CHA("차", 13, Cha::new),
    GUNG("궁", 0, Gung::new),
    JOL("졸", 2, Jol::new),
    MA("마", 5, Ma::new),
    PO("포", 7, Po::new),
    SA("사", 3, Sa::new),
    SANG("상", 3, Sang::new),
    ;

    PieceType(String name, int score, Function<TeamType, Piece> createPiece) {
        this.name = name;
        this.score = score;
        this.createPiece = createPiece;
    }

    private final String name;
    private final int score;
    private final Function<TeamType, Piece> createPiece;

    public static PieceType from(String pieceTypeName) {
        for (PieceType pieceType : values()) {
            if (pieceType.name.equals(pieceTypeName)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 기물 이름입니다.");
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Piece createPiece(TeamType teamType) {
        return createPiece.apply(teamType);
    }
}
