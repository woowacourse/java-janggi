package move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import direction.Point;
import fixture.GreenPieceFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

class SoldierMovementTest {

    Pieces pieces = new Pieces(GreenPieceFixture.pieces);

    @Test
    @DisplayName("졸은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-1);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        Point result = soldierMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-2);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        // then
        assertThatThrownBy(() -> soldierMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 아래로 한 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y+1);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        // then
        assertThatThrownBy(() -> soldierMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-1, y);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        Point result = soldierMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-2, y);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        // then
        assertThatThrownBy(() -> soldierMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+1, y);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        Point result = soldierMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("졸은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+2, y);
        SoldierMovement soldierMovement = new SoldierMovement(Team.GREEN.direction());

        // when
        // then
        assertThatThrownBy(() -> soldierMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
