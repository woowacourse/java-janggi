package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

import java.util.List;

public class Byeong implements Piece{
    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }


    @Override
    public Team getTeam() {
        return team;
    }
}
