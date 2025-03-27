package model.piece;

import model.Team;
import model.piece.goongsungpiece.*;

import java.util.Arrays;

public enum PieceInfo {
    JANG("漢", 0),
    SA("士", 3),
    SANG("象", 3),
    MA("馬", 5),
    CHA("車", 13),
    PHO("包", 7),
    BYEONG("兵", 2);

    private final String name;
    private final double score;

    PieceInfo(String name, long score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public static Piece createPieceWithName(String pieceName, String teamName) {
        if (pieceName.equals(JANG.getName())) {
            return new Jang(Team.getTeamWithName(teamName));
        }
        if (pieceName.equals(SA.getName())) {
            return new Sa(Team.getTeamWithName(teamName));
        }
        if (pieceName.equals(SANG.getName())) {
            return new Sang(Team.getTeamWithName(teamName));
        }
        if (pieceName.equals(MA.getName())) {
            return new Ma(Team.getTeamWithName(teamName));
        }
        if (pieceName.equals(CHA.getName())) {
            return new Cha(Team.getTeamWithName(teamName));
        }
        if (pieceName.equals(PHO.getName())) {
            return new Pho(Team.getTeamWithName(teamName));
        }
        return new Byeong(Team.getTeamWithName(teamName));
    }
}
