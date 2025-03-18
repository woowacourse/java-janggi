package domain.piece;

import domain.JanggiCoordinate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class PieceTest {
    @Nested
    class MaTest {
        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition() {
            Ma ma = new Ma(new JanggiCoordinate(0, 0));

            List<JanggiCoordinate> availableMovePositions = ma.availableMovePositions();

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(8);
        }
    }

    @Nested
    class SangTest {
        @DisplayName("상의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            Sang sang = new Sang(new JanggiCoordinate(0, 0));

            List<JanggiCoordinate> availableMovePositions = sang.availableMovePositions();

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(8);
        }
    }

    @Nested
    class ChaTest {
        @DisplayName("차의 이동 가능한 경로를 검사한다")
        @Test
        void sangAvailableMovePosition() {
            Cha cha = new Cha(new JanggiCoordinate(1, 1));

            List<JanggiCoordinate> availableMovePositions = cha.availableMovePositions();

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(17);
        }
    }
    
}