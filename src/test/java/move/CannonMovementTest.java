package move;

import direction.Point;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import team.Team;

class CannonMovementTest {

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
//
//    @Test
//    @DisplayName("수평 방향으로 이동할 수 있다.")
//    void test1() {
//        //given
//        Point from = new Point(2, 3);
//        Point to = new Point(3, 3);
//        CannonMovement cannonMovement = new CannonMovement();
//
//
//        //when
//        Point result = cannonMovement.move(pieces, from, to);
//
//        //then
//        assertThat(result).isEqualTo(to);
//    }
//
//    @Test
//    @DisplayName("수직 방향으로 이동할 수 있다.")
//    void test2() {
//        //given
//        Point from = new Point(2, 3);
//        Point to = new Point(2, 10);
//        CannonMovement cannonMovement = new CannonMovement();
//
//        //when
//        Point result = cannonMovement.move(pieces, from, to);
//
//        //then
//        assertThat(result).isEqualTo(to);
//    }

    @Test
    @DisplayName("수평, 수직이 아닌 경우 이동할 수 없다.")
    void test9() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(6, 3);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> cannonMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신이 있는 위치로 이동할 수 없다.")
    void test10() {
        //given
        Point from = new Point(2, 2);
        Point to = new Point(2, 2);
        CannonMovement cannonMovement = new CannonMovement();

        //when
        //then
        Assertions.assertThatThrownBy(() -> cannonMovement.move(pieces, from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
