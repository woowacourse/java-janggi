package domain.board;

import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HORSE;
import static org.assertj.core.api.Assertions.assertThat;

public class SetUpTest {

    @Test
    @DisplayName("왼상차림은 상마상마 순서를 가진다.")
    void leftElephantSetUp() {
        List<PieceType> types = SetUp.LEFT_ELEPHANT.placeOrder();

        assertThat(types).isEqualTo(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE));
    }

    @Test
    @DisplayName("오른상차림은 마상마상 순서를 가진다.")
    void rightElephantSetUp() {
        List<PieceType> types = SetUp.RIGHT_ELEPHANT.placeOrder();

        assertThat(types).isEqualTo(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT));
    }

    @Test
    @DisplayName("안상차림은 마상상마 순서를 가진다.")
    void innerElephantSetUp() {
        List<PieceType> types = SetUp.INNER_ELEPHANT.placeOrder();

        assertThat(types).isEqualTo(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE));
    }

    @Test
    @DisplayName("바깥상차림은 상마마상 순서를 가진다.")
    void outerElephantSetUp() {
        List<PieceType> types = SetUp.OUTER_ELEPHANT.placeOrder();

        assertThat(types).isEqualTo(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));
    }

}
