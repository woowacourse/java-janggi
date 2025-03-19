package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Nested
    class ValidCases {

        @DisplayName("기물에 대한 이동 규칙을 찾는다.")
        @Test
        void findMovementRule() {
            // given
            Piece piece = new Piece(PieceType.쭈, Team.RED);

            // when
            List<Offset> movementRule = piece.findMovementRule(
                new Position(0, 0),
                new Position(1, 0)
            );

            // then
            assertThat(movementRule).isEqualTo(List.of(new Offset(1, 0)));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("기물에 대한 이동 규칙을 찾을 수 없는 경우 예외가 발생한다.")
        @Test
        void findMovementRule() {
            // given
            Piece piece = new Piece(PieceType.쭈, Team.RED);

            // when & then
            assertThatThrownBy(() -> piece.findMovementRule(
                new Position(0, 0),
                new Position(0, 1)
            )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }
    }
}
