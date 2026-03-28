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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record BoardDto(
    List<String> rowStatuses
) {

    private static final Map<TeamType, Map<PieceType, String>> CHINESE_MAP;
    private static final String EMPTY_SPACE = "＊";
    private static final String DELIMITER = "  ";

    static {
        CHINESE_MAP = Map.of(
            TeamType.RED, Map.of(
                PieceType.GENERAL, "漢",
                PieceType.GUARD, "士",
                PieceType.CHARIOT, "車",
                PieceType.CANNON, "包",
                PieceType.HORSE, "馬",
                PieceType.ELEPHANT, "象",
                PieceType.SOLDIER, "兵"
            ),
            TeamType.BLUE, Map.of(
                PieceType.GENERAL, "楚",
                PieceType.GUARD, "士",
                PieceType.CHARIOT, "車",
                PieceType.CANNON, "包",
                PieceType.HORSE, "馬",
                PieceType.ELEPHANT, "象",
                PieceType.SOLDIER, "卒"
            ));
    }

    public static BoardDto from(final Board board, final List<Position> movablePositions) {
        final List<String> rowStatuses = new ArrayList<>();
        final Map<Position, Piece> positionPieceMap = board.getPositionPieceMap();
        for (int row = MINIMUM_ROW; row <= MAXIMUM_ROW; row++) {
            rowStatuses.add(composeRowStatus(row, positionPieceMap, movablePositions));
        }

        return new BoardDto(rowStatuses);
    }

    private static String composeRowStatus(final int row,
        final Map<Position, Piece> positionPieceMap, final List<Position> movablePositions) {
        return IntStream.rangeClosed(MINIMUM_COLUMN, MAXIMUM_COLUMN)
            .mapToObj(column -> Position.valueOf(row, column))
            .map(position -> getNotation(position, positionPieceMap, movablePositions))
            .collect(Collectors.joining(DELIMITER));
    }

    private static String getNotation(final Position position,
        final Map<Position, Piece> positionPieceMap, List<Position> movablePositions) {
        if (positionPieceMap.containsKey(position)) {
            final Piece piece = positionPieceMap.get(position);
            final TeamType teamType = piece.getTeamType();
            return applyTeamColor(getChineseOf(piece), teamType,
                isMovable(position, movablePositions));
        }
        return applyMovableColor(EMPTY_SPACE, isMovable(position, movablePositions));
    }

    private static String applyTeamColor(final String space, final TeamType teamType,
        final boolean movable) {
        if (teamType == TeamType.RED) {
            return ConsoleColor.red(space);
        }
        if (teamType == TeamType.BLUE) {
            return ConsoleColor.blue(space);
        }
        return applyMovableColor(space, movable);
    }

    private static String applyMovableColor(final String space, final boolean movable) {
        if (movable) {
            return ConsoleColor.cyan(space);
        }
        return space;
    }

    private static boolean isMovable(final Position position,
        final List<Position> movablePositions) {
        return movablePositions.stream()
            .anyMatch(movablePosition -> Objects.equals(position, movablePosition));
    }

    private static String getChineseOf(final Piece piece) {
        final Map<PieceType, String> secondaryMap = CHINESE_MAP.get(piece.getTeamType());
        return secondaryMap.get(piece.getPieceType());
    }

}
