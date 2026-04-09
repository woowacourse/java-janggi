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
import janggi.utils.Characters;
import janggi.view.ConsoleColor;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record BoardDto(
    List<Integer> rows,
    String columns,
    List<String> rowStatuses,
    Map<String, Double> teamScoreMap
) {

    private static final Map<TeamType, Map<PieceType, String>> PIECE_NOTATION_MAP;
    private static final String EMPTY_SPACE = String.valueOf(Characters.toFullWidth('*'));
    private static final String DELIMITER = String.valueOf(Characters.toFullWidth(' '));

    static {
        PIECE_NOTATION_MAP = Map.of(
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
        final List<Integer> rows = IntStream.rangeClosed(MINIMUM_ROW, MAXIMUM_ROW)
            .boxed().toList();
        final List<String> rowStatuses = rows.stream()
            .map(row -> composeRowStatus(row, board.getPositionPieceMap(), movablePositions))
            .toList();
        final String columns = IntStream.rangeClosed(MINIMUM_COLUMN, MAXIMUM_COLUMN)
            .mapToObj(column -> Character.forDigit(column, 10))
            .map(column -> String.valueOf(Characters.toFullWidth(column)))
            .collect(Collectors.joining(DELIMITER));
        final Map<String, Double> teamScoreMap = new LinkedHashMap<>();
        Arrays.stream(TeamType.values())
            .forEach(teamType -> teamScoreMap.put(teamType.getName(),
                board.calculateScoreByTeam(teamType)));

        return new BoardDto(rows, columns, rowStatuses, teamScoreMap);
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
            return decidePieceNotation(getNotationOfPiece(piece), teamType,
                isMovable(position, movablePositions));
        }
        return decideEmptyNotation(isMovable(position, movablePositions));
    }

    private static String decidePieceNotation(final String space, final TeamType teamType,
        final boolean movable) {
        if (movable) {
            return ConsoleColor.cyan(space);
        }
        if (teamType == TeamType.RED) {
            return ConsoleColor.red(space);
        }
        return ConsoleColor.blue(space);
    }

    private static String decideEmptyNotation(final boolean movable) {
        if (movable) {
            return ConsoleColor.cyan(BoardDto.EMPTY_SPACE);
        }
        return BoardDto.EMPTY_SPACE;
    }

    private static boolean isMovable(final Position position,
        final List<Position> movablePositions) {
        return movablePositions.stream()
            .anyMatch(movablePosition -> Objects.equals(position, movablePosition));
    }

    private static String getNotationOfPiece(final Piece piece) {
        final Map<PieceType, String> secondaryMap = PIECE_NOTATION_MAP.get(piece.getTeamType());
        return secondaryMap.get(piece.getPieceType());
    }

}
