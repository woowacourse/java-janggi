package gameloader;

import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameCheckerTest {

    @DisplayName("해당 게임방이 있는지 체크한다.")
    @Test
    void test() throws SQLException {
        //given
        GameChecker gameChecker = new GameChecker();
        int expected = 100;

        //when
        boolean isGameExist = gameChecker.checkIfGameExists(expected);

        //then
        Assertions.assertThat(isGameExist).isTrue();
    }
}
