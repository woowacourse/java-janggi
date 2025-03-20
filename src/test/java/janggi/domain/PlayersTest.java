package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @DisplayName("중복되지 않은 두명의 플레이어를 저장한다.")
    @Test
    void createPlayersTest() {
        // given
        Player player1 = new Player("test1", Dynasty.HAN);
        Player player2 = new Player("test2", Dynasty.HAN);

        // when, then
        assertThatCode(() -> new Players(List.of(player1, player2)))
                .doesNotThrowAnyException();
    }

    @DisplayName("중복되는 플레이어가 존재하면 예외가 발생한다.")
    @Test
    void createPlayersFail_WhenDuplicatePlayersTest() {
        // given
        Player player1 = new Player("test1", Dynasty.HAN);
        Player player2 = new Player("test1", Dynasty.HAN);

        // when, then
        assertThatThrownBy(() -> new Players(List.of(player1, player2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어는 중복될 수 없습니다.");
    }

    @DisplayName("플레이어가 2명이 아니면 예외가 발생한다.")
    @Test
    void createPlayersFail_WhenPlayersNotTwoTest() {
        // given
        Player player1 = new Player("test1", Dynasty.HAN);

        // when, then
        assertThatThrownBy(() -> new Players(List.of(player1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("플레이어는 두 명이어야 합니다.");
    }

    @DisplayName("나라에 속하는 플레이어를 찾을 수 있다.")
    @Test
    void findDynastyPlayer() {
        //given
        Player player1 = new Player("test1", Dynasty.HAN);
        Player player2 = new Player("test1", Dynasty.CHU);
        Players players = new Players(List.of(player1, player2));

        //when
        Player result = players.findDynastyPlayer(Dynasty.CHU);

        //then
        assertThat(result).isEqualTo(player2);
    }
}