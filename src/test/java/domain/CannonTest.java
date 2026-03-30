package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    void 중간에_넘을_기물이_없으면_이동할_수_없다() {
        Cannon cannon = new Cannon(Side.CHO);
        Position from = new Position(4, 4);

        List<Position> destinations = cannon.getPossibleDestinations(from, new HashMap<>());

        assertThat(destinations).isEmpty();
    }

    @Test
    void 한_기물을_넘은_뒤_이동하고_포는_잡을_수_없다() {
        Cannon cannon = new Cannon(Side.CHO);
        Position from = new Position(4, 4);
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(4, 5), new Soldier(Side.HAN));
        board.put(new Position(4, 8), new Soldier(Side.HAN));
        board.put(new Position(4, 3), new Soldier(Side.HAN));
        board.put(new Position(4, 2), new Soldier(Side.CHO));
        board.put(new Position(3, 4), new Cannon(Side.HAN));
        board.put(new Position(5, 4), new Soldier(Side.HAN));
        board.put(new Position(7, 4), new Cannon(Side.HAN));

        List<Position> destinations = cannon.getPossibleDestinations(from, board);

        assertThat(destinations).containsExactlyInAnyOrder(
                new Position(4, 6),
                new Position(4, 7),
                new Position(4, 8),
                new Position(6, 4)
        );
    }
}
