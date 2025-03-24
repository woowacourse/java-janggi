package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Position;

public class PalaceTest {

    @Test
    @DisplayName("장기 말의 종류에는 궁이 있다.")
    void createPalaceTest(){
        Piece palace = new Palace(E1);
    }

    // todo: routes와 direction 선행 필요.
    @Test
    @DisplayName("궁은 상하좌우로 이동할 수 있다.")
    void movePalaceTest(){
        // given
        Position source = E1;
        Position destination = E0;
        Piece palace = new Palace(source);

        // when - then
        Assertions.assertThat(palace.canMove(destination)).isTrue();
    }
}
