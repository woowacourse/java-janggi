package domain;

import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Map;

public class Board {
    private Piece[][] board = new Piece[10][9];
    private Side turn;

    public Board(BoardInitializer boardInitializer) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Map<Position, Piece> initializedPosition = boardInitializer.initialize();
                board[i][j] = initializedPosition.getOrDefault(new Position(i, j), new EmptyPiece(Side.CHU));
            }
        }
        turn = boardInitializer.getFirstTurn();
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return board[position.col()][position.row()].equals(piece);
    }

    public Piece[][] getBoard() {
        return board;
    }
}
