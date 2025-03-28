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
import java.util.ArrayList;
import java.util.List;

public class JanggiBoardGenerator implements BoardGenerator {
    @Override
    public List<Piece> generateBoard() {
        List<Piece> board = new ArrayList<>();
        board.add(new Cha(Team.HAN, new Position(1, 1)));
        board.add(new Ma(Team.HAN, new Position(1, 2)));
        board.add(new Sang(Team.HAN, new Position(1, 3)));
        board.add(new Sa(Team.HAN, new Position(1, 4)));
        board.add(new Sa(Team.HAN, new Position(1, 6)));
        board.add(new Sang(Team.HAN, new Position(1, 7)));
        board.add(new Ma(Team.HAN, new Position(1, 8)));
        board.add(new Cha(Team.HAN, new Position(1, 9)));
        board.add(new Gung(Team.HAN, new Position(2, 5)));
        board.add(new Po(Team.HAN, new Position(3, 2)));
        board.add(new Po(Team.HAN, new Position(3, 8)));
        board.add(new Pawn(Team.HAN, new Position(4, 1)));
        board.add(new Pawn(Team.HAN, new Position(4, 3)));
        board.add(new Pawn(Team.HAN, new Position(4, 5)));
        board.add(new Pawn(Team.HAN, new Position(4, 7)));
        board.add(new Pawn(Team.HAN, new Position(4, 9)));
        board.add(new Pawn(Team.CHO, new Position(7, 1)));
        board.add(new Pawn(Team.CHO, new Position(7, 3)));
        board.add(new Pawn(Team.CHO, new Position(7, 5)));
        board.add(new Pawn(Team.CHO, new Position(7, 7)));
        board.add(new Pawn(Team.CHO, new Position(7, 9)));
        board.add(new Po(Team.CHO, new Position(8, 2)));
        board.add(new Po(Team.CHO, new Position(8, 8)));
        board.add(new Gung(Team.CHO, new Position(9, 5)));
        board.add(new Cha(Team.CHO, new Position(10, 1)));
        board.add(new Sang(Team.CHO, new Position(10, 2)));
        board.add(new Ma(Team.CHO, new Position(10, 3)));
        board.add(new Sa(Team.CHO, new Position(10, 4)));
        board.add(new Sa(Team.CHO, new Position(10, 6)));
        board.add(new Ma(Team.CHO, new Position(10, 7)));
        board.add(new Sang(Team.CHO, new Position(10, 8)));
        board.add(new Cha(Team.CHO, new Position(10, 9)));
        return board;
    }
}
