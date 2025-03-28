package janggi.piece;

import janggi.game.Score;

public enum PieceInformation {
    GUNG(new Score(0), "궁", 1),
    CHA(new Score(13), "차", 2),
    PO(new Score(7), "포", 2),
    MA(new Score(5), "마", 2),
    SANG(new Score(3), "상", 2),
    SA(new Score(3), "사", 2),
    BYEONG(new Score(2), "병", 5),
    ;

    private final Score score;
    private final String name;
    private final int countPerTeam;

    PieceInformation(Score score, String name, int countPerTeam) {
        this.score = score;
        this.name = name;
        this.countPerTeam = countPerTeam;
    }

    public static Score calculateTotalScore() {
        Score totalScore = new Score(0);
        for (PieceInformation pieceInformation : PieceInformation.values()) {
            Score pieceTotalScore = pieceInformation.score.multiply(pieceInformation.countPerTeam);
            totalScore = totalScore.plus(pieceTotalScore);
        }
        return totalScore;
    }

    public Score getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
