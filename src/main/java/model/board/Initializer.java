package model.board;

import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.Palace;
import model.piece.Pao;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Soldier;

class Initializer {

    public Board generate() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Palace(4, 1, Team.HAN));
        pieces.add(new Palace(4, 8, Team.CHO));

        pieces.add(new Soldier(3, 0, Team.HAN));
        pieces.add(new Soldier(5, 0, Team.HAN));
        pieces.add(new Soldier(3, 9, Team.CHO));
        pieces.add(new Soldier(5, 9, Team.CHO));

        pieces.add(new Elephant(2, 0, Team.HAN));
        pieces.add(new Elephant(6, 0, Team.HAN));
        pieces.add(new Elephant(2, 9, Team.CHO));
        pieces.add(new Elephant(6, 9, Team.CHO));

        pieces.add(new Horse(1, 0, Team.HAN));
        pieces.add(new Horse(7, 0, Team.HAN));
        pieces.add(new Horse(1, 9, Team.CHO));
        pieces.add(new Horse(7, 9, Team.CHO));

        pieces.add(new Chariot(0, 0, Team.HAN));
        pieces.add(new Chariot(8, 0, Team.HAN));
        pieces.add(new Chariot(0, 9, Team.CHO));
        pieces.add(new Chariot(8, 9, Team.CHO));

        pieces.add(new Pao(1, 2, Team.HAN));
        pieces.add(new Pao(7, 2, Team.HAN));
        pieces.add(new Pao(1, 7, Team.CHO));
        pieces.add(new Pao(7, 7, Team.CHO));

        pieces.add(new Pawn(0, 3, Team.HAN));
        pieces.add(new Pawn(2, 3, Team.HAN));
        pieces.add(new Pawn(4, 3, Team.HAN));
        pieces.add(new Pawn(6, 3, Team.HAN));
        pieces.add(new Pawn(8, 3, Team.HAN));
        pieces.add(new Pawn(0, 6, Team.CHO));
        pieces.add(new Pawn(2, 6, Team.CHO));
        pieces.add(new Pawn(4, 6, Team.CHO));
        pieces.add(new Pawn(6, 6, Team.CHO));
        pieces.add(new Pawn(8, 6, Team.CHO));

        return new Board(pieces);
    }
}
