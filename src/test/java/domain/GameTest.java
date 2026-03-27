package domain;

import domain.board.BasicBoardInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GameTest {

    private final BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer();

    @Test
    @DisplayName("목적지에 아군 기물이 존재하면 False를 반환한다.")
    void isAvailableDestination_Friendly_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position destination = new Position(9, 0);

        // when - then
        assertThat(game.isAvailableDestination(destination)).isFalse();
    }

    @Test
    @DisplayName("목적지에 상대 기물이 존재하면 True를 반환한다.")
    void isAvailableDestination_Opponent_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position destination = new Position(0, 0);

        // when - then
        assertThat(game.isAvailableDestination(destination)).isTrue();
    }

    @Test
    @DisplayName("목적지가 비어있으면 True를 반환한다.")
    void isAvailableDestination_Empty_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position destination = new Position(4, 4);

        // when - then
        assertThat(game.isAvailableDestination(destination)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표에 상대 기물이 존재하면 True를 반환한다.")
    void isOpponentPiece_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(0, 0);

        // when - then
        assertThat(game.isOpponentPiece(position)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 False를 반환한다.")
    void isOpponentPiece_Empty_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(4, 4);

        // when - then
        assertThat(game.isOpponentPiece(position)).isFalse();
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 False를 반환한다.")
    void isOpponentPiece_Friendly_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(9, 0);

        // when - then
        assertThat(game.isOpponentPiece(position)).isFalse();
    }

    @Test
    @DisplayName("해당 좌표가 포이면 True를 반환한다.")
    void isCannon_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(7, 1);

        // when - then
        assertThat(game.isCannon(position)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표가 포가 아니면 False를 반환한다.")
    void isCannon_False_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(8, 1);

        // when - then
        assertThat(game.isCannon(position)).isFalse();
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 False를 반환한다.")
    void isNotEmpty_False_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(8, 1);

        // when - then
        assertThat(game.isNotEmpty(position)).isFalse();
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 False를 반환한다.")
    void isNotEmpty_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(7, 1);

        // when - then
        assertThat(game.isNotEmpty(position)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 시작 좌표로 선택할 수 없다.")
    void validateStartPosition_Empty_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(8, 1);

        // when - then
        assertThatThrownBy(() -> game.validateStartPosition(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 좌표가 상대 기물이라면 시작 좌표로 선택할 수 없다.")
    void validateStartPosition_Opponent_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(0,0);

        // when - then
        assertThatThrownBy(() -> game.validateStartPosition(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아군 기물을 시작 좌표로 선택할 수 있다.")
    void validateStartPosition_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(7,1);

        // when - then
        assertDoesNotThrow(() -> game.validateStartPosition(position));
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position start = new Position(10, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> game.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position start = new Position(4, 9);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> game.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외에 대한 이동은 발생한다.")
    void boardRange_Negative_Error_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position start = new Position(-1, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> game.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동을 마치면 턴이 변경된다.")
    void changeTurnTest() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position start = new Position(6, 0);
        Position destination = new Position(6, 1);

        // when
        game.move(start, destination);

        // then
        assertThat(game.getTurn()).isEqualTo(Side.HAN);
    }
}
