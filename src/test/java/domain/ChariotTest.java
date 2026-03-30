package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    void 빈_보드에서는_직선_모든_칸으로_이동한다() {
        Chariot chariot = new Chariot(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = chariot.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 5),
                new Position(4, 6),
                new Position(4, 7),
                new Position(4, 8),
                new Position(4, 9),
                new Position(4, 3),
                new Position(4, 2),
                new Position(4, 1),
                new Position(4, 0),
                new Position(3, 4),
                new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4),
                new Position(5, 4),
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4)
        );
    }

    @Test
    void 기물이_있으면_해당_칸까지만_이동하고_더_이상_가지_못한다() {
        Chariot chariot = new Chariot(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 6), new Soldier(Side.CHO));
        board.put(new Position(4, 2), new Soldier(Side.HAN));
        board.put(new Position(2, 4), new Soldier(Side.CHO));
        board.put(new Position(6, 4), new Soldier(Side.HAN));

        List<Position> destinations = chariot.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 5),
                new Position(4, 3),
                new Position(4, 2),
                new Position(3, 4),
                new Position(5, 4),
                new Position(6, 4)
        );
    }
}
