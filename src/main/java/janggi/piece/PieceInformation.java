package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.score.Score;
import java.util.function.BiFunction;

public enum PieceInformation {
    GUNG(new Score(0), "궁", 1, (Gung::new)),
    CHA(new Score(13), "차", 2, (Cha::new)),
    PO(new Score(7), "포", 2, (Po::new)),
    MA(new Score(5), "마", 2, (Ma::new)),
    SANG(new Score(3), "상", 2, (Sang::new)),
    SA(new Score(3), "사", 2, (Sa::new)),
    BYEONG(new Score(2), "병", 5, (Byeong::new)),
    ;

    private final Score score;
    private final String name;
    private final int countPerTeam;
    private final BiFunction<Team, Point, Piece> pieceGenerator;

    PieceInformation(Score score, String name, int countPerTeam, BiFunction<Team, Point, Piece> pieceGenerator) {
        this.score = score;
        this.name = name;
        this.countPerTeam = countPerTeam;
        this.pieceGenerator = pieceGenerator;
    }

    public static Score calculateTotalScore() {
        Score totalScore = new Score(0);
        for (PieceInformation pieceInformation : PieceInformation.values()) {
            Score pieceTotalScore = pieceInformation.score.multiply(pieceInformation.countPerTeam);
            totalScore = totalScore.plus(pieceTotalScore);
        }
        return totalScore;
    }

    public Piece createPiece(Team team, int rowIndex, int columnIndex) {
        return pieceGenerator.apply(team, new Point(rowIndex, columnIndex));
    }

    public Score getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
