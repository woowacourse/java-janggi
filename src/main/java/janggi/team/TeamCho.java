package janggi.team;

import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.palace.PalaceCho;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Guard;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.ArrayList;
import java.util.List;

public class TeamCho extends Team {

    public TeamCho(BoardSetup boardSetup) {
        this.pieces = initBoard(boardSetup);
        this.teamScore = new TeamScore();
        this.palace = new PalaceCho();
    }

    @Override
    protected List<Piece> initBoard(BoardSetup boardSetup) {
        List<Piece> choDefaultPosition = new ArrayList<>(List.of(
                new King(TeamName.CHO, new Position(4, 1)),
                new Cannon(TeamName.CHO, new Position(1, 2)),
                new Cannon(TeamName.CHO, new Position(7, 2)),
                new Chariot(TeamName.CHO, new Position(0, 0)),
                new Chariot(TeamName.CHO, new Position(8, 0)),
                new Soldier(TeamName.CHO, new Position(0, 3)),
                new Soldier(TeamName.CHO, new Position(2, 3)),
                new Soldier(TeamName.CHO, new Position(4, 3)),
                new Soldier(TeamName.CHO, new Position(6, 3)),
                new Soldier(TeamName.CHO, new Position(8, 3)),
                new Guard(TeamName.CHO, new Position(3, 0)),
                new Guard(TeamName.CHO, new Position(5, 0))
        ));
        choDefaultPosition.addAll(boardSetup.getPieces());
        return choDefaultPosition;
    }
}
