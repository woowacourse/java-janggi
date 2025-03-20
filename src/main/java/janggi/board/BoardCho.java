package janggi.board;

import janggi.team.Team;
import janggi.piece.*;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardCho {
    private final List<Piece> pieces;

    public BoardCho(BoardOption option) {
        pieces = initBoard(option);
    }

    public List<Piece> initBoard(BoardOption option) {
        List<Piece> choDefaultPosition = new ArrayList<>(List.of(
                new King(Team.CHO,new Position(4, 1)),
                new Cannon(Team.CHO,new Position(1, 2)), new Cannon(Team.CHO,new Position(7, 2)),
                new Chariot(Team.CHO,new Position(0, 0)), new Chariot(Team.CHO,new Position(8, 0)),
                new Soldier(Team.CHO,new Position(0, 3)),
                new Soldier(Team.CHO,new Position(2, 3)),
                new Soldier(Team.CHO,new Position(4, 3)),
                new Soldier(Team.CHO,new Position(6, 3)),
                new Soldier(Team.CHO,new Position(8, 3)),
                new Guard(Team.CHO,new Position(3, 0)), new Guard(Team.CHO,new Position(5, 0))
        ));
        choDefaultPosition.addAll(option.getPieces());
        return choDefaultPosition;
    }

    public List<Piece> getBoard() {
        return pieces;
    }
}
