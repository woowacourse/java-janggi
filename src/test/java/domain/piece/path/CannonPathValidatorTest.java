package domain.piece.path;

import static fixtures.PositionFixture.E6;
import static fixtures.PositionFixture.E7;
import static fixtures.PositionFixture.E8;
import static fixtures.PositionFixture.E9;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Position;
import domain.piece.TeamType;
import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonPathValidatorTest {

    @Test
    @DisplayName("해당 자리에 팀이 있으면 예외가 발생한다.")
    void validatePathTest() {
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                to, new Elephant(TeamType.HAN)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("해당 자리에 캐논이 있으면 예외가 발생한다.")
    void validatePathTest2() {
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                to, new Cannon(TeamType.CHO)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("캐논을 넘을 수 없다.")
    void validatePathTest3() {
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                E9, new Cannon(TeamType.CHO)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("중간에 하나 이상의 기물이 있으면 예외가 발생한다.")
    void validatePathTest4() {
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                E9, new Elephant(TeamType.CHO),
                E7, new Horse(TeamType.CHO)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(TeamType.HAN, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }
}