package move;

import static org.assertj.core.api.Assertions.*;

import direction.Point;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

public class GeneralMovementTest {

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
    @DisplayName("궁성 내 기물은 위로 한 칸 이동할 수 있다.")
    void test1() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-1);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁성 내 기물은 위로 두 칸 이상 이동할 수 없다.")
    void test2() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y-2);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 한 칸 이동할 수 있다.")
    void test3() {
        // given
        int x = 0;
        int y = 1;
        Point from = new Point(x, y);
        Point to = new Point(x, y+1);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁성 내 기물은 아래로 두 칸 이상 이동할 수 없다.")
    void test4() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x, y+2);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 한 칸 이동할 수 있다.")
    void test5() {
        // given
        int x = 1;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-1, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁성 내 기물은 왼쪽으로 두 칸 이상 이동할 수 없다.")
    void test6() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x-2, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 한 칸 이동할 수 있다.")
    void test7() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+1, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        Point result = generalMovement.move(pieces, from, to);

        // then
        assertThat(result)
                .isEqualTo(to);
    }

    @Test
    @DisplayName("궁성 내 기물은 오른쪽으로 두 칸 이상 이동할 수 없다.")
    void test8() {
        // given
        int x = 0;
        int y = 0;
        Point from = new Point(x, y);
        Point to = new Point(x+2, y);
        GeneralMovement generalMovement = new GeneralMovement();

        // when
        // then
        assertThatThrownBy(() -> generalMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
