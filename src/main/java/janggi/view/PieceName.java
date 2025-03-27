package janggi.view;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Arrays;
import java.util.Objects;

public enum PieceName {
    GENERAL(PieceType.GENERAL, "한", "초"),
    CHARIOT(PieceType.CHARIOT, "차", "차"),
    ELEPHANT(PieceType.ELEPHANT, "상", "상"),
    GUARD(PieceType.GUARD, "사", "사"),
    HORSE(PieceType.HORSE, "마", "마"),
    SOLDIER(PieceType.SOLDIER, "병", "졸"),
    CANNON(PieceType.CANNON, "포", "포"),
    ;

    private final PieceType pieceType;
    private final String redName;
    private final String greenName;

    PieceName(final PieceType pieceType, final String redName, final String greenName) {
        this.pieceType = pieceType;
        this.redName = redName;
        this.greenName = greenName;
    }

    public static String getPieceName(Piece piece) {
        Objects.requireNonNull(piece, "기물은 null일 수 없습니다");
        return Arrays.stream(PieceName.values())
                .filter(pieceName -> pieceName.pieceType == piece.getPieceType())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("기물 이름 탐색 실패: " + piece.getPieceType()))
                .decideTeamName(piece.getTeam());
    }

    private String decideTeamName(Team team) {
        if (team.isRed()) {
            return TeamName.RED.getColorName(redName);
        }
        return TeamName.GREEN.getColorName(greenName);
    }
}
