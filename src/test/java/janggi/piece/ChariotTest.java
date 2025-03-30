package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {
    private Chariot chariot;

    @BeforeEach
    void setUp() {
        chariot = new Chariot(TeamName.CHO, new Position(0, 0));
    }

    @DisplayName("정상: 포는 전후좌우로 다른 기물 하나를 넘어 여러 칸을 이동 가능")
    @Test
    void validateChariot() {
        assertThatCode(() -> chariot.validateMovement(new Position(0, 0), new Position(0, 7),
                PalaceArea.OUTSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("정상: 포는 궁성의 대각선을 타고 다른 기물 하나를 넘어 최대 2칸을 이동 가능")
    @Test
    void validateChariot_Palace() {
        assertThatCode(() -> chariot.validateMovement(new Position(3, 0), new Position(5, 2),
                PalaceArea.INSIDE)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 포는 다른 기물 하나를 넘어 좌우 최대 8칸 이동 가능")
    @Test
    void validateChariot_LimitX() {
        assertThatThrownBy(() -> chariot.validateMovement(new Position(0, 0), new Position(9, 0),
                PalaceArea.OUTSIDE)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예외: 포는 다른 기물 하나를 넘어 전후 최대 9칸 이동 가능")
    @Test
    void validateChariot_LimitY() {
        assertThatThrownBy(() -> chariot.validateMovement(new Position(0, 0), new Position(0, 10),
                PalaceArea.OUTSIDE)).isInstanceOf(IllegalArgumentException.class);
    }
}
