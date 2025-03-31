package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    @DisplayName("턴을 교체한다.")
    @Test
    void turnTest1() {
        Game game = new Game("game1", Country.CHO);

        game.next();

        assertThat(game.getCountry()).isEqualTo(Country.HAN);
    }

}
