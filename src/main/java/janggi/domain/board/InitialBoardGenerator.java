package janggi.domain.board;

import static janggi.domain.board.Board.MAX_COLUMN;
import static janggi.domain.board.Board.MAX_ROW;
import static janggi.domain.board.Board.MIN_COLUMN;
import static janggi.domain.board.Board.MIN_ROW;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Empty;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
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
        for (int i = MIN_COLUMN; i < MAX_COLUMN; i++) {
            for (int j = MIN_ROW; j < MAX_ROW; j++) {
                cells.put(Position.of(i, j), Empty.INSTANCE);
            }
        }
        return cells;
    }

    private void placeChoPieces(Board board) {
        board.placePiece(Position.of(0, 0), new Chariot(Camp.CHO));
        board.placePiece(Position.of(1, 0), new Elephant(Camp.CHO));
        board.placePiece(Position.of(2, 0), new Horse(Camp.CHO));
        board.placePiece(Position.of(3, 0), new Guard(Camp.CHO));
        board.placePiece(Position.of(4, 1), new General(Camp.CHO));
        board.placePiece(Position.of(5, 0), new Guard(Camp.CHO));
        board.placePiece(Position.of(6, 0), new Elephant(Camp.CHO));
        board.placePiece(Position.of(7, 0), new Horse(Camp.CHO));
        board.placePiece(Position.of(8, 0), new Chariot(Camp.CHO));

        for (int x : List.of(1, 7)) {
            board.placePiece(Position.of(x, 2), new Cannon(Camp.CHO));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(Position.of(x, 3), new Soldier(Camp.CHO));
        }
    }

    private void placeHanPieces(Board board) {
        board.placePiece(Position.of(0, 9), new Chariot(Camp.HAN));
        board.placePiece(Position.of(1, 9), new Elephant(Camp.HAN));
        board.placePiece(Position.of(2, 9), new Horse(Camp.HAN));
        board.placePiece(Position.of(3, 9), new Guard(Camp.HAN));
        board.placePiece(Position.of(4, 8), new General(Camp.HAN));
        board.placePiece(Position.of(5, 9), new Guard(Camp.HAN));
        board.placePiece(Position.of(6, 9), new Elephant(Camp.HAN));
        board.placePiece(Position.of(7, 9), new Horse(Camp.HAN));
        board.placePiece(Position.of(8, 9), new Chariot(Camp.HAN));

        for (int x : List.of(1, 7)) {
            board.placePiece(Position.of(x, 7), new Cannon(Camp.HAN));
        }
        for (int x = 0; x <= 8; x += 2) {
            board.placePiece(Position.of(x, 6), new Soldier(Camp.HAN));
        }
    }
}
