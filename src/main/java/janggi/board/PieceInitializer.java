package janggi.board;

import janggi.piece.Piece;
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
import java.util.ArrayList;
import java.util.List;

public class PieceInitializer {

    public List<Piece> generate() {
        final List<Piece> pieces = new ArrayList<>();

        pieces.add(new Chariot(Team.HAN, new Position(0, 0)));
        pieces.add(new Chariot(Team.HAN, new Position(0, 8)));

        pieces.add(new Elephant(Team.HAN, new Position(0, 1)));
        pieces.add(new Elephant(Team.HAN, new Position(0, 7)));

        pieces.add(new Horse(Team.HAN, new Position(0, 2)));
        pieces.add(new Horse(Team.HAN, new Position(0, 6)));

        pieces.add(new Guard(Team.HAN, new Position(0, 3)));
        pieces.add(new Guard(Team.HAN, new Position(0, 5)));

        pieces.add(new King(Team.HAN, new Position(1, 4)));

        pieces.add(new Cannon(Team.HAN, new Position(2, 1)));
        pieces.add(new Cannon(Team.HAN, new Position(2, 7)));

        pieces.add(new Soldier(Team.HAN, new Position(3, 0)));
        pieces.add(new Soldier(Team.HAN, new Position(3, 2)));
        pieces.add(new Soldier(Team.HAN, new Position(3, 4)));
        pieces.add(new Soldier(Team.HAN, new Position(3, 6)));
        pieces.add(new Soldier(Team.HAN, new Position(3, 8)));

        pieces.add(new Chariot(Team.CHO, new Position(9, 0)));
        pieces.add(new Chariot(Team.CHO, new Position(9, 8)));
        pieces.add(new Elephant(Team.CHO, new Position(9, 1)));
        pieces.add(new Elephant(Team.CHO, new Position(9, 7)));

        pieces.add(new Horse(Team.CHO, new Position(9, 2)));
        pieces.add(new Horse(Team.CHO, new Position(9, 6)));

        pieces.add(new Guard(Team.CHO, new Position(9, 3)));
        pieces.add(new Guard(Team.CHO, new Position(9, 5)));

        pieces.add(new King(Team.CHO, new Position(8, 4)));

        pieces.add(new Cannon(Team.CHO, new Position(7, 1)));
        pieces.add(new Cannon(Team.CHO, new Position(7, 7)));

        pieces.add(new Pawn(Team.CHO, new Position(6, 0)));
        pieces.add(new Pawn(Team.CHO, new Position(6, 2)));
        pieces.add(new Pawn(Team.CHO, new Position(6, 4)));
        pieces.add(new Pawn(Team.CHO, new Position(6, 6)));
        pieces.add(new Pawn(Team.CHO, new Position(6, 8)));

        return pieces;
    }

}
