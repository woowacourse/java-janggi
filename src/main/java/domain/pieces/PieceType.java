package domain.pieces;

import domain.player.Score;
import domain.player.TeamType;

public enum PieceType {
    CHARIOT("車", "차", new Score(13.0)),
    CANNON("包", "포", new Score(7.0)),
    HORSE("馬", "마", new Score(5.0)),
    ELEPHANT("象", "상", new Score(3.0)),
    GUARD("士", "사", new Score(3.0)),
    SOLDIER("兵", "졸", new Score(2.0)),
    GENERAL("將", "궁", new Score(Double.MAX_VALUE)),
    ;

    private final String nameForHan;
    private final String nameForCho;

    private final Score score;

    PieceType(final String nameForHan, final String nameForCho, final Score score) {
        this.nameForHan = nameForHan;
        this.nameForCho = nameForCho;
        this.score = score;
    }

    public String getNameForTeam(final TeamType teamType) {
        if (teamType.equals(TeamType.HAN)) {
            return this.nameForHan;
        }
        return this.nameForCho;
    }

    public Score getScore() {
        return score;
    }
}
