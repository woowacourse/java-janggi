package object.piece.generator;

import static object.board.Board.BOARD_MAX_HEIGHT;
import static object.board.Board.BOARD_MAX_WIDTH;
import static object.board.Board.BOARD_MIN_HEIGHT;
import static object.board.Board.BOARD_MIN_WIDTH;

import object.coordinate.Coordinate;
import java.util.HashSet;
import java.util.Set;

public class CrossPathGenerator implements PathGenerator {

    @Override
    public Set<Coordinate> generate(Coordinate departure) {
        Set<Coordinate> coordinates = new HashSet<>();

        for (int x = BOARD_MIN_WIDTH; x <= BOARD_MAX_WIDTH; x++) {
            coordinates.add(new Coordinate(x, departure.getY()));
        }

        for (int y = BOARD_MIN_HEIGHT; y <= BOARD_MAX_HEIGHT; y++) {
            coordinates.add(new Coordinate(departure.getX(), y));
        }

        coordinates.remove(departure);
        return coordinates;
    }
}
