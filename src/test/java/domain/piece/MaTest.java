package domain.piece;

import domain.JanggiCoordinate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;


class MaTest {
    @Nested
    class PieceMove{
        @DisplayName("말의 이동 가능한 경로를 검사한다")
        @Test
        void maAvailableMovePosition(){
            Ma ma = new Ma(new JanggiCoordinate(0,0));

            List<JanggiCoordinate> availableMovePositions = ma.availableMovePositions();

            Assertions.assertThat(availableMovePositions.size()).isEqualTo(8);
        }
    }
}