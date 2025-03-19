package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

import java.util.List;

public class Pho implements Piece{
    private final Team team;

    public Pho(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return null;
    }
}
