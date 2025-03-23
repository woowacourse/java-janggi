package player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Janggun;
import piece.Jol;
import pieceProperty.Position;

class PlayerTest {
    @Test
    @DisplayName("플레이어는 국가와 기물들을 가진다")
    void playerTest() {
        Pieces pieces = new Pieces(List.of());

        assertDoesNotThrow(() -> new Player(pieces, CHO));
    }

    @Test
    @DisplayName("왕이 죽었는지 판단 테스트")
    void isKingDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Jol(new Position(5, 5))));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThat(player.isKingDie()).isTrue();
    }

    @Test
    @DisplayName("왕이 살았는지 판단 테스트")
    void isKingNotDieTest() {
        //given
        Pieces pieces = new Pieces(List.of(new Janggun(new Position(5, 5))));
        Player player = new Player(pieces, HAN);

        //when - then
        assertThat(player.isKingDie()).isFalse();
    }

}
