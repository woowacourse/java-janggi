package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SaTest {


    private Sa sa;

    @BeforeEach
    void setUp() {
        sa = new Sa(TeamType.CHO);
    }

    @ParameterizedTest
    @CsvSource({
            "9, 5, 9, 4",
            "9, 5, 9, 6",
            "9, 5, 8, 5",
            "9, 5, 10, 5",
    })
    void 사는_전후좌우로_한칸_움직인다(final int currentY, final int currentX, final int arrivalY, final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = sa.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 사는_한_칸_초과하여_움직일_수_없다() {
        // Given
        final int currentY = 10;
        final int currentX = 5;
        final int arrivalY = 8;
        final int arrivalX = 5;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> sa.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 사는 이어진 선을 따라 한 칸만 이동할 수 있습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "9, 5, 8, 4",
            "9, 5, 8, 6",
            "9, 5, 10, 4",
            "9, 5, 10, 6",
    })
    void 사는_궁성_안에서_간선을_따라_대각선으로_1칸_이동한다(final int currentY, final int currentX, final int arrivalY,
                                        final int arrivalX) {
        // Given
        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When
        Path path = sa.makePath(currentPosition, arrivalPosition);

        // Then
        assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
    }

    @Test
    void 사는_궁성_밖으로_벗어날_수_없다() {
        // Given
        final int currentY = 8;
        final int currentX = 5;
        final int arrivalY = 7;
        final int arrivalX = 5;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> sa.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 사는 궁성 밖을 벗어날 수 없습니다.");
    }

    @Test
    void 사는_궁성_안에서_간선이_이어지지_않았다면_이동할_수_없다() {
        // Given
        final int currentY = 8;
        final int currentX = 5;
        final int arrivalY = 9;
        final int arrivalX = 4;

        Position currentPosition = new Position(currentY, currentX);
        Position arrivalPosition = new Position(arrivalY, arrivalX);

        // When & Then
        assertThatThrownBy(() -> sa.makePath(currentPosition, arrivalPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 사는 이어진 선을 따라 한 칸만 이동할 수 있습니다.");
    }
}
