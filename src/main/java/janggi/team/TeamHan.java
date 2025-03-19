package janggi.team;

import janggi.piece.*;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class TeamHan {
    private List<Piece> pieces;

    public TeamHan() {
        pieces = new ArrayList<>(List.of(
                new King(new Position(4, 8)),
                new Horse(new Position(2, 9)), new Horse(new Position(7, 9)),
                new Elephant(new Position(1, 9)), new Elephant(new Position(6, 9)),
                new Cannon(new Position(1, 7)), new Cannon(new Position(7, 2)),
                new Chariot(new Position(0, 9)), new Chariot(new Position(8, 9)),
                new SoldierCho(new Position(0, 6)),
                new SoldierCho(new Position(2, 6)),
                new SoldierCho(new Position(4, 6)),
                new SoldierCho(new Position(6, 6)),
                new SoldierCho(new Position(8, 6)),
                new Guard(new Position(3, 9)), new Guard(new Position(5, 9))
        ));

/*
        public List<Position> isLegalMove(Piece selectedPiece, List<Piece> teamChoPieces) {
            List<Position> possiblePositions = selectedPiece.checkPossibleMoves();

            return null;
        }*/
    }

}
