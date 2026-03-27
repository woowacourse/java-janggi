package janggi.presentation.dto;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import java.util.List;
import java.util.Map;

public record GameStatusInfo(
        List<List<String>> pieces
) {
    private static final Map<PieceType, String> DISPLAY_NAMES = Map.of(
            PieceType.CHA, "차",
            PieceType.PHO, "포",
            PieceType.MA, "마",
            PieceType.SANG, "상",
            PieceType.JOL, "졸",
            PieceType.SA, "사",
            PieceType.JANG, "장"
    );

    public static GameStatusInfo from(List<List<Piece>> board) {
        return new GameStatusInfo(
                board.stream()
                        .map(GameStatusInfo::getPieceNames)
                        .toList()
                        .reversed()
        );
    }

    private static List<String> getPieceNames(List<Piece> row) {
        return row.stream()
                .map(point -> {
                    if (point == null) {
                        return "\u001B[0m+";
                    }
                    if (point.isSameTeam(Team.CHO)) {
                        return "\u001B[32m" + DISPLAY_NAMES.get(point.getType()) + "\u001B[0m";
                    }
                    return "\u001B[31m" + DISPLAY_NAMES.get(point.getType()) + "\u001B[0m";
                }).toList();
    }
}
