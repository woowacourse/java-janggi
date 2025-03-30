package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TurnTest {

    @DisplayName("턴을 교체한다.")
    @Test
    void turnTest1() {
        Turn turn = new Turn(Country.CHO);

        turn.next();

        assertThat(turn.getCountry()).isEqualTo(Country.HAN);
    }
    
}
