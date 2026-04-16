package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Test
    @DisplayName("중앙을 중심으로 점대칭 좌표를 반환")
    void 중앙_중심으로_점대칭_반환() {
        //given
        Position currentPosition = new Position(4, 10);
        Palace palace = Palace.getPalace(currentPosition);

        //when
        Assertions.assertNotNull(palace);
        Position oppositePosition = palace.calculateOppositeVertex(currentPosition);

        //then
        assertThat(oppositePosition).isEqualTo(new Position(6, 8));
    }

}
