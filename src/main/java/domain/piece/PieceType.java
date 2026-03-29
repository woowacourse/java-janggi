package domain.piece;

import domain.team.Team;

public enum PieceType {

    GENERAL("楚", "漢"),
    CHARIOT("車", "車"),
    CANNON("包", "包"),
    HORSE("馬", "馬"),
    ELEPHANT("象", "象"),
    GUARD("士", "士"),
    SOLDIER("卒", "兵"),
    NONE("＋", "＋"),
    ;

    private final String choChineseCharacter;
    private final String hanChineseCharacter;

    PieceType(String choChineseCharacter, String hanChineseCharacter) {
        this.choChineseCharacter = choChineseCharacter;
        this.hanChineseCharacter = hanChineseCharacter;
    }

    public String getChineseCharacter(Team team) {
        if (team == Team.CHO) {
            return choChineseCharacter;
        }
        return hanChineseCharacter;
    }

}
