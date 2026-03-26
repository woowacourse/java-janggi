package janggi.domain;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board() {
        this.board = initialize();
    }

    public Map<Position, Piece> initialize() {
        Map<Position, Piece> initBoard = new HashMap<>();

        initBoard.put(new Position(1, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(3, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(5, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(7, 7), new Piece(Team.CHO, PieceType.ZOL));
        initBoard.put(new Position(9, 7), new Piece(Team.CHO, PieceType.ZOL));

        initBoard.put(new Position(2, 8), new Piece(Team.CHO, PieceType.PO));
        initBoard.put(new Position(8, 8), new Piece(Team.CHO, PieceType.PO));

        initBoard.put(new Position(1, 10), new Piece(Team.CHO, PieceType.CHA));
        initBoard.put(new Position(9, 10), new Piece(Team.CHO, PieceType.CHA));

        initBoard.put(new Position(2, 10), new Piece(Team.CHO, PieceType.MA));
        initBoard.put(new Position(3, 10), new Piece(Team.CHO, PieceType.SANG));
        initBoard.put(new Position(7, 10), new Piece(Team.CHO, PieceType.MA));
        initBoard.put(new Position(8, 10), new Piece(Team.CHO, PieceType.SANG));

        initBoard.put(new Position(4, 10), new Piece(Team.CHO, PieceType.SA));
        initBoard.put(new Position(6, 10), new Piece(Team.CHO, PieceType.SA));
        initBoard.put(new Position(5, 9), new Piece(Team.CHO, PieceType.KING));

        initBoard.put(new Position(1, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(3, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(5, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(7, 4), new Piece(Team.HAN, PieceType.ZOL));
        initBoard.put(new Position(9, 4), new Piece(Team.HAN, PieceType.ZOL));

        initBoard.put(new Position(2, 3), new Piece(Team.HAN, PieceType.PO));
        initBoard.put(new Position(8, 3), new Piece(Team.HAN, PieceType.PO));

        initBoard.put(new Position(1, 1), new Piece(Team.HAN, PieceType.CHA));
        initBoard.put(new Position(9, 1), new Piece(Team.HAN, PieceType.CHA));

        initBoard.put(new Position(2, 1), new Piece(Team.HAN, PieceType.MA));
        initBoard.put(new Position(3, 1), new Piece(Team.HAN, PieceType.SANG));
        initBoard.put(new Position(7, 1), new Piece(Team.HAN, PieceType.MA));
        initBoard.put(new Position(8, 1), new Piece(Team.HAN, PieceType.SANG));

        initBoard.put(new Position(4, 1), new Piece(Team.HAN, PieceType.SA));
        initBoard.put(new Position(6, 1), new Piece(Team.HAN, PieceType.SA));
        initBoard.put(new Position(5, 2), new Piece(Team.HAN, PieceType.KING));

        return initBoard;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
