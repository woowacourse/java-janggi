package janggi.presentation.dto;

import janggi.domain.piece.Piece;
import janggi.domain.status.Team;
import java.util.List;

public record GameStatusInfo(
        List<List<String>> pieces
) {
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
                        return "\u001B[32m" + point.getType().getName() + "\u001B[0m";
                    }
                    return "\u001B[31m" + point.getType().getName() + "\u001B[0m";
                }).toList();
    }
}
