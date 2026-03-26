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
    @DisplayName("입력이 1이면 왼상차림 순서를 반환한다.")
    void InputOne_Then_leftElephantSetUp() {
        int input = 1;

        List<PieceType> types = SetUp.from(input);

        assertThat(types).isEqualTo(List.of(ELEPHANT, HORSE, ELEPHANT, HORSE));
    }

    @Test
    @DisplayName("입력이 2면 오른상차림 순서를 반환한다.")
    void InputTwo_Then_rightElephantSetUp() {
        int input = 2;

        List<PieceType> types = SetUp.from(input);

        assertThat(types).isEqualTo(List.of(HORSE, ELEPHANT, HORSE, ELEPHANT));
    }

    @Test
    @DisplayName("입력이 3이면 안상차림 순서를 반환한다.")
    void InputThree_Then_innerElephantSetUp() {
        int input = 3;

        List<PieceType> types = SetUp.from(input);

        assertThat(types).isEqualTo(List.of(HORSE, ELEPHANT, ELEPHANT, HORSE));
    }

    @Test
    @DisplayName("입력이 4면 바깥상차림 순서를 반환한다.")
    void InputFour_Then_outerElephantSetUp() {
        int input = 4;

        List<PieceType> types = SetUp.from(input);

        assertThat(types).isEqualTo(List.of(ELEPHANT, HORSE, HORSE, ELEPHANT));
    }

}
