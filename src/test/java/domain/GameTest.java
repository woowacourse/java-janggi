package domain;

import domain.board.BasicBoardInitializer;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GameTest {

    private final BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer();


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
        Position position = new Position(0, 0);

        // when - then
        assertThatThrownBy(() -> game.validateStartPosition(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아군 기물을 시작 좌표로 선택할 수 있다.")
    void validateStartPosition_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(7, 1);

        // when - then
        assertDoesNotThrow(() -> game.validateStartPosition(position));
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(10, 4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(4, 9))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외에 대한 이동은 발생한다.")
    void boardRange_Negative_Error_Test() {
        // when - then
        assertThatThrownBy(() -> new Position(-1, 4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("초기 상태에서 초나라의 점수를 계산한다.")
    void calculateScore_Chu_Test() {
        // given
        Game game = new Game(basicBoardInitializer);

        // when
        double score = game.calculateScore(Side.CHU);

        // then
        assertThat(score).isEqualTo(72);
    }

    @Test
    @DisplayName("초기 상태에서 한나라의 점수를 계산한다.")
    void calculateScore_Han_Test() {
        // given
        Game game = new Game(basicBoardInitializer);

        // when
        double score = game.calculateScore(Side.HAN);

        // then
        assertThat(score).isEqualTo(73.5);
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
