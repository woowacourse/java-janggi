package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    @DisplayName("기물은 이동 후에 이동한 위치에 맞는 이동 경로를 알아야 한다.")
    void piece_after_moved_test() {
        Position pre = Position.of(4, 4);
        Piece piece = new Piece(PieceProperty.of(PieceType.HORSE, Team.RED), pre);

        Position after = Position.of(5, 6);
        piece = piece.moved(after);

        assertThat(piece.isMoveAble(Position.of(6,8))).isTrue();
        assertThat(piece.isMoveAble(Position.of(3,2))).isFalse();
    }
}