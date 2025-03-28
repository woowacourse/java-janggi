package domain.pieces;

import domain.player.TeamType;

public enum PieceName {
    CHARIOT("車", "차"),
    CANNON("包", "포"),
    HORSE("馬", "마"),
    ELEPHANT("象", "상"),
    GUARD("士", "사"),
    SOLDIER("兵", "졸"),
    GENERAL("將", "궁"),
    ;

    private final String nameForHan;
    private final String nameForCho;

    PieceName(final String nameForHan, final String nameForCho) {
        this.nameForHan = nameForHan;
        this.nameForCho = nameForCho;
    }

    public String getNameForTeam(final TeamType teamType) {
        if (teamType.equals(TeamType.HAN)) {
            return this.nameForHan;
        }
        return this.nameForCho;
    }
}
