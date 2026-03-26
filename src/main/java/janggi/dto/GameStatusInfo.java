package janggi.dto;

import janggi.domain.piece.Piece;
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
                        return "+";
                    }
                    return point.getType().getName();
                }).toList();
    }
}
