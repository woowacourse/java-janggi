package piece;

import board.Board;
import board.Position;
import java.util.List;
import java.util.Map;

public enum PieceType {

    GENERAL(
            List.of(new Position(2, 5)),
            List.of(new Position(9, 5)),
            (now, destination, board, teamType) -> {
                return now.calculateDistance(destination) == 1;
            }
    ),
    GUARD(
            List.of(new Position(1, 4), new Position(1, 6)),
            List.of(new Position(10, 4), new Position(10, 6)),
            (now, destination, board, teamType) -> {
                return now.calculateDistance(destination) == 1;
            }
    ),
    HORSE(
            List.of(new Position(1, 2), new Position(1, 8)),
            List.of(new Position(10, 3), new Position(10, 7)),
            (now, destination, board, teamType) -> {
                if (now.calculateDistance(destination) != Math.sqrt(5)) {
                    return false;
                }
                final Position position = now.calculateHorseMiddlePosition(destination);
                if (board.existPieceByPosition(position)) {
                    return false;
                }
                return true;
            }
    ),
    ELEPHANT(
            List.of(new Position(1, 3), new Position(1, 7)),
            List.of(new Position(10, 2), new Position(10, 8)),
            (now, destination, board, teamType) -> {
                if (now.calculateDistance(destination) != Math.sqrt(13)) {
                    return false;
                }

                final List<Position> positions = now.calculateElephantMiddlePositions(destination);
                for (Position position : positions) {
                    if (board.existPieceByPosition(position)) {
                        return false;
                    }
                }
                return true;
            }
    ),
    CHARIOT(
            List.of(new Position(1, 1), new Position(1, 9)),
            List.of(new Position(10, 1), new Position(10, 9)),
            (now, destination, board, teamType) -> {
                if (!now.isSameLine(destination)) {
                    return false;
                }
                final List<Position> positions = now.calculateBetweenPositions(destination);
                for (final Position position : positions) {
                    if (board.existPieceByPosition(position)) {
                        return false;
                    }
                }
                return true;
            }
    ),
    CANNON(
            List.of(new Position(3, 2), new Position(3, 8)),
            List.of(new Position(8, 2), new Position(8, 8)),
            (now, destination, board, teamType) -> {
                if (!now.isSameLine(destination) || board.isCannonByPosition(destination)) {
                    return false;
                }

                int count = 0;
                final List<Position> positions = now.calculateBetweenPositions(destination);
                for (final Position position : positions) {
                    if (board.existPieceByPosition(position)) {
                        count++;

                        if (board.isCannonByPosition(position)) {
                            return false;
                        }
                    }
                }

                if (count != 1) {
                    return false;
                }
                return true;
            }
    ),
    SOLDIER(
            List.of(new Position(4, 1), new Position(4, 3), new Position(4, 5), new Position(4, 7), new Position(4, 9)),
            List.of(new Position(7, 1), new Position(7, 3), new Position(7, 5), new Position(7, 7), new Position(7, 9)),
            (now, destination, board, teamType) -> {
                if (teamType == TeamType.RED) {
                    return now.calculateDistance(destination) == 1 && now.isXLessThan(destination);
                }
                return now.calculateDistance(destination) == 1 && now.isXGreaterThan(destination);
            }
    );

    private final Map<TeamType, List<Position>> initPositions;
    private final QuadruplePredicate<Position, Position, Board, TeamType> isAbleMove;

    PieceType(final List<Position> redTeam, final List<Position> blueTeam,
              final QuadruplePredicate<Position, Position, Board, TeamType> isAbleMove
    ) {
        initPositions = Map.of(
                TeamType.RED, redTeam,
                TeamType.BLUE, blueTeam
        );
        this.isAbleMove = isAbleMove;
    }

    public List<Position> getInitPositions(final TeamType teamType) {
        return initPositions.get(teamType);
    }

    public boolean isAbleMove(
            final Position now,
            final Position destination,
            final Board board,
            final TeamType teamType
    ) {
        if (board.existPieceByPosition(destination) && board.equalsTeamTypeByPosition(destination, teamType)) {
            return false;
        }
        return isAbleMove.test(now, destination, board, teamType);
    }
}
