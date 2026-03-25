package domain;

import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.Map;

public class Board {

    private Piece[][] board = new Piece[10][9];

    public Board(Map<Position, Piece> piecesInitPosition) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = piecesInitPosition.getOrDefault(new Position(i, j), new EmptyPiece(Side.CHU));
            }
        }
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return board[position.col()][position.row()] == piece;
    }

    public Piece[][] getBoard() {
        return board;
    }
}
