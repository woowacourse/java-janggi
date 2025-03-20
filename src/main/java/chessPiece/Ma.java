package chessPiece;

import java.util.ArrayList;
import java.util.List;

public class Ma extends ChessPiece {

    public Ma(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        super(pieceProfile, boardPosition);
    }

    @Override
    public boolean isMove(final BoardPosition boardPosition) {
        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();

        if (dx == 2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == 2 && Math.abs(dx) == 1) {
            return true;
        }

        if (dx == -2 && Math.abs(dy) == 1) {
            return true;
        }

        if (dy == -2 && Math.abs(dx) == 1) {
            return true;
        }

        throw new IllegalArgumentException("[ERROR] 마가 움직일 수 없는 위치입니다.");
    }

    @Override
    public List<BoardPosition> makeRoute(final BoardPosition boardPosition) {
        List<BoardPosition> route = new ArrayList<>();

        int dx = getBoardPosition().getRow() - boardPosition.getRow();
        int dy = getBoardPosition().getCol() - boardPosition.getCol();
        int presentCol = getBoardPosition().getCol();
        int presentRow = getBoardPosition().getRow();

        if (dx == 2 && dy == 1) {
            route.add(new BoardPosition(presentRow - 1, presentCol));
            route.add(new BoardPosition(presentRow - 2, presentCol - 1));
        }

        if (dx == 2 && dy == -1) {
            route.add(new BoardPosition(presentRow - 1, presentCol));
            route.add(new BoardPosition(presentRow - 2, presentCol + 1));
        }

        if (dx == 1 && dy == -2) {
            route.add(new BoardPosition(presentRow, presentCol + 1));
            route.add(new BoardPosition(presentRow - 1, presentCol + 2));
        }

        if (dx == -1 && dy == -2) {
            route.add(new BoardPosition(presentRow, presentCol + 1));
            route.add(new BoardPosition(presentRow + 1, presentCol + 2));
        }

        if (dx == -2 && dy == -1) {
            route.add(new BoardPosition(presentRow + 1, presentCol));
            route.add(new BoardPosition(presentRow + 2, presentCol + 1));
        }

        if (dx == -2 && dy == 1) {
            route.add(new BoardPosition(presentRow + 1, presentCol));
            route.add(new BoardPosition(presentRow + 2, presentCol - 1));
        }

        if (dx == 1 && dy == 2) {
            route.add(new BoardPosition(presentRow, presentCol - 1));
            route.add(new BoardPosition(presentRow - 1, presentCol - 2));
        }

        if (dx == -1 && dy == 2) {
            route.add(new BoardPosition(presentRow, presentCol - 1));
            route.add(new BoardPosition(presentRow + 1, presentCol - 2));
        }

        return route;
    }

    public void updateChessPiecePositionBy(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
