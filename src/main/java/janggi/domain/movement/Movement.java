package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Movement {

    private final int maxDistance;
    private final Direction direction;

    public Movement(int maxDistance, Direction direction) {
        this.maxDistance = maxDistance;
        this.direction = direction;
    }

    public boolean canMove(final Position from) {
        return from.checkNextBound(maxDistance, direction);
    }

    // 마지막 movement에서만 사용
    // 현재 기물이 다음으로 가는 곳에서 갈 수 있는 칸이 있는지
    public boolean hasReachablePosition(Position from, TeamType teamType, final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            from = calculateNextPosition(from, distance);
            if (!hasPieceAt(from, boardMediator)) {
                return true;
            }
            if (!boardMediator.isSameTeamType(from, teamType)) {
                return true;
            }
        }
        return false;
    }

    // 이동 경로 중 막히는지
    public boolean isBlocked(final Position from, final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            final Position to = calculateNextPosition(from, distance);
            if (hasPieceAt(to, boardMediator)) {
                return true;
            }
        }
        return false;
    }

    // 처음 만나는 기물 찾기(그 기물이 적군이라면 기물의 위치 반환, 아군이라면 이전 위치 반환)
    public Position calculateDestination(final Position from, final TeamType teamType,
                                         final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            final Position to = calculateNextPosition(from, distance);
            if (!hasPieceAt(to, boardMediator)) {
                continue;
            }
            if (boardMediator.isSameTeamType(to, teamType)) {
                return calculateNextPosition(from, distance - 1);
            }
            return to;
        }
        return calculateNextPosition(from, maxDistance);
    }

    // 처음 만나는 기물 위치 반환, 만약 끝까지 가도 없으면 그 끝 위치 반환
    public Position findFirstOccupiedPositionOrMax(final Position from,
                                                   final BoardMediator boardMediator) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            final Position to = calculateNextPosition(from, distance);
            if (hasPieceAt(to, boardMediator)) {
                return to;
            }
        }
        return calculateNextPosition(from, maxDistance);
    }

    // 이동 가능한 경로의 자취 위치 리스트를 반환한다.
    // 경로에 장애물을 만나면 그때까지의 리스트를 반환하고, 적을 만난다면 적의 좌표를 포함하여 반환한다.
    public List<Position> calculateTraces(final Position from, final TeamType teamType,
                                          final BoardMediator boardMediator) {
        final List<Position> traces = new ArrayList<>();
        Position prev = from;
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = calculateNextPosition(from, distance);
            if (prev.equals(to)) {
                return traces;
            }
            if (!hasPieceAt(to, boardMediator)) {
                traces.add(to);
                prev = to;
                continue;
            }
            if (!boardMediator.isSameTeamType(to, teamType)) {
                traces.add(to);
            }
            return traces;
        }
        return traces;
    }

    private Position calculateNextPosition(final Position from, final int distance) {
        return from.calculateNext(distance, direction);
    }

    private boolean hasPieceAt(final Position position, final BoardMediator boardMediator) {
        return boardMediator.hasPieceAt(position);
    }

    private Piece findPieceAt(final Position position, final BoardMediator boardMediator) {
        return boardMediator.getPieceInPosition(position);
    }
}
