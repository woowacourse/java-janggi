import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import game.Board;
import game.Country;
import game.StartingPosition;
import game.Team;
import org.junit.jupiter.api.Test;
import piece.StaticPieceInitializer;

public class BoardTest {


    @Test
    void 보드를_만들_수_있다() {
        // given
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

        // expected
        assertThatCode(() -> new Board(team1, team2))
                .doesNotThrowAnyException();
    }

    @Test
    void 보드에_추가된_팀들은_서로_달라야_한다() {
        // given
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 개의 장기판의 나라는 서로 달라야 합니다.");
    }

    @Test
    void 팀이_null이면_예외가_발생한다() {
        // given
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = null;

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판은 필수값입니다.");
    }
}
