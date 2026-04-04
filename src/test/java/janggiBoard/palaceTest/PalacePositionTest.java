package janggiBoard.palaceTest;

import domain.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PalacePositionTest {

    @Test
    void 한나라_현재_좌표가_궁성내에_존재하면_참을_반환한다() {
        Position currentPosition = new Position(0, 3);
        Assertions.assertThat(currentPosition.isInsidePalace()).isTrue();
    }

    @Test
    void 한나라_현재_좌표가_궁성내에_존재하지_않으면_거짓을_반환한다() {
        Position currentPosition = new Position(3, 3);
        Assertions.assertThat(currentPosition.isInsidePalace()).isFalse();
    }

    @Test
    void 초나라_현재_좌표가_궁성내에_존재하면_참을_반환한다() {
        Position currentPosition = new Position(7, 3);
        Assertions.assertThat(currentPosition.isInsidePalace()).isTrue();
    }

    @Test
    void 초나라_현재_좌표가_궁성내에_존재하지_않으면_거짓을_반환한다() {
        Position currentPosition = new Position(8, 6);
        Assertions.assertThat(currentPosition.isInsidePalace()).isFalse();
    }

    @Test
    void 한나라_상대좌표를_반환한다() {
        Position currentPosition = new Position(0, 3);
        Position relativePosition = new Position(0, 0);
        Assertions.assertThat(currentPosition.toRelative()).isEqualTo(relativePosition);
    }

    @Test
    void 초나라_상대좌표를_반환한다() {
        Position currentPosition = new Position(7, 3);
        Position relativePosition = new Position(0, 0);
        Assertions.assertThat(currentPosition.toRelative()).isEqualTo(relativePosition);
    }
}
