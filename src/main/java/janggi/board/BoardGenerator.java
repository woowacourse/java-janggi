package janggi.board;

import janggi.piece.Team;
import janggi.piece.multiplemovepiece.Cannon;
import janggi.piece.multiplemovepiece.Chariot;
import janggi.piece.multiplemovepiece.Elephant;
import janggi.piece.multiplemovepiece.Horse;
import janggi.piece.onemovepiece.Guard;
import janggi.piece.onemovepiece.King;
import janggi.piece.onemovepiece.Pawn;
import janggi.piece.onemovepiece.Soldier;
import janggi.position.Position;

public class BoardGenerator {

    public Board generate() {
        final Board board = new Board();

        board.deployPiece(new Position(0, 8), new Chariot(Team.HAN));
        board.deployPiece(new Position(0, 0), new Chariot(Team.HAN));

        board.deployPiece(new Position(0, 1), new Elephant(Team.HAN));
        board.deployPiece(new Position(0, 7), new Elephant(Team.HAN));

        board.deployPiece(new Position(0, 2), new Horse(Team.HAN));
        board.deployPiece(new Position(0, 6), new Horse(Team.HAN));

        board.deployPiece(new Position(0, 3), new Guard(Team.HAN));
        board.deployPiece(new Position(0, 5), new Guard(Team.HAN));

        board.deployPiece(new Position(1, 4), new King(Team.HAN));

        board.deployPiece(new Position(2, 1), new Cannon(Team.HAN));
        board.deployPiece(new Position(2, 7), new Cannon(Team.HAN));

        board.deployPiece(new Position(3, 0), new Soldier(Team.HAN));
        board.deployPiece(new Position(3, 2), new Soldier(Team.HAN));
        board.deployPiece(new Position(3, 4), new Soldier(Team.HAN));
        board.deployPiece(new Position(3, 6), new Soldier(Team.HAN));
        board.deployPiece(new Position(3, 8), new Soldier(Team.HAN));

        board.deployPiece(new Position(9, 0), new Chariot(Team.CHU));
        board.deployPiece(new Position(9, 8), new Chariot(Team.CHU));

        board.deployPiece(new Position(9, 1), new Elephant(Team.CHU));
        board.deployPiece(new Position(9, 7), new Elephant(Team.CHU));

        board.deployPiece(new Position(9, 2), new Horse(Team.CHU));
        board.deployPiece(new Position(9, 6), new Horse(Team.CHU));

        board.deployPiece(new Position(9, 3), new Guard(Team.CHU));
        board.deployPiece(new Position(9, 5), new Guard(Team.CHU));

        board.deployPiece(new Position(8, 4), new King(Team.CHU));

        board.deployPiece(new Position(7, 1), new Cannon(Team.CHU));
        board.deployPiece(new Position(7, 7), new Cannon(Team.CHU));

        board.deployPiece(new Position(6, 0), new Pawn(Team.CHU));
        board.deployPiece(new Position(6, 2), new Pawn(Team.CHU));
        board.deployPiece(new Position(6, 4), new Pawn(Team.CHU));
        board.deployPiece(new Position(6, 6), new Pawn(Team.CHU));
        board.deployPiece(new Position(6, 8), new Pawn(Team.CHU));

        return board;
    }
}
