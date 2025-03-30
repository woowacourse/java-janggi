package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class KingTest {
    private King king;

    @BeforeEach
    void setUp() {
        king = new King(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 왕은 전후좌우로 1칸을 이동 가능")
    @Test
    void validateKing() {
        assertThatCode(() -> king.validateMovement(new Position(4, 1), new Position(5, 1),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 왕은 궁성의 대각선을 타고 1칸을 이동 가능")
    @Test
    void validateKing_Palace() {
        assertThatCode(() -> king.validateMovement(new Position(4, 1), new Position(5, 2),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 왕은 좌우 최대 1칸 이동 가능")
    @Test
    void validateKing_LimitX() {
        assertThatThrownBy(() -> king.validateMovement(new Position(4, 1), new Position(2, 1),
                PalaceArea.INSIDE)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예외: 왕은 전후 최대 1칸 이동 가능")
    @Test
    void validateKing_LimitY() {
        assertThatThrownBy(() -> king.validateMovement(new Position(4, 1), new Position(4, 3),
                PalaceArea.INSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
