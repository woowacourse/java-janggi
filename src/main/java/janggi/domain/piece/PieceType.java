package janggi.domain.piece;

import janggi.domain.Team;
import java.util.Arrays;

public enum PieceType {
    General("한", "초"),
    Chariot("차", "차"),
    Elephant("상", "상"),
    Guard("사", "사"),
    Horse("마", "마"),
    Soldier("병", "졸"),
    Cannon("포", "포"),
    ;

    private final String redName;
    private final String greenName;

    PieceType(final String redName, final String greenName) {
        this.redName = redName;
        this.greenName = greenName;
    }

    public String getName(Piece piece) {
        PieceType target = Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType == piece.pieceType)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("기물 이름 탐색 실패"));
        String teamName = decideTeamName(target, piece.getTeam());
        return piece.getTeam().getColor() + teamName + Team.COLOR_RESET;
    }

    private String decideTeamName(PieceType pieceType, Team team) {
        if (team.isRed()) {
            return pieceType.redName;
        }
        return pieceType.greenName;
    }
}
