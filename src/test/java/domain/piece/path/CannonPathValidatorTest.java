package domain.piece.path;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.TeamType;
import domain.piece.Cannon;
import domain.piece.Horse;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonPathValidatorTest {

    private Board board;

    @BeforeEach
    void beforeEach() {
        board = new Board(
                Map.of(
                        Position.of(3, 4), new Cannon(TeamType.HAN),
                        Position.of(5, 2), new Horse(TeamType.CHO),
                        Position.of(3, 1), new Soldier(TeamType.CHO),
                        Position.of(7, 2), new Cannon(TeamType.CHO)
                )
        );
    }

    @Test
    @DisplayName("포는 포를 뛰어넘지 못한다")
    void validateMovePathJumpCannonException() {
        // given
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        List<Position> positions = List.of(Position.of(3, 3), Position.of(3, 4), Position.of(3, 5));
        Cannon cannon = new Cannon(TeamType.HAN);

        // when & then
        assertThatThrownBy(() -> cannonPathValidator.validateMovePath(positions, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 뛰어넘을 수 없습니다.");
    }

    @Test
    @DisplayName("포는 하나의 기물을 뛰어넘지 않으면 예외가 발생한다")
    void validateMovePathJumpPieceException() {
        // given
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        List<Position> positions = List.of(Position.of(4, 2));
        Cannon cannon = new Cannon(TeamType.HAN);

        // when & then
        assertThatThrownBy(() -> cannonPathValidator.validateMovePath(positions, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 하나의 기물을 뛰어넘어야 합니다.");
    }

    @Test
    @DisplayName("포는 하나의 기물을 뛰어넘으면 예외가 발생하지 않는다")
    void validateMovePathJumpPiece() {
        // given
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        List<Position> positions = List.of(Position.of(4, 2), Position.of(5, 2), Position.of(6, 2));
        Cannon cannon = new Cannon(TeamType.HAN);

        // when & then
        assertThatCode(() -> cannonPathValidator.validateMovePath(positions, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이동하려는 위치에 같은 팀의 기물이 있으면 예외가 발생한다")
    void validateDestinationSameTeam() {
        // given
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position destination = Position.of(7, 2);
        Cannon cannon = new Cannon(TeamType.CHO);

        // when & then
        assertThatThrownBy(() -> cannonPathValidator.validateDestination(destination, board, cannon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("포가 포를 잡으려고 하면 예외가 발생한다")
    void validateDestinationCannon() {
        // given
        CannonPathValidator cannonPathValidator = new CannonPathValidator();
        Position destination = Position.of(3, 4);
        Cannon cannon = new Cannon(TeamType.CHO);

        // when & then
        assertThatThrownBy(() -> cannonPathValidator.validateDestination(destination, board, cannon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포를 잡을 수 없습니다.");
    }


}
