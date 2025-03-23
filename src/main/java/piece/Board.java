package piece;

import static pieceProperty.PieceType.PO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pieceProperty.Position;

public class Board {

    private final Map<Position, Piece> janggiPan = new HashMap<>();

    public Board(List<Piece> han, List<Piece> cho) {
        janggiPan.putAll(
                Stream.concat(han.stream(), cho.stream())
                        .collect(Collectors.toMap(Piece::getBoardPosition, piece -> piece))
        );
    }

    public Map<Position, Piece> getJanggiPan() {
        return janggiPan;
    }

    public void updateBoard(final Position presentPosition, final Position futurePosition) {
        Piece piece = janggiPan.get(presentPosition);
        piece.canMoveTo(futurePosition);

        checkObstacle(presentPosition, futurePosition);

        if (isPo(presentPosition)) {
            if (!isPieceInFront(presentPosition, futurePosition)) {
                throw new IllegalArgumentException("[ERROR] 포가 움직일 수 없는 위치입니다.");
            }

            janggiPan.remove(presentPosition);
            janggiPan.put(futurePosition, piece);
            piece.updateChessPiecePositionBy(futurePosition);
            return;
        }

        janggiPan.remove(presentPosition);
        janggiPan.put(futurePosition, piece);
        piece.updateChessPiecePositionBy(futurePosition);
    }

    private boolean isPo(Position position) {
        return janggiPan.containsKey(position) && janggiPan.get(position).getPieceType().equals(PO);
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

                if (janggiPan.containsKey(position)) {
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
                if (janggiPan.containsKey(position)) {
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
                if (janggiPan.containsKey(position)) {
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
                if (janggiPan.containsKey(position)) {
                    cnt++;
                }
            }
            return cnt <= 1;
        }
        return false;
    }

    public void checkObstacle(final Position presentPosition, final Position futurePosition) {
        List<Position> route = janggiPan.get(presentPosition).makeRoute(futurePosition).getPositions();

        int cnt = 0;
        if (isPo(presentPosition)) {
            for (Position position : route) {
                if (janggiPan.containsKey(position)) {
                    cnt++;
                }
            }
            if (cnt >= 2) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
            return;
        }

        for (Position position : route) {
            if (janggiPan.containsKey(position)) {
                throw new IllegalArgumentException("[ERROR] 이동하려는 경로에 장애물이 존재합니다.");
            }
        }
    }
}
