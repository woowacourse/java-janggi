package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    void 빈_보드에서는_8곳으로_이동한다() {
        Horse horse = new Horse(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = horse.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(5, 6),
                new Position(3, 6),
                new Position(5, 2),
                new Position(3, 2),
                new Position(2, 5),
                new Position(2, 3),
                new Position(6, 5),
                new Position(6, 3)
        );
    }

    @Test
    void 다리_칸이_막히면_해당_방향으로_이동하지_못한다() {
        Horse horse = new Horse(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 5), new Soldier(Side.HAN));
        board.put(new Position(6, 5), new Soldier(Side.CHO));
        board.put(new Position(2, 3), new Soldier(Side.HAN));

        List<Position> destinations = horse.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(5, 2),
                new Position(3, 2),
                new Position(2, 5),
                new Position(2, 3),
                new Position(6, 3)
        );
    }
}
