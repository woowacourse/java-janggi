package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> janggiBoard = new HashMap<>();

    public Board(List<Piece> pieces) {
        janggiBoard.putAll(
                pieces.stream()
                        .collect((Collectors.toMap(Piece::getBoardPosition, piece -> piece))
                        ));
    }

    public void pieceMove(final Position presentPosition, final Position futurePosition) {
        Piece piece = janggiBoard.get(presentPosition);
        piece.isMove(futurePosition);
        checkObstacle(presentPosition, futurePosition);
        updatePiecePosition(presentPosition, futurePosition, piece);
    }

    private void updatePiecePosition(final Position presentPosition, final Position futurePosition, final Piece piece) {
        janggiBoard.remove(presentPosition);
        janggiBoard.put(futurePosition, piece);
        piece.updatePiecePositionBy(futurePosition);
    }

    private void checkObstacle(final Position presentPosition, final Position futurePosition) {
        List<Position> moveRoute = janggiBoard.get(presentPosition).makeRoute(futurePosition);

        if (isPo(presentPosition)) {
            validatePoMove(moveRoute);
            return;
        }

        for (Position position : moveRoute) {
            if (janggiBoard.containsKey(position)) {
                throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 이동하려는 경로에 장애물이 존재합니다.");
            }
        }
    }

    private void validatePoMove(final List<Position> moveRoute) {
        int obstacleCount = 0;
        for (Position position : moveRoute) {
            if (isPo(position)) {
                throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 이동하려는 경로에 포가 존재합니다. 포는 포를 넘을 수 없습니다.");
            }

            if (janggiBoard.containsKey(position)) {
                obstacleCount++;
            }
        }
        validateObstacleBy(obstacleCount);
    }

    private void validateObstacleBy(final int obstacle) {
        if (obstacle == 0) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 포는 반드시 포를 제외한 기물 하나를 넘어야 합니다.");
        }

        if (obstacle >= 2) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다. 이동하려는 경로에 " + obstacle + "개의 장애물이 존재합니다.");
        }
    }

    private boolean isPo(Position position) {
        return janggiBoard.containsKey(position) && janggiBoard.get(position).getName().equals("포");
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

}
