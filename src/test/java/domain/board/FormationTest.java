package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.PieceType;
import java.util.List;
import org.junit.jupiter.api.Test;

class FormationTest {

    @Test
    void 포메이션은_기물_배치_순서를_가진다() {
        assertThat(Formation.LEFT_ELEPHANT.getOrders())
                .isEqualTo(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE));
        assertThat(Formation.RIGHT_ELEPHANT.getOrders())
                .isEqualTo(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT));
        assertThat(Formation.OUTER_ELEPHANT.getOrders())
                .isEqualTo(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT));
        assertThat(Formation.INNER_ELEPHANT.getOrders())
                .isEqualTo(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE));
    }
}
