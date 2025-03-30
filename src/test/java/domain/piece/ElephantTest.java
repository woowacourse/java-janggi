package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.TeamType;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    static Stream<Arguments> canMoveElephantEmpty() {
        return Stream.of(
                Arguments.of(Position.of(0, 1)),
                Arguments.of(Position.of(1, 0)),
                Arguments.of(Position.of(5, 0)),
                Arguments.of(Position.of(6, 1)),
                Arguments.of(Position.of(6, 5)),
                Arguments.of(Position.of(5, 6)),
                Arguments.of(Position.of(1, 6)),
                Arguments.of(Position.of(0, 5))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveElephantEmpty(Position endPosition) {
        // given
        Position startPosition = Position.of(3, 3);
        Piece elephant = new Elephant(TeamType.CHO);

        // when & then
        assertThatCode(() -> elephant.validateMove(startPosition, endPosition, new Board(Map.of())))
                .doesNotThrowAnyException();
    }

    static Stream<Arguments> canMoveElephantBlocked() {
        return Stream.of(
                Arguments.of(Position.of(4, 3), new Soldier(TeamType.HAN), Position.of(6, 5)),
                Arguments.of(Position.of(3, 4), new Horse(TeamType.HAN), Position.of(5, 6)),
                Arguments.of(Position.of(2, 3), new King(TeamType.HAN), Position.of(0, 5)),
                Arguments.of(Position.of(3, 2), new Cannon(TeamType.HAN), Position.of(1, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동 경로에 다른 기물이 있으면 예외가 발생한다")
    void canMoveElephantBlocked(Position otherPosition, Piece otherPiece, Position endPosition) {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(otherPosition, otherPiece);
        Board board = new Board(pieces);

        Position startPosition = Position.of(3, 3);
        Piece elephant = new Elephant(TeamType.HAN);

        // when & then
        assertThatThrownBy(() -> elephant.validateMove(startPosition, endPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 기물이 있어 이동할 수 없습니다.");
    }

    static Stream<Arguments> canMoveElephantSameTeam() {
        return Stream.of(
                Arguments.of(Position.of(6, 5), new Soldier(TeamType.HAN), Position.of(6, 5)),
                Arguments.of(Position.of(5, 6), new Horse(TeamType.HAN), Position.of(5, 6)),
                Arguments.of(Position.of(0, 5), new King(TeamType.HAN), Position.of(0, 5)),
                Arguments.of(Position.of(1, 0), new Cannon(TeamType.HAN), Position.of(1, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("도착 지점에 아군이 있으면 예외가 발생한다")
    void canMoveElephantSameTeam(Position teamPosition, Piece teamPiece, Position endPosition) {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(teamPosition, teamPiece);
        Board board = new Board(pieces);

        Position startPosition = Position.of(3, 3);
        Piece elephant = new Elephant(TeamType.HAN);

        // when & then
        assertThatThrownBy(() -> elephant.validateMove(startPosition, endPosition, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    static Stream<Arguments> canMoveElephantOtherTeam() {
        return Stream.of(
                Arguments.of(Position.of(6, 5), new Soldier(TeamType.HAN), Position.of(6, 5)),
                Arguments.of(Position.of(5, 1), new Horse(TeamType.HAN), Position.of(5, 6)),
                Arguments.of(Position.of(0, 1), new King(TeamType.HAN), Position.of(0, 5)),
                Arguments.of(Position.of(5, 0), new Cannon(TeamType.HAN), Position.of(1, 0))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("도착 지점에 적이 있으면 예외가 발생하지 않는다")
    void canMoveElephantOtherTeam(Position otherTeamPosition, Piece otherTeamPiece, Position endPosition) {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(otherTeamPosition, otherTeamPiece);
        Board board = new Board(pieces);

        Position startPosition = Position.of(3, 3);
        Piece elephant = new Elephant(TeamType.CHO);

        // when & then
        assertThatCode(() -> elephant.validateMove(startPosition, endPosition, board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("해당 점수를 반환한다")
    void getScore() {
        // given
        Elephant elephant = new Elephant(TeamType.HAN);

        // when & then
        double actual = elephant.getScore();
        double expected = 3.0;
        assertThat(actual).isEqualTo(expected);
    }
}
