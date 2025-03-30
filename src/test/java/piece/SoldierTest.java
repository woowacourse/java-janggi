package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.Player;
import team.Team;

class SoldierTest {

    @Test
    @DisplayName("CHO 졸은 전진할 수 있다.")
    void can_move_up_cho() {
        Point start = new Point(3, 3);
        Point destination = new Point(3, 2);

        Soldier soldier = new Soldier(start, Team.CHO);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        soldier.move(pieces, destination);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("HAN 졸은 전진할 수 있다.")
    void can_move_up_han() {
        Point start = new Point(3, 3);
        Point destination = new Point(3, 4);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        soldier.move(pieces, destination);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("졸은 두 칸 이상 전진할 수 없다.")
    void only_move_up_once() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(3, 5);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("CHO 졸은 한 칸 이상 후진할 수 없다.")
    void dont_move_down_cho() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(3, 4);

        Soldier soldier = new Soldier(start, Team.CHO);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("HAN 졸은 한 칸 이상 후진할 수 없다.")
    void dont_move_down_han() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(3, 2);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 한 칸 이동할 수 있다.")
    void can_move_left() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(2, 3);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        soldier.move(pieces, destination);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_left_once() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(1, 3);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 한 칸 이동할 수 있다.")
    void can_move_right() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(4, 3);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        soldier.move(pieces, destination);

        // then
        assertThat(soldier).extracting("current")
                .isEqualTo(destination);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void only_move_right_once() {
        // given
        Point start = new Point(3, 3);
        Point destination = new Point(5, 3);

        Soldier soldier = new Soldier(start, Team.HAN);
        Pieces pieces = new Pieces(List.of(soldier));

        // when
        // then
        assertThatThrownBy(() -> soldier.move(pieces, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("경로 상 본인의 기물이 있으면 움직일 수 없다.")
    void dont_move_to_exist_piece_in_path() {
        // given
        Soldier soldier = new Soldier(new Point(2, 2), Team.HAN);
        Player player = new Player(new Pieces(List.of(soldier, new Horse(new Point(3, 2)))), 0, Team.HAN);

        Point destination = new Point(3, 2);

        //when
        //then
        Assertions.assertThatThrownBy(() -> soldier.move(new Pieces(player.getPieces()), destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
