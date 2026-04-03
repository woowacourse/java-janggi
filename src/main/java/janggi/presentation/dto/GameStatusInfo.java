package janggi.presentation.dto;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.status.Team;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public record GameStatusInfo(
        List<List<String>> pieces
) {
    private static final int BOARD_HEIGHT = 10;
    private static final int BOARD_WIDTH = 9;
    private static final Map<PieceType, String> DISPLAY_NAMES = Map.of(
            PieceType.CHA, "차",
            PieceType.PHO, "포",
            PieceType.MA, "마",
            PieceType.SANG, "상",
            PieceType.JOL, "졸",
            PieceType.SA, "사",
            PieceType.JANG, "장"
    );

    public static GameStatusInfo from(Map<Point, Piece> board) {
        return new GameStatusInfo(
                getPieces(board).stream()
                        .map(GameStatusInfo::getPieceNames)
                        .toList()
                        .reversed()
        );
    }

    private static List<List<Piece>> getPieces(Map<Point, Piece> pieces) {
        return IntStream.range(0, BOARD_HEIGHT)
                .mapToObj(row -> IntStream.range(0, BOARD_WIDTH)
                        .mapToObj(col -> Point.of(col, row))
                        .map(pieces::get)
                        .toList()
                ).toList();
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
