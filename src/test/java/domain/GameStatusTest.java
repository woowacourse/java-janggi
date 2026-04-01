package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStatusTest {

    @Test
    @DisplayName("플레이어 턴이 한나라(red) 차례라면 초나라(green)로 턴을 바꾸어 준다.")
    void change_turn_from_red_to_green_test() {
        GameStatus gameStaus = GameStatus.RED_PLAYER_TURN;

        GameStatus changeStatus = gameStaus.changePlayerTurn();

        Assertions.assertThat(changeStatus.equals(GameStatus.GREEN_PLAYER_TURN)).isTrue();
    }

    @Test
    @DisplayName("플레이어 턴이 초나라(green) 차례라면 한나라(red)로 턴을 바꾸어 준다.")
    void change_turn_from_green_to_red_test() {
        GameStatus gameStaus = GameStatus.GREEN_PLAYER_TURN;

        GameStatus changeStatus = gameStaus.changePlayerTurn();

        Assertions.assertThat(changeStatus.equals(GameStatus.RED_PLAYER_TURN)).isTrue();
    }

    @Test
    @DisplayName("플레이어 턴이 초나라(green), 한나라(red) 차례가 아니라면 그 상태 그대로 반환한다.")
    void return_same_turn_when_it_is_neither_green_nor_red_test() {
        GameStatus gameStaus = GameStatus.GREEN_TEAM_WIN;

        GameStatus changeStatus = gameStaus.changePlayerTurn();

        Assertions.assertThat(changeStatus.equals(gameStaus)).isTrue();
    }

}
