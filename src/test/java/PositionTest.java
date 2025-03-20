import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.Position;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PositionTest {
    @Test
    void 포지션은_숫자를_가진다() {
        // given

        // when

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Position(0, 0));
    }

    @Test
    void 포지션은_더할_수_있다() {
        // given
        Position position = new Position(0, 0);
        Position addPosition = new Position(0, 1);

        // when
        Position newPosition = position.add(addPosition);

        // then
        Assertions.assertThat(newPosition).isEqualTo(new Position(0, 1));
    }
}
