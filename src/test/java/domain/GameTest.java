package domain;

import domain.board.BasicBoardInitializer;
import domain.state.Side;
import domain.board.formation.OutsideMaFormation;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
