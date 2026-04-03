package domain.rule;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Team;

public class BikjangDetector {
    public boolean isBikjang(Board board) {
        Position han = board.findGeneral(Team.HAN);
        Position cho = board.findGeneral(Team.CHO);
        if (!han.column().equals(cho.column())) return false;
        return board.isColumnClearBetween(han, cho);
    }
}
