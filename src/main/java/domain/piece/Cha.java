package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final Team team;


    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
