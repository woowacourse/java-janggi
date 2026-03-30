package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 빈_보드에서는_8곳으로_이동한다() {
        Elephant elephant = new Elephant(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = elephant.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(6, 7),
                new Position(2, 7),
                new Position(6, 1),
                new Position(2, 1),
                new Position(1, 6),
                new Position(1, 2),
                new Position(7, 6),
                new Position(7, 2)
        );
    }

    @Test
    void 경로가_막힌_방향으로는_이동하지_못한다() {
        Elephant elephant = new Elephant(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 3), new Soldier(Side.HAN));
        board.put(new Position(5, 6), new Soldier(Side.HAN));
        board.put(new Position(1, 6), new Soldier(Side.CHO));
        board.put(new Position(1, 2), new Soldier(Side.HAN));

        List<Position> destinations = elephant.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(2, 7),
                new Position(1, 2),
                new Position(7, 6),
                new Position(7, 2)
        );
    }
}
