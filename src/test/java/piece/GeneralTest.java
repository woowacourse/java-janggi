package piece;

import board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.Position;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class GeneralTest {

    @DisplayName("거리 상수가 이동 규칙에 맞게 생성되었다.")
    @Test
    void distance() throws Exception {

        // given
        Field distanceField = General.class.getDeclaredField("DISTANCE");
        distanceField.setAccessible(true);

        // when
        double distanceValue = distanceField.getDouble(null);

        // then
        assertThat(distanceValue).isEqualTo(1.0);
    }

    @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
    @Test
    void validateMove() {
        final Position src = new Position(1, 1);
        final Piece general = new General(src, Country.HAN);
        final Board board = new Board(new HashMap<>());

        // 정상 이동
        Position validDest = new Position(1, 2);
        assertThatCode(() -> general.validateMove(src, validDest, board))
                .doesNotThrowAnyException();

        // 예외 발생 이동
        Position invalidDest = new Position(1, 3);
        assertThatThrownBy(() -> general.validateMove(src, invalidDest, board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
