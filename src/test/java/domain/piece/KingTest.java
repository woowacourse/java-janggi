package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.TeamType;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class KingTest {

    static Stream<Arguments> validateMoveEmptyPosition() {
        return Stream.of(
                Arguments.of(Position.of(2, 4)),
                Arguments.of(Position.of(0, 4)),
                Arguments.of(Position.of(1, 3)),
                Arguments.of(Position.of(1, 5)),
                Arguments.of(Position.of(2, 3)),
                Arguments.of(Position.of(2, 5)),
                Arguments.of(Position.of(0, 3)),
                Arguments.of(Position.of(0, 5))
        );
    }

    static Stream<Arguments> canMoveKingException() {
        return Stream.of(
                Arguments.of(Position.of(1, 3), Position.of(2, 4)),
                Arguments.of(Position.of(1, 3), Position.of(0, 4)),
                Arguments.of(Position.of(1, 5), Position.of(2, 4)),
                Arguments.of(Position.of(1, 5), Position.of(0, 4))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void validateMoveEmptyPosition(Position movePosition) {
        // given
        Position position = Position.of(1, 4);
        Piece king = new King(TeamType.CHO);

        // when & then
        assertThatCode(() -> king.validateMove(position, movePosition, new Board(Map.of())))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 위치가 올바르지 않으면 예외가 발생한다")
    void canMoveKingException(Position from, Position to) {
        // given
        Piece king = new King(TeamType.CHO);
        Board board = new Board(Map.of());

        // when & then
        assertThatThrownBy(() -> king.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정한 포지션으로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void validateMoveTeam() {
        // given
        Position movePosition = Position.of(2, 3);
        Position position = Position.of(1, 3);
        Piece king = new King(TeamType.CHO);

        Position teamPosition = Position.of(2, 3);
        Piece team = new Soldier(TeamType.CHO);
        Board board = new Board(Map.of(teamPosition, team));

        // when & then
        assertThatThrownBy(() -> king.validateMove(position, movePosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void validateMoveEnemy() {
        // given
        Position movePosition = Position.of(2, 3);
        Position position = Position.of(1, 3);
        Piece king = new King(TeamType.CHO);

        Position enemyPosition = Position.of(2, 3);
        Piece enemy = new Soldier(TeamType.HAN);
        Board board = new Board(Map.of(enemyPosition, enemy));

        // when & then
        assertThatCode(() -> king.validateMove(position, movePosition, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 점수를 반환한다")
    void getScore() {
        // given
        King king = new King(TeamType.HAN);

        // when & then
        double actual = king.getScore();
        double expected = 0;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("궁 위치를 벗어나면 예외가 발생한다")
    void canMoveAreaTest() {
        // given
        King king = new King(TeamType.CHO);
        Board board = new Board(Map.of());
        Position from = Position.of(2, 3);
        Position to = Position.of(3, 3);

        // when & then
        assertThatThrownBy(() -> king.validateMove(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지정된 지역으로 이동할 수 없는 기물입니다.");
    }
}
