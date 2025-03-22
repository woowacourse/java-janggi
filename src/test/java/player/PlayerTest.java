package player;

import static org.junit.jupiter.api.Assertions.*;
import static player.Nation.CHO;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayerTest {
    @Test
    @DisplayName("플레이어는 국가와 기물들을 가진다")
    void playerTest() {
        Pieces pieces = new Pieces(List.of());

        assertDoesNotThrow(() -> new Player(pieces, CHO));
    }

}
