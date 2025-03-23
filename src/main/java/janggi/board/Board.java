package janggi.board;

import janggi.piece.Piece;
import janggi.position.BoardPosition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final Map<BoardPosition, Piece> janggiBoard = new HashMap<>();

    public Board(List<Piece> han, List<Piece> cho) {
        janggiBoard.putAll(
                Stream.concat(han.stream(), cho.stream())
                        .collect(Collectors.toMap(Piece::getBoardPosition, piece -> piece))
        );
    }

    public Map<BoardPosition, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public void updateBoard(final BoardPosition presentPosition, final BoardPosition futurePosition) {
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

    private boolean isPo(BoardPosition boardPosition) {
        return janggiBoard.containsKey(boardPosition) && janggiBoard.get(boardPosition).getName().equals("포");
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

                if (janggiBoard.containsKey(boardPosition)) {
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
                if (janggiBoard.containsKey(boardPosition)) {
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
                if (janggiBoard.containsKey(boardPosition)) {
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
                if (janggiBoard.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        return false;
    }

    public void checkObstacle(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        List<BoardPosition> route = janggiBoard.get(presentPosition).makeRoute(futurePosition);

        int cnt = 0;
        if (isPo(presentPosition)) {
            for (BoardPosition boardPosition : route) {
                if (janggiBoard.containsKey(boardPosition)) {
                    cnt++;
                }
            }
            if (cnt >= 2) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
            return;
        }

        for (BoardPosition boardPosition : route) {
            if (janggiBoard.containsKey(boardPosition)) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
        }
    }
}
