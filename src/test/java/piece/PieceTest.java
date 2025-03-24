package piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pieceProperty.Position;
import player.Pieces;

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

    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        //given
        Position position = new Position(5, 5);
        Piece piece = new Jol(position);

        //when
        piece.updateChessPiecePositionBy(new Position(5, 6));

        //then
        assertThat(piece.isSamePosition(new Position(5, 6))).isTrue();
    }

}
