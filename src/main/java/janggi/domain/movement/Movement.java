package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Movement {
    private static final int MAX_DISTANCE = 10;
    private final Direction direction;

    public Movement(Direction direction) {
        this.direction = direction;
    }

    public boolean canMove(final Position from) {
        return from.checkNextBound(direction);
    }

    // 마지막 movement에서만 사용, 1칸 이동으로 고정
    public boolean hasReachablePosition(Position from, final TeamType teamType, final BoardMediator boardMediator) {
        Position to = calculateNextPosition(from);
        return !hasPieceAt(to, boardMediator) || !boardMediator.isSameTeamType(to, teamType);
    }

    // 이동 경로 중 막히는지, 1칸 이동으로 고정
    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        final Position to = calculateNextPosition(from);
        return hasPieceAt(to, boardMediator);
    }

    // 1칸 움직였을 때, 갈 수 있는 위치 반환
    public Position calculateDestination(final Position from, final TeamType teamType,
                                         final BoardMediator boardMediator) {
        final Position to = calculateNextPosition(from);
        if (!hasPieceAt(to, boardMediator)) {
            return to;
        }
        if (boardMediator.isSameTeamType(to, teamType)) {
            return from;
        }
        return to;
    }

    public Position calculateNextPosition(final Position from) {
        return from.calculateNext(direction);
    }

    public Position findFirstOccupiedPositionOrMax(final Position from, final BoardMediator boardMediator) {
        for (int distance = 1; distance <= MAX_DISTANCE; distance++) {
            final Position to = calculateNextPosition(from, distance);
            if (hasPieceAt(to, boardMediator)) {
                return to;
            }
        }
        return calculateNextPosition(from, MAX_DISTANCE);
    }

    public Position findFirstOccupiedPalacePositionOrMax(final Position from, final BoardMediator boardMediator) {
        Position tempTo = from;
        for (int distance = 1; distance <= MAX_DISTANCE; distance++) {
            if (boardMediator.isPalace(tempTo) && !Palace.isAllowedDirection(tempTo, direction)) {
                return tempTo;
            }
            final Position to = calculateNextPosition(from, distance);
            if (hasPieceAt(to, boardMediator)) {
                return to;
            }
            tempTo = to;
        }
        return calculateNextPosition(from, MAX_DISTANCE);
    }

    // 이동 가능한 경로의 자취 위치 리스트를 반환한다.
    // 경로에 장애물을 만나면 그때까지의 리스트를 반환하고, 적을 만난다면 적의 좌표를 포함하여 반환한다.
    public List<Position> calculateTracesOne(final Position from, final TeamType teamType,
                                             final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        if (!from.checkNextBound(direction)) {
            return traces;
        }
        Position to = calculateNextPosition(from);
        if (processPosition(to, traces, teamType, boardMediator)) {
            return traces;
        }
        return traces;
    }

    public List<Position> calculateTraces(final Position from, final TeamType teamType,
                                          final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        for (int distance = 1; distance <= MAX_DISTANCE; distance++) {
            if (!from.checkNextBound(direction, distance)) {
                break;
            }
            Position to = calculateNextPosition(from, distance);
            if (processPosition(to, traces, teamType, boardMediator)) {
                return traces;
            }
        }
        return traces;
    }

    public List<Position> calculateTracesForPalace(final Position from, final TeamType teamType,
                                                   final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        Position tempTo = from;
        for (int distance = 1; distance <= MAX_DISTANCE; distance++) {
            if (boardMediator.isPalace(tempTo) && !Palace.isAllowedDirection(tempTo, direction)) {
                return traces;
            }
            if (!from.checkNextBound(direction, distance)) {
                break;
            }
            Position to = calculateNextPosition(from, distance);
            tempTo = to;
            if (processPosition(to, traces, teamType, boardMediator)) {
                return traces;
            }
        }
        return traces;
    }

    private boolean processPosition(final Position to, final List<Position> traces, TeamType teamType,
                                    BoardMediator boardMediator) {
        if (!hasPieceAt(to, boardMediator)) {
            traces.add(to);
            return false;
        }
        if (!boardMediator.isSameTeamType(to, teamType)) {
            traces.add(to);
        }
        return true;
    }

    private Position calculateNextPosition(final Position from, final int distance) {
        return from.calculateNext(direction, distance);
    }

    private boolean hasPieceAt(final Position position, final BoardMediator boardMediator) {
        return boardMediator.hasPieceAt(position);
    }
}
