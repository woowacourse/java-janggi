package janggi.team;

import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.palace.PalaceHan;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Guard;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.ArrayList;
import java.util.List;

public class TeamHan extends Team {

    public TeamHan(BoardSetup boardSetup) {
        this.pieces = initBoard(boardSetup);
        this.teamScore = new TeamScore();
        this.palace = new PalaceHan();
    }

    @Override
    protected List<Piece> initBoard(BoardSetup boardSetup) {
        List<Piece> hanDefaultPosition = new ArrayList<>(List.of(
                new King(TeamName.HAN, new Position(4, 8)),
                new Cannon(TeamName.HAN, new Position(1, 7)),
                new Cannon(TeamName.HAN, new Position(7, 7)),
                new Chariot(TeamName.HAN, new Position(0, 9)),
                new Chariot(TeamName.HAN, new Position(8, 9)),
                new Soldier(TeamName.HAN, new Position(0, 6)),
                new Soldier(TeamName.HAN, new Position(2, 6)),
                new Soldier(TeamName.HAN, new Position(4, 6)),
                new Soldier(TeamName.HAN, new Position(6, 6)),
                new Soldier(TeamName.HAN, new Position(8, 6)),
                new Guard(TeamName.HAN, new Position(3, 9)),
                new Guard(TeamName.HAN, new Position(5, 9))));
        hanDefaultPosition.addAll(boardSetup.getPieces());
        return hanDefaultPosition;
    }
}
