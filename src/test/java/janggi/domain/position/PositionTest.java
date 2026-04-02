package janggi.domain.position;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PositionTest {

    @Test
    void 장기판의_모든_좌표가_정상적으로_캐싱되어_생성된다() {
        // when & then
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 9; c++) {
                Position position1 = Position.of(Row.of(r), Column.of(c));
                Position position2 = Position.of(Row.of(r), Column.of(c));

                assertThat(position1)
                        .as("row=%d, col=%d 에서 캐싱 실패", r, c)
                        .isSameAs(position2);
            }
        }
    }
}
