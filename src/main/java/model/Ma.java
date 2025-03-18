package model;

import java.util.List;
import java.util.stream.IntStream;

public class Ma extends Piece {

    public Ma(String team) {
        super(team);
    }

    @Override
    public boolean canMove(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(-1, 1, 2, 2, 1, -1, -2, -2);
        List<Integer> vertical = List.of(2, 2, 1, -1, -2, -2, -1, 1);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }
}
