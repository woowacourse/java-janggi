package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @Nested
    class ValidCases {

        @DisplayName("기물 타입에서 이동 규칙을 찾는다.")
        @Test
        void findMovementRule() {
            // given
            PieceType pieceType = PieceType.쭈;

            // when
            Optional<List<Offset>> optionalMovementRule = pieceType.findMovementRule(
                    new Offset(1, 0), Team.GREEN);

            // then
            assertThat(optionalMovementRule).isPresent();
        }
    }
}
