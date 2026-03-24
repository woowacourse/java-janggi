import domain.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    void Point의_좌표가_범위를_벗어나는_경우에_에러_발생(){
        int outOfIndexY = 10;
        int outOfIndexX = 9;

        Assertions.assertThatThrownBy(() -> {
            new Point(outOfIndexY, outOfIndexX);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
