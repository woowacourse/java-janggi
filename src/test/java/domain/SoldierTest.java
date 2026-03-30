package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    void 초_졸은_위_좌_우로_이동한다() {
        Soldier soldier = new Soldier(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = soldier.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 5),
                new Position(3, 4),
                new Position(5, 4)
        );
    }

    @Test
    void 한_졸은_아래_좌_우로_이동한다() {
        Soldier soldier = new Soldier(Side.HAN);
        Position from = new Position(4, 4);

        List<Position> destinations = soldier.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 3),
                new Position(3, 4),
                new Position(5, 4)
        );
    }

    @Test
    void 같은_진영_기물이_있는_칸으로는_이동하지_못한다() {
        Soldier soldier = new Soldier(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 5), new Soldier(Side.CHO));
        board.put(new Position(3, 4), new Soldier(Side.HAN));
        board.put(new Position(5, 4), new Soldier(Side.CHO));

        List<Position> destinations = soldier.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(3, 4)
        );
    }
}
