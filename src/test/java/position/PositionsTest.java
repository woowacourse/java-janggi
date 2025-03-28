package position;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.position.Positions;
import janggi.route.Routes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PositionsTest {

    @Test
    @DisplayName("궁성에서 이동 가능한 루트를 알 수 있다.")
    void addPossiblePalaceDirectionsTest() {
        // given
        Positions positions = new Positions();

        // when
        Routes routes = positions.addPossiblePalaceDirections(Positions.D0);

        // then
        assertThat(routes.routes().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("궁성에 해당하는 위치인지 알 수 있다.")
    void isInPalaceTest() {
        // given
        Positions positions = new Positions();

        // when - then
        assertThat(positions.isInPalace(Positions.E1)).isTrue();
    }

    @Test
    @DisplayName("궁성에 해당하는 위치인지 알 수 있다.")
    void isInPalaceTest_2() {
        // given
        Positions positions = new Positions();

        // when - then
        assertThat(positions.isInPalace(Positions.I0)).isFalse();
    }
}
