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


    public void updateBoard(final Position presentPosition, final Position futurePosition) {
        Piece piece = janggiBoard.get(presentPosition);
        piece.isMove(futurePosition);

        checkObstacle(presentPosition, futurePosition);

        if (isPo(presentPosition)) {
            if (!isPieceInFront(presentPosition, futurePosition)) {
                throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
            }

            janggiBoard.remove(presentPosition);
            janggiBoard.put(futurePosition, piece);
            piece.updateChessPiecePositionBy(futurePosition);
            return;
        }

        janggiBoard.remove(presentPosition);
        janggiBoard.put(futurePosition, piece);
        piece.updateChessPiecePositionBy(futurePosition);
    }

    private boolean isPo(Position position) {
        return janggiBoard.containsKey(position) && janggiBoard.get(position).getName().equals("포");
    }

    private boolean isPieceInFront(final Position presentPosition, final Position futurePosition) {
        int dx = presentPosition.getRow() - futurePosition.getRow();
        int dy = presentPosition.getCol() - futurePosition.getCol();

        int cnt = 0;

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                Position position =
                        new Position(presentPosition.getRow(), futurePosition.getCol() + i);

                if (isPo(position)) {
                    return false;
                }

                if (janggiBoard.containsKey(position)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                Position position =
                        new Position(presentPosition.getRow(), presentPosition.getCol() + i);
                if (isPo(position)) {
                    return false;
                }
                if (janggiBoard.containsKey(position)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                Position position =
                        new Position(futurePosition.getRow() + i, futurePosition.getCol());
                if (isPo(position)) {
                    return false;
                }
                if (janggiBoard.containsKey(position)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                Position position =
                        new Position(presentPosition.getRow() + i, futurePosition.getCol());
                if (isPo(position)) {
                    return false;
                }
                if (janggiBoard.containsKey(position)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        return false;
    }

    public void checkObstacle(final Position presentPosition, final Position futurePosition) {
        List<Position> route = janggiBoard.get(presentPosition).makeRoute(futurePosition);

        int cnt = 0;
        if (isPo(presentPosition)) {
            for (Position position : route) {
                if (janggiBoard.containsKey(position)) {
                    cnt++;
                }
            }
            if (cnt >= 2) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
            return;
        }

        for (Position position : route) {
            if (janggiBoard.containsKey(position)) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
        }
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

}
