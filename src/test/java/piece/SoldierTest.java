package piece;

import static position.PositionFixtures.D0;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoldierTest {
    @Test
    @DisplayName("장기 말의 종류에는 사가 있다.")
    void createPalaceTest() {
        Piece soldier = new Soldier(D0);
    }
}
