import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.Position;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PositionTest {
    @Test
    void 포지션은_음수를_가질_수_없다”() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new Position(-1, -1));
    }

    @Test
    void 포지션은_양수를_가질_수_있다() {
        Assertions.assertThatNoException().isThrownBy(() -> new Position(0, 0));
    }
}
