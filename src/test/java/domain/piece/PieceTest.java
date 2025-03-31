package domain.piece;

import domain.Team;
import domain.player.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @DisplayName("두 기물의 팀이 같은지 판단한다")
    @Test
    void test() {
        // given
        Player player = new Player(1, "짱구", Team.RED);

        Piece piece1 = new Horse(player, 5);
        Piece piece2 = new Horse(player, 5);

        // when
        boolean actual = piece1.comparePlayer(piece2);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
