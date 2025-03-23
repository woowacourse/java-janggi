package domain.boardgenerator;

import domain.Position;
import domain.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardGenerator implements BoardGenerator {
    @Override
    public Map<Position, Piece> generateBoard() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Chariot(Team.HAN));
        board.put(new Position(1, 2), new Horse(Team.HAN));
        board.put(new Position(1, 3), new Elephant(Team.HAN));
        board.put(new Position(1, 4), new Guard(Team.HAN));
        board.put(new Position(1, 6), new Guard(Team.HAN));
        board.put(new Position(1, 7), new Elephant(Team.HAN));
        board.put(new Position(1, 8), new Horse(Team.HAN));
        board.put(new Position(1, 9), new Chariot(Team.HAN));
        board.put(new Position(2, 5), new King(Team.HAN));
        board.put(new Position(3, 2), new Cannon(Team.HAN));
        board.put(new Position(3, 8), new Cannon(Team.HAN));
        board.put(new Position(4, 1), new Pawn(Team.HAN));
        board.put(new Position(4, 3), new Pawn(Team.HAN));
        board.put(new Position(4, 5), new Pawn(Team.HAN));
        board.put(new Position(4, 7), new Pawn(Team.HAN));
        board.put(new Position(4, 9), new Pawn(Team.HAN));
        board.put(new Position(7, 1), new Pawn(Team.CHO));
        board.put(new Position(7, 3), new Pawn(Team.CHO));
        board.put(new Position(7, 5), new Pawn(Team.CHO));
        board.put(new Position(7, 7), new Pawn(Team.CHO));
        board.put(new Position(7, 9), new Pawn(Team.CHO));
        board.put(new Position(8, 2), new Cannon(Team.CHO));
        board.put(new Position(8, 8), new Cannon(Team.CHO));
        board.put(new Position(9, 5), new King(Team.CHO));
        board.put(new Position(10, 1), new Chariot(Team.CHO));
        board.put(new Position(10, 2), new Elephant(Team.CHO));
        board.put(new Position(10, 3), new Horse(Team.CHO));
        board.put(new Position(10, 4), new Guard(Team.CHO));
        board.put(new Position(10, 6), new Guard(Team.CHO));
        board.put(new Position(10, 7), new Horse(Team.CHO));
        board.put(new Position(10, 8), new Elephant(Team.CHO));
        board.put(new Position(10, 9), new Chariot(Team.CHO));
        return board;
    }
}
