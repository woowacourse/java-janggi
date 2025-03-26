package domain.pieces;

import domain.Team;

public enum PieceNames {
    CANNON("包", "포"),
    CHARIOT("車", "차"),
    ELEPHANT("象", "상"),
    HORSE("馬", "마"),
    GENERAL("將", "궁"),
    GUARD("士", "사"),
    SOLDIER("兵", "졸");

    private final String nameForHan;
    private final String nameForCho;

    PieceNames(final String nameForHan, final String nameForCho) {
        this.nameForHan = nameForHan;
        this.nameForCho = nameForCho;
    }

    public String getNameForTeam(final Team team) {
        if (team.equals(Team.HAN)) {
            return this.nameForHan;
        }
        return this.nameForCho;
    }
}
