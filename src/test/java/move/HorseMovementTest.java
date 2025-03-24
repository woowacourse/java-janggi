package move;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import fixture.GreenPieceFixture;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

class HorseMovementTest {

    Pieces pieces = new Pieces(GreenPieceFixture.pieces);

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void test1() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(1, 0);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void test2() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(3, 0);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void test3() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 1);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void test4() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 3);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 1칸으로 이동할 수 있다.")
    void test5() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(1, 4);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 1칸으로 이동할 수 있다.")
    void test6() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(3, 4);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 1칸으로 이동할 수 있다.")
    void test7() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 1);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 1칸으로 이동할 수 있다.")
    void test8() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 3);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        Point result = horseMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 1칸을 제외하고 움직일 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        HorseMovement horseMovement = new HorseMovement(Team.GREEN.direction());

        //when
        //then
        Assertions.assertThatThrownBy(() -> horseMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
