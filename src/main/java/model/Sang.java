package model;

import java.util.List;
import java.util.stream.IntStream;

public class Sang extends Piece {
    public Sang(String team) {
        super(team);
    }

    @Override
    public boolean canMove(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(-2, 2, 3, 3, 2, -2, -3, -3);
        List<Integer> vertical = List.of(3, 3, 2, -2, -3, -3, -2, 2);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }
}
