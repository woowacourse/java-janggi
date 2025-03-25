package game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import piece.PieceType;
import piece.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import testUtil.TestConstant;

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

    @Test
    void move_메서드는_현재_턴_플레이어의_말을_옮긴다() {
        // given
        final Team cho = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team han = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);
        final Board board = new Board(cho, han);

        Position from = new Position(PositionFile.FILE_5, TestConstant.RANK_4);
        Position to = new Position(PositionFile.FILE_5, TestConstant.RANK_5);

        // when
        board.move(from, to);

        // then
        assertThat(cho.getPieces()).doesNotContainKey(from);
        assertThat(cho.getPieces().get(to)).extracting("type").isEqualTo(PieceType.CHO_SOLDIER);
    }

}
