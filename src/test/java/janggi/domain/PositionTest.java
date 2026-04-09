package janggi.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PositionTest {

    @Test
    void 장기판에_모든_좌표가_생성된다() {
        // when & then
        for (int x = 0; x < 9; x++) {
            for(int y = 0; y < 10; y++) {
                Position position1 = Position.of(Row.of(x), Column.of(y));
                Position position2 = Position.of(Row.of(x), Column.of(y));

                assertThat(position1)
                        .as("x=%d, y=%d 에서 캐싱 실패", x, y)
                        .isSameAs(position2);
            }
        }
    }
}
