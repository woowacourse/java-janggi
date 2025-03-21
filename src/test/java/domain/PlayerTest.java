package domain;

import domain.piece.Horse;
import domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @DisplayName("플레이어와 장기말의 팀을 비교한다")
    @Test
    void test() {
        // given
        Piece piece = new Horse(Team.RED);
        Player player = new Player("짱구", Team.RED);
        // when
        boolean result = player.isTeam(piece);
        // then
        Assertions.assertThat(result).isTrue();
    }

}