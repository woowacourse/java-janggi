package domain.piece;

import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece{

    private final List<List<Move>> moves = List.of(List.of(Move.FRONT,Move.FRONT_LEFT), List.of(Move.FRONT,Move.FRONT_RIGHT), List.of(Move.BACK,Move.BACK_LEFT), List.of(Move.BACK,Move.BACK_RIGHT)
            ,List.of(Move.RIGHT,Move.FRONT_RIGHT),List.of(Move.RIGHT,Move.BACK_RIGHT),List.of(Move.LEFT,Move.FRONT_LEFT),List.of(Move.LEFT,Move.BACK_LEFT));

    public Horse(Team team) {
        super(team);
    }

    @Override
    public List<Move> calculatePath(int startRow, int startColumn, int targetRow, int targetColumn) {
        return null;
    }
}
