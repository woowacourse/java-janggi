package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
            Piece piece = new Piece(PieceType.ZZU, Team.RED);

            // when
            List<Offset> movementRule = piece.findMovementRule(
                    new BoardPosition(0, 0),
                    new BoardPosition(1, 0)
            );

            // then
            assertThat(movementRule).isEqualTo(List.of(new Offset(1, 0)));
        }

        @DisplayName("기물의 이동 경로에 있는 장애물 갯수가 허용되는 갯수인지 확인한다.")
        @Test
        void isObstacleCountAllowed() {
            // given
            Piece piece = new Piece(PieceType.CANNON, Team.RED);

            // when & then
            assertAll(
                    () -> assertThat(piece.isObstacleCountAllowed(1)).isTrue(),
                    () -> assertThat(piece.isObstacleCountAllowed(2)).isFalse()
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("기물에 대한 이동 규칙을 찾을 수 없는 경우 예외가 발생한다.")
        @Test
        void findMovementRule() {
            // given
            Piece piece = new Piece(PieceType.ZZU, Team.RED);

            // when & then
            assertThatThrownBy(() -> piece.findMovementRule(
                    new BoardPosition(0, 0),
                    new BoardPosition(0, 1)
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 말은 해당 위치로 이동할 수 없습니다.");
        }
    }
}
