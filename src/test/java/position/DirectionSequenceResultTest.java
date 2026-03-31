package position;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class DirectionSequenceResultTest {

    @Test
    void 이동_경로가_비어있으면_마지막_위치를_반환할_수_없다() {
        // given
        DirectionSequenceResult result = new DirectionSequenceResult(List.of());

        // when & then
        assertThatThrownBy(result::lastPosition)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
