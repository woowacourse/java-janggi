package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;

class PieceTest {
    @Test
    @DisplayName("같은 위치 판단 테스트")
    void isSamePositionTest() {
        //give
        Piece piece = new Jol(new Position(5, 5));
        Position position = new Position(5, 5);

        //when - then
        assertThat(piece.isSamePosition(position)).isTrue();
    }

}
