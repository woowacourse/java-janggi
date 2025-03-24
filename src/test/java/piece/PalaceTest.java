package piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import route.Routes;

public class PalaceTest {

    @Test
    @DisplayName("장기 말의 종류에는 궁이 있다.")
    void createPalaceTest(){
        Piece palace = new Palace();
    }

    /*
    0 ＿ * ＿
    1 * 궁 *
    2 ＿ * ＿
    3 d e f
    */
    // todo: routes 선행
    @Test
    @DisplayName("궁의 이동 가능한 경로를 모두 표시할 수 있다.")
    void possibleRoutesTest(){
        Piece palace = new Palace();

        Routes palaceRoutes = palace.possibleRoutes();
    }

    // todo: routes와 direction 선행 필요.
//    @Test
//    @DisplayName("궁은 상하좌우로 이동할 수 있다.")
//    void movePalaceTest(){
//        // given
//        Piece palace = new Palace(E1);
//
//        // when - then
//        Assertions.assertThat(palace.canMove(E0)).isTrue();
//    }
}
