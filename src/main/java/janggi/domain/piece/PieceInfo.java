package janggi.domain.piece;

import janggi.domain.Camp;

public class PieceInfo {

    private final Camp camp;
    private final int score;

    private PieceInfo(Camp camp, int score) {
        this.camp = camp;
        this.score = score;
    }

    public static PieceInfo from(Camp camp, int score) {
        return new PieceInfo(camp, score);
    }

    public int getScoreIfCampMatches(Camp otherCamp) {
        if (isSameCamp(otherCamp)) {
            return score;
        }
        return 0;
    }

    public boolean isSameCamp(Camp otherCamp) {
        return camp.isSameCamp(otherCamp);
    }

    public boolean isSameCamp(PieceInfo pieceInfo) {
        return this.camp.isSameCamp(pieceInfo.camp);
    }

    public Camp getCamp() {
        return camp;
    }
}
