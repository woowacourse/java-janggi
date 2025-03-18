package piece;

import static org.junit.jupiter.api.Assertions.*;

import board.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {


    @Nested
    @DisplayName("피스 생성")
    class Construct {

        @DisplayName("피스를 올바로 생성한다.")
        @Test
        void construct1() {
            // given
            final PieceType type = PieceType.CANNON;
            final Position position = new Position(1, 1);

            // when
            final Piece piece = new Piece(type, position);

            // then
            assertAll(
                    () -> Assertions.assertThat(piece.getType()).isEqualTo(type),
                    () -> Assertions.assertThat(piece.getPosition()).isEqualTo(position)
            );
        }
    }
}
