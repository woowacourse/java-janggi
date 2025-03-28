package game;

import java.util.HashMap;
import java.util.Map;
import piece.Country;
import piece.Piece;
import position.Position;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public Board(StartPosition choStartPosition, StartPosition hanStartPosition) {
        BoardSetting boardSetting = new BoardSetting();
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(boardSetting.setting(Country.Cho, choStartPosition));
        board.putAll(boardSetting.setting(Country.Han, hanStartPosition));
        this.board = board;
    }

    public void movePiece(Position fromPosition, Position toPosition) {
        Piece piece = board.get(fromPosition);
        piece.canMove(fromPosition, toPosition, this);

    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
