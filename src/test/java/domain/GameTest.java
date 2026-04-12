package domain;

import domain.board.BasicBoardInitializer;
import domain.state.Side;
import domain.board.formation.OutsideMaFormation;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GameTest {

    BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer(new OutsideMaFormation(Side.HAN), new OutsideMaFormation(Side.CHU));

    @Test
    @DisplayName("해당 좌표가 비어있으면 시작 좌표로 선택할 수 없다.")
    void getValidatedStart_Position_Empty_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(8, 1);

        // when - then
        assertThatThrownBy(() -> game.validateMoveable(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 좌표가 상대 기물이라면 시작 좌표로 선택할 수 없다.")
    void getValidatedStart_Position_Opponent_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(0, 0);

        // when - then
        assertThatThrownBy(() -> game.validateMoveable(position))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아군 기물을 시작 좌표로 선택할 수 있다.")
    void getValidatedStart_Position_True_Test() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position position = new Position(9, 0);

        // when - then
        assertDoesNotThrow(() -> game.validateMoveable(position));
    }

    @Test
    @DisplayName("기물을 시작 좌표에서 도착 좌표로 이동시킨다.")
    void movePieceTest() {
        // given
        Game game = new Game(basicBoardInitializer);
        Position start = new Position(9, 0);
        Position destination = new Position(8, 0);

        // when - then
        assertDoesNotThrow(() -> game.movePiece(start, destination));
    }

    @Test
    @DisplayName("초나라의 시작 기물 점수는 72 점이다.")
    void calculateScoreChuSideTest() {
        // given
        Game game = new Game(basicBoardInitializer);

        // when - then
        assertThat(game.calculateScore(Side.CHU)).isEqualTo(72);
    }

    @Test
    @DisplayName("한나라의 시작 기물 점수는 73.5 점이다. (후공 보너스 1.5)")
    void calculateScoreHanSideTest() {
        // given
        Game game = new Game(basicBoardInitializer);

        // when - then
        assertThat(game.calculateScore(Side.HAN)).isEqualTo(73.5);
    }
}
