package routes;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.A5;
import static position.PositionFixtures.B5;
import static position.PositionFixtures.C5;
import static position.PositionFixtures.D1;
import static position.PositionFixtures.D5;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.E3;
import static position.PositionFixtures.E4;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.E6;
import static position.PositionFixtures.E7;
import static position.PositionFixtures.E8;
import static position.PositionFixtures.E9;
import static position.PositionFixtures.F1;
import static position.PositionFixtures.F5;
import static position.PositionFixtures.G5;
import static position.PositionFixtures.H5;
import static position.PositionFixtures.I5;

import janggi.piece.Piece;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.pawnPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.piece.palacePiece.King;
import janggi.piece.straightPiece.Chariot;
import janggi.position.Board;
import janggi.position.Position;
import janggi.route.Routes;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoutesTest {

    @Test
    @DisplayName("궁은 상하좌우 4가지의 이동 범위를 가진다.")
    void routesOfPalaceTest() {
        assertThat(Routes.ofPalace().routes().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("사는 상하좌우 4가지의 이동 범위를 가진다.")
    void routesOfSoldierTest() {
        assertThat(Routes.ofSoldier().routes().size()).isEqualTo(4);
    }

    /*
    0 ＿ * ＿
    1 * 궁 *
    2 ＿ * ＿
    3 d e f
    */
    @Test
    @DisplayName("일반 기물이 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest_1() {
        // given
        Piece king = new King(HAN, E1);
        Board board = new Board(Set.of(king));
        Routes routes = Routes.ofPalace();

        // when
        Set<Position> positions = routes.possibleRoutes(E1, board);

        // then
        assertThat(positions).containsOnly(E0, E2, D1, F1);
    }

    /*
    0  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    1  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    5  ＿ 마 *  * 차 * *  * *
    6  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ 병 ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
       a  b c  d e f g h i
    */
    @Test
    @DisplayName("차가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleStraightRoutesTest() {
        // given
        Piece chariot = new Chariot(HAN, E5);
        Piece hanPawn = new HanPawn(E7);
        Piece horse = new Horse(CHO, B5);
        Routes routes = Routes.ofChariot();
        Board board = new Board(HAN, Set.of(chariot, hanPawn, horse));

        // when
        Set<Position> positions = routes.possibleStraightRoutes(E5, board);

        // then
        assertThat(positions).containsOnly(E0, E1, E2, E3, E4, D5, C5, B5, E6, F5, G5, H5, I5);
    }

    /*
    0  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    1  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    2  ＿ ＿ ＿ ＿ 포 ＿ ＿ ＿ ＿
    3  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
    5  차 ＿ ＿ ＿ 포 ＿ ＿ 마 *
    6  ＿ ＿ ＿ ＿ 상 ＿ ＿ ＿ ＿
    7  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    8  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
    9  ＿ ＿ ＿ ＿ * ＿ ＿ ＿ ＿
       a  b c  d e f g  h i
    */
    @Test
    @DisplayName("포가 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleJumpingRoutesTest() {
        // given
        Piece cannon1 = new Cannon(HAN, E5);
        Piece cannon2 = new Cannon(CHO, E2);
        Piece horse = new Horse(HAN, H5);
        Piece elephant = new Elephant(CHO, E6);
        Piece chariot = new Chariot(CHO, A5);
        Board board = new Board(HAN, Set.of(cannon1, cannon2, horse, elephant, chariot));

        Routes routes = Routes.ofCannon();

        // when
        Set<Position> positions = routes.possibleJumpingRoutes(E5, board);

        // then
        assertThat(positions).containsOnly(E7, E8, E9, I5);
    }
}
