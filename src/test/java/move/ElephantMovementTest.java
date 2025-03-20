package move;

import static org.assertj.core.api.Assertions.assertThat;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

class ElephantMovementTest {

    List<Piece> greenPieces = List.of(
            new Piece("c", new Point(1, 10), new ChariotMovement()),
            new Piece("c", new Point(9, 10), new ChariotMovement()),
            new Piece("e", new Point(2, 10), new ElephantMovement(Team.GREEN.direction())),
            new Piece("e", new Point(7, 10), new ElephantMovement(Team.GREEN.direction())),
            new Piece("h",new Point(3, 10), new HorseMovement(Team.GREEN.direction())),
            new Piece("h",new Point(8, 10), new HorseMovement(Team.GREEN.direction())),
            new Piece("r",new Point(4, 10), new GuardMovement()),
            new Piece("r",new Point(6, 10), new GuardMovement()),
            new Piece("g",new Point(5, 9), new GeneralMovement()),
            new Piece("n", new Point(2, 8), new CannonMovement()),
            new Piece("n", new Point(8, 8), new CannonMovement()),
            new Piece("s", new Point(1, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(3, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(5, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(7, 7), new SoldierMovement(Team.GREEN.direction())),
            new Piece("s", new Point(9, 7), new SoldierMovement(Team.GREEN.direction()))
    );

    Pieces pieces = new Pieces(greenPieces);

    @Test
    @DisplayName("위쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test1() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, -1);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("위쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test2() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, -1);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test3() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 0);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("왼쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test4() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(-1, 4);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 왼쪽 대각선 2칸으로 이동할 수 있다.")
    void test5() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(0, 5);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("아래쪽 1칸, 오른쪽 대각선 2칸으로 이동할 수 있다.")
    void test6() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(4, 5);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 위쪽 대각선 2칸으로 이동할 수 있다.")
    void test7() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 0);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("오른쪽 1칸, 아래쪽 대각선 2칸으로 이동할 수 있다.")
    void test8() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(5, 4);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        Point result = elephantMovement.move(pieces, from, to);

        //then
        assertThat(result).isEqualTo(to);
    }

    @Test
    @DisplayName("직선 1칸, 대각선 2칸을 제외하고 움직일 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(8, 3);
        ElephantMovement elephantMovement = new ElephantMovement(Team.GREEN.direction());

        //when
        //then
        Assertions.assertThatThrownBy(() -> elephantMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
