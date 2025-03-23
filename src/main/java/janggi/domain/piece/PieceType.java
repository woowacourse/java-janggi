package janggi.domain.piece;

import janggi.domain.Team;
import java.util.Arrays;

public enum PieceType {
    GENERAL("한", "초"),
    CHARIOT("차", "차"),
    ELEPHANT("상", "상"),
    GUARD("사", "사"),
    HORSE("마", "마"),
    SOLDIER("병", "졸"),
    CANNON("포", "포"),
    ;

    private final String redName;
    private final String greenName;

    PieceType(final String redName, final String greenName) {
        this.redName = redName;
        this.greenName = greenName;
    }

    public String getName(Piece piece) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType == piece.pieceType)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("기물 이름 탐색 실패"))
                .decideTeamName(piece.getTeam());
    }

    private String decideTeamName(Team team) {
        if (team.isRed()) {
            return team.getColorName(redName);
        }
        return team.getColorName(greenName);
    }
}
