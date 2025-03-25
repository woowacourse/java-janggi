package domain.boardgenerator;

import domain.Position;
import domain.Team;
import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardGenerator implements BoardGenerator {
    @Override
    public Map<Position, Piece> generateBoard() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Cha(Team.HAN));
        board.put(new Position(1, 2), new Ma(Team.HAN));
        board.put(new Position(1, 3), new Sang(Team.HAN));
        board.put(new Position(1, 4), new Sa(Team.HAN));
        board.put(new Position(1, 6), new Sa(Team.HAN));
        board.put(new Position(1, 7), new Sang(Team.HAN));
        board.put(new Position(1, 8), new Ma(Team.HAN));
        board.put(new Position(1, 9), new Cha(Team.HAN));
        board.put(new Position(2, 5), new Gung(Team.HAN));
        board.put(new Position(3, 2), new Po(Team.HAN));
        board.put(new Position(3, 8), new Po(Team.HAN));
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
        board.put(new Position(8, 2), new Po(Team.CHO));
        board.put(new Position(8, 8), new Po(Team.CHO));
        board.put(new Position(9, 5), new Gung(Team.CHO));
        board.put(new Position(10, 1), new Cha(Team.CHO));
        board.put(new Position(10, 2), new Sang(Team.CHO));
        board.put(new Position(10, 3), new Ma(Team.CHO));
        board.put(new Position(10, 4), new Sa(Team.CHO));
        board.put(new Position(10, 6), new Sa(Team.CHO));
        board.put(new Position(10, 7), new Ma(Team.CHO));
        board.put(new Position(10, 8), new Sang(Team.CHO));
        board.put(new Position(10, 9), new Cha(Team.CHO));
        return board;
    }
}
