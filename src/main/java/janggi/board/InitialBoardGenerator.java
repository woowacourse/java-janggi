package janggi.board;

import static janggi.board.Board.COLUMN;
import static janggi.board.Board.ROW;

import janggi.piece.Camp;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.Empty;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitialBoardGenerator implements BoardGenerator {

    @Override
    public Board generate(Camp baseCamp) {
        Board board = new Board(initializeCells(), baseCamp);
        placeChoPieces(board);
        placeHanPieces(board);
        return board;
    }

    private Map<Position, Piece> initializeCells() {
        Map<Position, Piece> cells = new HashMap<>();
        for (int i = 0; i < COLUMN; i++) {
            for (int j = 0; j < ROW; j++) {
                cells.put(new Position(i, j), Empty.INSTANCE);
            }
        }
        return cells;
    }

    private void placeChoPieces(Board board) {
        board.placePiece(new Position(0, 0), new Chariot(Camp.CHO, board));
        board.placePiece(new Position(1, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(2, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(3, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(4, 1), new General(Camp.CHO, board));
        board.placePiece(new Position(5, 0), new Guard(Camp.CHO, board));
        board.placePiece(new Position(6, 0), new Elephant(Camp.CHO, board));
        board.placePiece(new Position(7, 0), new Horse(Camp.CHO, board));
        board.placePiece(new Position(8, 0), new Chariot(Camp.CHO, board));

        for (int x : List.of(1, 7)) {
            board.placePiece(new Position(x, 2), new Cannon(Camp.CHO, board));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(new Position(x, 2), new Soldier(Camp.CHO, board));
        }
    }

    private void placeHanPieces(Board board) {
        board.placePiece(new Position(0, 9), new Chariot(Camp.HAN, board));
        board.placePiece(new Position(1, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(2, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(3, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(4, 8), new General(Camp.HAN, board));
        board.placePiece(new Position(5, 9), new Guard(Camp.HAN, board));
        board.placePiece(new Position(6, 9), new Elephant(Camp.HAN, board));
        board.placePiece(new Position(7, 9), new Horse(Camp.HAN, board));
        board.placePiece(new Position(8, 9), new Chariot(Camp.HAN, board));

        for (int x : List.of(1, 7)) {
            board.placePiece(new Position(x, 7), new Cannon(Camp.HAN, board));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(new Position(x, 6), new Soldier(Camp.HAN, board));
        }
    }
}
