package domain.piece;

import domain.score.Score;

import java.util.EnumSet;
import java.util.function.Function;

public enum PieceType {

    WANG(new Score(0), Wang::new),
    SA(new Score(3), Sa::new),
    CHA(new Score(13), Cha::new),
    SANG(new Score(3), Sang::new),
    MA(new Score(5), Ma::new),
    PO(new Score(7), Po::new),
    BYEONG(new Score(2), Byeong::new),
    ;

    private static final EnumSet<PieceType> SANG_MA = EnumSet.of(SANG, MA);

    private final Score score;
    private final Function<Team, Piece> function;

    PieceType(final Score score, final Function<Team, Piece> function) {
        this.score = score;
        this.function = function;
    }

    public Score score() {
        return score;
    }

    public Piece create(Team team) {
        return function.apply(team);
    }

    public static Piece createSangMaPiece(final Team team, final PieceType pieceType) {
        if (!SANG_MA.contains(pieceType)) {
            throw new IllegalArgumentException("[ERROR] 상 또는 마가 아닙니다.");
        }
        return pieceType.create(team);
    }

    public static Piece createPiece(final String team, final String pieceType) {
        Team pieceTeam = Team.valueOf(team);
        return PieceType.valueOf(pieceType).create(pieceTeam);
    }
}
