package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.Movement;

import java.util.List;

public class Ma implements Piece {
    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
