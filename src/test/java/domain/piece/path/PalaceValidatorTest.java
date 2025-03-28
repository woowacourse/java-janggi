package domain.piece.path;

import static fixtures.PositionFixture.C0;
import static fixtures.PositionFixture.D0;
import static fixtures.PositionFixture.E1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceValidatorTest {
    @Test
    @DisplayName("중간길에 유물이 있으면 예외가 발생한다.")
    void validatePathTest() {
        PalaceValidator palaceValidator = new PalaceValidator();
        Position to = D0;
        List<Position> positions = List.of(E1);
        Map<Position, Piece> pieces = Map.of(
                E1, new Elephant(TeamType.HAN)
        );

        assertThatThrownBy(() -> palaceValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("해당 위치에 팀 유물이 있으면 예외가 발생한다.")
    void validatePathTest2() {
        PalaceValidator palaceValidator = new PalaceValidator();
        Position to = D0;
        List<Position> positions = List.of();
        Map<Position, Piece> pieces = Map.of(
                to, new Elephant(TeamType.HAN)
        );

        assertThatThrownBy(() -> palaceValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("궁 외의 위치로 이동하려고 하면 예외가 발생한다.")
    void validatePathTest3() {
        PalaceValidator palaceValidator = new PalaceValidator();
        Position to = C0;
        List<Position> positions = List.of();
        Map<Position, Piece> pieces = Map.of();

        assertThatThrownBy(() -> palaceValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }
}