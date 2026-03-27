package janggi.dto;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;
import static janggi.domain.Position.MINIMUM_COLUMN;
import static janggi.domain.Position.MINIMUM_ROW;

import janggi.domain.piece.PieceType;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.view.ConsoleColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(
    List<String> rowStatuses
) {

    private static final Map<TeamType, Map<PieceType, String>> CHINESE_MAP;
    private static final String EMPTY_SPACE = "＊";
    private static final String DELIMITER = "  ";

    static {
        CHINESE_MAP = Map.of(
            TeamType.RED, Map.of(
                PieceType.GENERAL, ConsoleColor.red("漢"),
                PieceType.GUARD, ConsoleColor.red("士"),
                PieceType.CHARIOT, ConsoleColor.red("車"),
                PieceType.CANNON, ConsoleColor.red("包"),
                PieceType.HORSE, ConsoleColor.red("馬"),
                PieceType.ELEPHANT, ConsoleColor.red("象"),
                PieceType.SOLDIER, ConsoleColor.red("兵")
            ),
            TeamType.BLUE, Map.of(
                PieceType.GENERAL, ConsoleColor.blue("楚"),
                PieceType.GUARD, ConsoleColor.blue("士"),
                PieceType.CHARIOT, ConsoleColor.blue("車"),
                PieceType.CANNON, ConsoleColor.blue("包"),
                PieceType.HORSE, ConsoleColor.blue("馬"),
                PieceType.ELEPHANT, ConsoleColor.blue("象"),
                PieceType.SOLDIER, ConsoleColor.blue("卒")
            ));
    }

    public static BoardDto from(final Board board) {
        final List<String> rowStatuses = new ArrayList<>();
        final Map<Position, Piece> positionPieceMap = board.getPositionPieceMap();
        for (int row = MINIMUM_ROW; row <= MAXIMUM_ROW; row++) {
            rowStatuses.add(composeRowStatus(row, positionPieceMap));
        }

        return new BoardDto(rowStatuses);
    }

    private static String composeRowStatus(final int row,
        final Map<Position, Piece> positionPieceMap) {
        final StringBuilder stringBuilder = new StringBuilder();
        Position current;
        for (int column = MINIMUM_COLUMN; column <= MAXIMUM_COLUMN; column++) {
            current = Position.valueOf(row, column);
            stringBuilder.append(DELIMITER);
            stringBuilder.append(getSpace(current, positionPieceMap));
        }

        return stringBuilder.toString();
    }

    private static String getSpace(final Position position,
        final Map<Position, Piece> positionPieceMap) {
        if (positionPieceMap.containsKey(position)) {
            final Piece piece = positionPieceMap.get(position);
            return getChineseOf(piece);
        }
        return EMPTY_SPACE;
    }

    private static String getChineseOf(final Piece piece) {
        final Map<PieceType, String> secondaryMap = CHINESE_MAP.get(piece.getTeamType());
        return secondaryMap.get(piece.getPieceType());
    }

}
