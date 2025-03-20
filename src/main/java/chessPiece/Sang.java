package chessPiece;

import java.util.ArrayList;
import java.util.List;

public class Sang extends ChessPiece {

    public Sang(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == 3 && Math.abs(dx) == 2) {
            return true;
        }

        if (dx == -3 && Math.abs(dy) == 2) {
            return true;
        }

        if (dy == -3 && Math.abs(dx) == 2) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 상이 움직일 수 없는 위치입니다.");
    }

    @Override
    public List<BoardPosition> makeRoute(final BoardPosition boardPosition) {
        List<BoardPosition> route = new ArrayList<>();

        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();
        int presentRow = getBoardPosition().getRow();
        int presentCol = getBoardPosition().getCol();

        if (dx == 3 && dy == 2) {
            route.add(new BoardPosition(presentRow - 1, presentCol));
            route.add(new BoardPosition(presentRow - 2, presentCol - 1));
            route.add(new BoardPosition(presentRow - 3, presentCol - 2));
        }

        if (dx == 3 && dy == -2) {
            route.add(new BoardPosition(presentRow - 1, presentCol));
            route.add(new BoardPosition(presentRow - 2, presentCol + 1));
            route.add(new BoardPosition(presentRow - 3, presentCol + 2));
        }

        if (dx == 2 && dy == -3) {
            route.add(new BoardPosition(presentRow, presentCol + 1));
            route.add(new BoardPosition(presentRow - 1, presentCol + 2));
            route.add(new BoardPosition(presentRow - 2, presentCol + 3));
        }

        if (dx == -2 && dy == -3) {
            route.add(new BoardPosition(presentRow, presentCol + 1));
            route.add(new BoardPosition(presentRow + 1, presentCol + 2));
            route.add(new BoardPosition(presentRow + 2, presentCol + 3));
        }

        if (dx == -3 && dy == 2) {
            route.add(new BoardPosition(presentRow + 1, presentCol));
            route.add(new BoardPosition(presentRow + 2, presentCol - 1));
            route.add(new BoardPosition(presentRow + 3, presentCol - 2));
        }

        if (dx == -3 && dy == -2) {
            route.add(new BoardPosition(presentRow + 1, presentCol));
            route.add(new BoardPosition(presentRow + 2, presentCol + 1));
            route.add(new BoardPosition(presentRow + 3, presentCol + 2));
        }

        if (dx == 2 && dy == 3) {
            route.add(new BoardPosition(presentRow, presentCol - 1));
            route.add(new BoardPosition(presentRow - 1, presentCol - 2));
            route.add(new BoardPosition(presentRow - 2, presentCol - 3));
        }

        if (dx == -2 && dy == 3) {
            route.add(new BoardPosition(presentRow, presentCol - 1));
            route.add(new BoardPosition(presentRow + 1, presentCol - 2));
            route.add(new BoardPosition(presentRow + 2, presentCol - 3));
        }

        return route;
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }
}
