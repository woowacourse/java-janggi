package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ByeongTest {

    private Byeong byeong;

    @BeforeEach
    void setUp() {
        byeong = new Byeong();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "1, 1, 1, 2",
            "1, 2, 1, 1",
    })
    void 병은_전진_또는_좌우로_한칸_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = byeong.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 병은_뒤로_움직일_수_없다() {
        // Given
        final int currentY = 2;
        final int currentX = 1;
        final int arrivalY = 1;
        final int arrivalX = 1;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> byeong.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 병은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
    }

    @Test
    void 병은_한_칸_초과하여_움직일_수_없다() {
        // Given
        final int currentY = 3;
        final int currentX = 3;
        final int arrivalY = 5;
        final int arrivalX = 5;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> byeong.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 병은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "9, 5, 10, 4",
            "9, 5, 10, 6",
    })
    void 병은_궁성_안에서_간선을_따라_대각선으로_1칸_이동한다(final int currentY, final int currentX, final int arrivalY,
                                        final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = byeong.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }
}
