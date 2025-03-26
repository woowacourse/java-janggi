package move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import direction.Point;
import fixture.ChoPiecesFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Pieces;
import piece.Soldier;
import team.Team;

class SoldierTest {

    Pieces pieces = new Pieces(ChoPiecesFixture.pieces);

    @Test
    @DisplayName("졸은 위로 한 칸 이동할 수 있다.")
    void can_move_up() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 1);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        soldier.move(pieces, to);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 위로 두 칸 이상 이동할 수 없다.")
    void only_move_up_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y - 2);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 아래로 한 칸 이상 이동할 수 없다.")
    void dont_move_down() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y + 1);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 한 칸 이동할 수 있다.")
    void can_move_left() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 1, y);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        soldier.move(pieces, to);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_left_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x - 2, y);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 한 칸 이동할 수 있다.")
    void can_move_right() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 1, y);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        soldier.move(pieces, to);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_right_once() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x + 2, y);
        Soldier soldier = new Soldier("s", from, Team.CHO);

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
