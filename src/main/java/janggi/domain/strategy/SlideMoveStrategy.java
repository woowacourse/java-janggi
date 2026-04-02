package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SlideMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDirection : baseDirections) {
            addSlidePath(current, baseDirection, paths);
        }
        return paths;
    }

    private void addSlidePath(Position current, Direction baseDirection, Paths paths) {
        Path path = new Path();

        // 첫 이동 시도
        Optional<Position> nextCandidate = current.tryMove(baseDirection);

        // 이동에 성공한 경우, 계속 반복
        while (nextCandidate.isPresent()) {
            Position currentSlide = nextCandidate.get();
            path.add(currentSlide);

            // 현재 위치에서 같은 방향으로 또 이동 시도
            nextCandidate = currentSlide.tryMove(baseDirection);
        }

        if (!path.isEmpty()) {
            paths.addPath(path);
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateSlidePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateSlidePath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        for (Position destination : route) {
            if (isBlocked(destination, state, destinations, me)) {
                break;
            }
        }
    }

    private boolean isBlocked(Position destination, Map<Position, Piece> state, List<Position> destinations,
                              Piece me) {
        Piece target = state.get(destination);

        // 빈 칸이면 경로에 추가하고, 계속 전진
        if (target == null) {
            destinations.add(destination);
            return false;
        }

        // 적군이면 경로에 추가하고, 멈춤
        if (!target.isSameSide(me)) {
            destinations.add(destination);
        }

        // 아군이면 경로에 추가하지 않고 멈춤
        // 기물을 만났으므로 무조건 막힌 것(true)으로 반환
        return true;
    }
}
