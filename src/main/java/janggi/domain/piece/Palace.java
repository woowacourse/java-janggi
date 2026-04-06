package janggi.domain.piece;

import janggi.domain.Position;

import java.util.List;
import java.util.stream.IntStream;

public class Palace {

    private static final List<Position> choPalace;
    private static final List<Position> hanPalace;

    static {
        choPalace = IntStream.range(0, 3)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> Position.of(row, column)))
                .toList();
        hanPalace = IntStream.range(7, 10)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> Position.of(row, column)))
                .toList();
    }

    public boolean isPalace(Position position) {
        return choPalace.contains(position) || hanPalace.contains(position);
    }
}
