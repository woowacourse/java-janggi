package janggi.dto;

import static janggi.domain.Position.MAXIMUM_COLUMN;
import static janggi.domain.Position.MAXIMUM_ROW;
import static janggi.domain.Position.MINIMUM_COLUMN;
import static janggi.domain.Position.MINIMUM_ROW;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
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
    private static final String[] INDEX_LABELS = {"１", "２", "３", "４", "５", "６", "７", "８", "９", "10"};

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
        final Map<Position, Piece> positionPieceMap = board.getPositionPieceMapForDTO();
        rowStatuses.add(generateColumnIndex());
        for (int row = MINIMUM_ROW; row <= MAXIMUM_ROW; row++) {
            rowStatuses.add(composeRowStatus(row, positionPieceMap));
        }
        return new BoardDto(rowStatuses);
    }

    public static BoardDto from(final Board board, List<Position> movable) {
        final List<String> rowStatuses = new ArrayList<>();
        final Map<Position, Piece> positionPieceMap = board.getPositionPieceMapForDTO();
        rowStatuses.add(generateColumnIndex());
        for (int row = MINIMUM_ROW; row <= MAXIMUM_ROW; row++) {
            rowStatuses.add(composeRowStatusWithMovable(row, positionPieceMap, movable));
        }
        return new BoardDto(rowStatuses);
    }

    private static String generateColumnIndex() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DELIMITER);
        stringBuilder.append(DELIMITER);
        for (int column = MINIMUM_COLUMN; column <= MAXIMUM_COLUMN; column++) {
            stringBuilder.append(DELIMITER);
            stringBuilder.append(INDEX_LABELS[column - 1]);
        }
        return stringBuilder.toString();
    }

    private static String composeRowStatus(final int row, final Map<Position, Piece> positionPieceMap) {
        final StringBuilder stringBuilder = new StringBuilder();
        Position current;
        stringBuilder.append(DELIMITER);
        stringBuilder.append(INDEX_LABELS[row - 1]);
        for (int column = MINIMUM_COLUMN; column <= MAXIMUM_COLUMN; column++) {
            current = Position.valueOf(row, column);
            stringBuilder.append(DELIMITER);
            stringBuilder.append(getSpace(current, positionPieceMap));
        }

        return stringBuilder.toString();
    }

    private static String composeRowStatusWithMovable(final int row, final Map<Position, Piece> positionPieceMap,
                                                      List<Position> movable) {
        final StringBuilder stringBuilder = new StringBuilder();
        Position current;
        stringBuilder.append(DELIMITER);
        stringBuilder.append(INDEX_LABELS[row - 1]);
        for (int column = MINIMUM_COLUMN; column <= MAXIMUM_COLUMN; column++) {
            current = Position.valueOf(row, column);
            stringBuilder.append(DELIMITER);
            if (movable.contains(current)) {
                stringBuilder.append(getSpaceWithBackGround(current, positionPieceMap));
                continue;
            }
            stringBuilder.append(getSpace(current, positionPieceMap));
        }

        return stringBuilder.toString();
    }

    private static String getSpace(final Position position, final Map<Position, Piece> positionPieceMap) {
        if (positionPieceMap.containsKey(position)) {
            final Piece piece = positionPieceMap.get(position);
            return getChineseOf(piece);
        }
        return EMPTY_SPACE;
    }

    private static String getSpaceWithBackGround(final Position position, final Map<Position, Piece> positionPieceMap) {
        if (positionPieceMap.containsKey(position)) {
            final Piece piece = positionPieceMap.get(position);
            return ConsoleColor.getGreenBackground(getChineseOf(piece));
        }
        return ConsoleColor.getGreenBackground(EMPTY_SPACE);
    }

    private static String getChineseOf(final Piece piece) {
        final Map<PieceType, String> secondaryMap = CHINESE_MAP.get(piece.getTeamTypeForDTO());
        return secondaryMap.get(piece.getPieceTypeForDTO());
    }
}
