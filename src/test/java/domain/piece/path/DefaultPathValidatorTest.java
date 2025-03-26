package domain.piece.path;

import static fixtures.PositionFixture.E6;
import static fixtures.PositionFixture.E7;
import static fixtures.PositionFixture.E8;
import static fixtures.PositionFixture.E9;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Position;
import domain.piece.TeamType;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultPathValidatorTest {

    @Test
    @DisplayName("중간길에 유물이 있으면 예외가 발생한다.")
    void validatePathTest() {
        DefaultPathValidator cannonPathValidator = new DefaultPathValidator();
        Piece piece = new Horse(TeamType.HAN);
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                E7, new Elephant(TeamType.HAN)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(piece, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("해당 위치에 팀 유물이 있으면 예외가 발생한다.")
    void validatePathTest2() {
        DefaultPathValidator cannonPathValidator = new DefaultPathValidator();
        Piece piece = new Horse(TeamType.HAN);
        Position to = E6;
        List<Position> positions = List.of(E7, E8, E9);
        Map<Position, Piece> pieces = Map.of(
                to, new Elephant(TeamType.HAN)
        );

        assertThatThrownBy(() -> cannonPathValidator.validatePath(piece, to, positions, pieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }
}