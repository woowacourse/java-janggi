package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.Arrays;

public class Elephant implements Piece{

    public Elephant(Position position) {
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Team getTeam() {
        return null;
    }

    @Override
    public boolean isOccupiedByMe(Position position) {
        return false;
    }

    @Override
    public void move(Position position) {

    }
}
