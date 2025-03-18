package domain;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Move;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.List;

public class JanggiBoard {

    private final Piece[][] board;

    public JanggiBoard() {
        this.board = new Piece[][]{
                {new Chariot(Team.RED), new Horse(Team.RED), new Elephant(Team.RED), new Guard(Team.RED), null, new Guard(Team.RED), new Elephant(Team.RED), new Horse(Team.RED), new Chariot(Team.RED)},
                {null,null,null,null,new King(Team.RED),null,null,null,null},
                {null,new Cannon(Team.RED),null,null,null,null,null,new Cannon(Team.RED),null},
                {new Pawn(Team.RED),null,new Pawn(Team.RED),null,new Pawn(Team.RED),null,new Pawn(Team.RED),null,new Pawn(Team.RED),null,new Pawn(Team.RED)},
                {null,null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null,null},
                {new Pawn(Team.BLUE),null,new Pawn(Team.BLUE),null,new Pawn(Team.BLUE),null,new Pawn(Team.BLUE),null,new Pawn(Team.BLUE),null,new Pawn(Team.BLUE)},
                {null,new Cannon(Team.BLUE),null,null,null,null,null,new Cannon(Team.BLUE),null},
                {null,null,null,null,new King(Team.BLUE),null,null,null,null},
                {new Chariot(Team.BLUE), new Horse(Team.BLUE), new Elephant(Team.BLUE), new Guard(Team.BLUE), null, new Guard(Team.BLUE), new Elephant(Team.BLUE), new Horse(Team.BLUE), new Chariot(Team.BLUE)},
        };
    }


    public Piece[][] getBoard() {
        return board;
    }

    public void move(int startRow, int startColumn, int targetRow, int targetColumn) {
        Piece piece = findPiece(startRow, startColumn);

        List<Move> moves = piece.calculatePath(startRow, startColumn, targetRow, targetColumn);
        int row = startRow;
        int column = startColumn;
        for (Move move : moves) {
            canMove(startRow, startColumn, row + move.getDy(), column + move.getDx());
        }
    }

    public boolean canMove(int startRow, int startColumn, int targetRow, int targetColumn) {
        Piece targetPiece = findPiece(targetRow, targetColumn);
        Piece startpiece = findPiece(startRow, startColumn);
        if (targetPiece == null || !startpiece.getTeam().equals(targetPiece.getTeam())) {
            return true;
        }
        return false;
    }

    public Piece findPiece(int row, int column) {
        return board[row][column];
    }
}
