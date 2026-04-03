package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceTest {
    private final Palace palace = new Palace();

    @Test
    @DisplayName("궁성 내 대각선으로 양 끝에서 이동하는 경우 중앙 경로를 반환한다.")
    void findDiagonalPath_When_OppositeCorners() {
        Position from = new Position(4, 1);
        Position to = new Position(6, 3);

        assertThat(palace.findDiagonalPath(from, to))
                .contains(List.of(new Position(5, 2)));
    }
}
