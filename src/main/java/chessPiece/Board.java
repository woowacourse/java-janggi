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

    public boolean isPieceInFront(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        int dx = presentPosition.getCol() - futurePosition.getCol();
        int dy = presentPosition.getRow() - futurePosition.getRow();

        canMoveBy(presentPosition, futurePosition);

        //Po가 있는지 확인한다.
        int cnt = 0;

        if (dx == 0 && dy > 0) {
            for (int i = 0; i < dy; i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow(), futurePosition.getCol() + i);
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
                if (isPo(boardPosition)) {
                    return false;
                }
            }
            return cnt <= 1;
        }

        if (dx == 0 && dy < 0) {
            for (int i = 0; i < Math.abs(dy); i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow(), presentPosition.getCol() + i);
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
                if (isPo(boardPosition)) {
                    return false;
                }
            }
            return cnt <= 1;
        }

        if (dx > 0 && dy == 0) {
            for (int i = 0; i < dx; i++) {
                BoardPosition boardPosition =
                        new BoardPosition(futurePosition.getRow() + i, futurePosition.getCol());
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
                if (isPo(boardPosition)) {
                    return false;
                }
            }
            return cnt <= 1;
        }
        if (dx < 0 && dy == 0) {
            for (int i = 0; i < Math.abs(dx); i++) {
                BoardPosition boardPosition =
                        new BoardPosition(presentPosition.getRow() + i, futurePosition.getCol());
                if (janggiPan.containsKey(boardPosition)) {
                    cnt++;
                }
                if (isPo(boardPosition)) {
                    return false;
                }
            }
            return cnt <= 1;
        }

        return false;
    }

    public void canMoveBy(final BoardPosition presentPosition, final BoardPosition futurePosition) {
        ChessPiece chessPiece = janggiPan.get(presentPosition);
        chessPiece.move(futurePosition);
    }

    private boolean isPo(BoardPosition boardPosition) {
        return janggiPan.containsKey(boardPosition) && janggiPan.get(boardPosition).getName().equals("포");
    }
}
