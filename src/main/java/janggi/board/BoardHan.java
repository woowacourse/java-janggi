package janggi.board;

import janggi.team.Team;
import janggi.piece.*;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardHan {
    private final List<Piece> pieces;

    public BoardHan(BoardOption option) {
        pieces = initBoard(option);
    }

    public List<Piece> initBoard(BoardOption option) {
        List<Piece> hanDefaultPosition = new ArrayList<>(List.of(
                new King(Team.HAN, new Position(4, 8)),
                new Cannon(Team.HAN, new Position(1, 7)), new Cannon(Team.HAN, new Position(7, 7)),
                new Chariot(Team.HAN, new Position(0, 9)), new Chariot(Team.HAN, new Position(8, 9)),
                new Soldier(Team.HAN, new Position(0, 6)),
                new Soldier(Team.HAN, new Position(2, 6)),
                new Soldier(Team.HAN, new Position(4, 6)),
                new Soldier(Team.HAN, new Position(6, 6)),
                new Soldier(Team.HAN, new Position(8, 6)),
                new Guard(Team.HAN, new Position(3, 9)), new Guard(Team.HAN, new Position(5, 9))));
        hanDefaultPosition.addAll(option.getPieces());
        return hanDefaultPosition;
    }

    public List<Piece> getBoard() {
        return pieces;
    }
}
