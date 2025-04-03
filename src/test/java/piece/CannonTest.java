package piece;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Team;

class CannonTest {

    @Test
    @DisplayName("포는 기물을 하나 넘어야 이동이 가능하다.")
    void cannon_can_move_over_a_piece() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new Point(2, 6));
        Pieces pieces = new Pieces(List.of(cannon, new Horse(Team.CHO, new Point(2, 5))));
        Point destination = new Point(2, 4);

        // when
        cannon.move(pieces, destination);

        // then
        Assertions.assertThat(cannon)
                .extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("포는 기물을 오직 하나만 넘지 않으면 이동이 불가능하다.")
    void cannon_can_move_only_a_piece() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new Point(2, 6));
        Pieces pieces = new Pieces(List.of(cannon));
        Point destination = new Point(2, 4);

        // when
        // then
        Assertions.assertThatThrownBy(() -> cannon.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다.")
    void cannon_is_not_over_a_cannon() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new Point(2, 6));
        Pieces pieces = new Pieces(List.of(cannon, new Cannon(Team.CHO, new Point(2, 5))));
        Point destination = new Point(2, 4);

        // when
        // then
        Assertions.assertThatThrownBy(() -> cannon.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포는 대각선 이동이 불가능하다.")
    void cannon_not_move_to_diagonal() {
        // given
        Cannon cannon = new Cannon(Team.CHO, new Point(2, 6));
        Pieces pieces = new Pieces(List.of(cannon));
        Point destination = new Point(3, 4);

        // when
        // then
        Assertions.assertThatThrownBy(() -> cannon.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택할 수 없는 목적지입니다.");
    }
}
