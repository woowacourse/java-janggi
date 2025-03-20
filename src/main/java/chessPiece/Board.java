package chessPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Map<BoardPosition, ChessPiece> janggiPan = new HashMap<>();

    public Board(List<ChessPiece> han, List<ChessPiece> cho) {
        janggiPan.putAll(
                Stream.concat(han.stream(), cho.stream())
                        .collect(Collectors.toMap(ChessPiece::getBoardPosition, piece -> piece))
        );
    }

    public Map<BoardPosition, ChessPiece> getJanggiPan() {
        return janggiPan;
    }

    public void updateBoard(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        ChessPiece chessPiece = janggiPan.get(presentPosition);
        chessPiece.isMove(futurePosition);

        checkObstacle(presentPosition, futurePosition);

        if (isPo(presentPosition)) {
            if (!isPieceInFront(presentPosition, futurePosition)) {
                throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
            }

            janggiPan.remove(presentPosition);
            janggiPan.put(futurePosition, chessPiece);
            chessPiece.updateChessPiecePositionBy(futurePosition);
            return;
        }

        janggiPan.remove(presentPosition);
        janggiPan.put(futurePosition, chessPiece);
        chessPiece.updateChessPiecePositionBy(futurePosition);
    }

    private boolean isPo(BoardPosition boardPosition) {
        return janggiPan.containsKey(boardPosition) && janggiPan.get(boardPosition).getName().equals("포");
    }

    private boolean isPieceInFront(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        int dx = presentPosition.getRow() - futurePosition.getRow();
        int dy = presentPosition.getCol() - futurePosition.getCol();

        int cnt = 0;

        if (dx == 0 && dy > 0) {
            for (int i = 1; i <= dy; i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow(), futurePosition.getCol() + i);

                if (isPo(boardPosition)) {
                    return false;
                }

                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }

        if (dx == 0 && dy < 0) {
            for (int i = 1; i <= Math.abs(dy); i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow(), presentPosition.getCol() + i);
                if (isPo(boardPosition)) {
                    return false;
                }
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }

        if (dx > 0 && dy == 0) {
            for (int i = 1; i <= dx; i++) {
                BoardPosition boardPosition =
                        new BoardPosition(futurePosition.getRow() + i, futurePosition.getCol());
                if (isPo(boardPosition)) {
                    return false;
                }
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        if (dx < 0 && dy == 0) {
            for (int i = 1; i <= Math.abs(dx); i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow() + i, futurePosition.getCol());
                if (isPo(boardPosition)) {
                    return false;
                }
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        return false;
    }

    public void checkObstacle(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        List<BoardPosition> route = janggiPan.get(presentPosition).makeRoute(futurePosition);

        int cnt = 0;
        if (isPo(presentPosition)) {
            for (BoardPosition boardPosition : route) {
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            if (cnt >= 2) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
            return;
        }

        for (BoardPosition boardPosition : route) {
            if (janggiPan.containsKey(boardPosition)) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
        }
    }
}
