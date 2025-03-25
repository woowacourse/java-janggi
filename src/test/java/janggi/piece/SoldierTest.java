package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    private Soldier jolSoldier;
    private Soldier byeongSoldier;

    @BeforeEach
    void setUp() {
        jolSoldier = new Soldier(Team.CHO);
        byeongSoldier = new Soldier(Team.HAN);
    }

    @Nested
    class JolTest {

        @ParameterizedTest
        @CsvSource({
                "2, 1, 1, 1",
                "1, 1, 1, 2",
                "1, 2, 1, 1",
        })
        void 졸은_뒤로_갈_수_없고_앞과_양_옆으로만_이동한다(final int currentY, final int currentX, final int arrivalY,
                                         final int arrivalX) {
            // Given
            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When
            final Path path = jolSoldier.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, jolSoldier));

            // Then
            assertThat(path).isEqualTo(new Path(List.of(arrivalPosition)));
        }

        @Test
        void 졸은_뒤로_움직일_수_없다() {
            // Given
            final int currentY = 7;
            final int currentX = 1;
            final int arrivalY = 8;
            final int arrivalX = 1;

            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When & Then
            assertThatThrownBy(
                    () -> jolSoldier.makePath(currentPosition, arrivalPosition,
                            Map.of(currentPosition, new Soldier(Team.CHO))))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
        }

        @Test
        void 졸은_한_칸_초과하여_움직일_수_없다() {
            // Given
            final int currentY = 3;
            final int currentX = 3;
            final int arrivalY = 5;
            final int arrivalX = 5;

            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When & Then
            assertThatThrownBy(
                    () -> jolSoldier.makePath(currentPosition, arrivalPosition,
                            Map.of(currentPosition, new Soldier(Team.CHO))))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
        }
    }

    @Nested
    class ByeongTest {

        @ParameterizedTest
        @CsvSource({
                "1, 1, 2, 1",
                "1, 1, 1, 2",
                "1, 2, 1, 1",
        })
        void 병은_뒤로_갈_수_없고_앞과_양_옆으로만_이동한다(final int currentY, final int currentX, final int arrivalY,
                                         final int arrivalX) {
            // Given
            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When
            final Path path = byeongSoldier.makePath(currentPosition, arrivalPosition,
                    Map.of(currentPosition, new Soldier(Team.CHO)));
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

            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When & Then
            assertThatThrownBy(() -> byeongSoldier.makePath(currentPosition, arrivalPosition,
                    Map.of(currentPosition, new Soldier(Team.HAN))))
                    .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
        }

        @Test
        void 병은_한_칸_초과하여_움직일_수_없다() {
            // Given
            final int currentY = 3;
            final int currentX = 3;
            final int arrivalY = 5;
            final int arrivalX = 5;

            final Position currentPosition = new Position(currentY, currentX);
            final Position arrivalPosition = new Position(arrivalY, arrivalX);

            // When & Then
            assertThatThrownBy(() -> byeongSoldier.makePath(currentPosition, arrivalPosition,
                    Map.of(currentPosition, new Soldier(Team.HAN))))
                    .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
        }
    }

    @Test
    void 경로를_찾는다() {
        // Given
        final Position currentPosition = new Position(1, 2);
        final Position arrivalPosition = new Position(1, 1);

        // When
        final Path path = jolSoldier.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, new Soldier(Team.CHO)));

        // Then
        assertThat(path.getPositions()).isEqualTo(List.of(arrivalPosition));
    }
}
