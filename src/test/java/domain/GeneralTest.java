package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    void 빈_보드에서는_상하좌우로_이동한다() {
        General general = new General(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = general.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 5),
                new Position(4, 3),
                new Position(3, 4),
                new Position(5, 4)
        );
    }

    @Test
    void 같은_진영_기물이_있는_칸으로는_이동하지_못한다() {
        General general = new General(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 5), new Soldier(Side.CHO));
        board.put(new Position(4, 3), new Soldier(Side.HAN));
        board.put(new Position(3, 4), new Soldier(Side.CHO));
        board.put(new Position(5, 4), new Soldier(Side.HAN));

        List<Position> destinations = general.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 3),
                new Position(5, 4)
        );
    }
}
