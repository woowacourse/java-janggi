package janggi.view;

import janggi.domain.piece.PieceColor;
import java.util.Arrays;

public enum TeamColorName {
    CHO(PieceColor.BLUE, "초나라"),
    HAN(PieceColor.RED, "한나라"),
    ;

    private final PieceColor teamColor;
    private final String teamName;

    TeamColorName(PieceColor teamColor, String teamName) {
        this.teamColor = teamColor;
        this.teamName = teamName;
    }

    public static String geNameFrom(PieceColor teamColor) {
        return Arrays.stream(TeamColorName.values())
                .filter(teamColorName -> teamColorName.teamColor == teamColor)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 팀 색상은 존재하지 않습니다."))
                .teamName;
    }

}
