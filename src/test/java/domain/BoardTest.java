package domain;

import domain.position.Position;
import domain.position.PositionFile;
import org.junit.jupiter.api.Test;
import testUtil.TestConstant;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class BoardTest {

    @Test
    void 보드를_만들_수_있다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, Country.초나라);
        final Team team2 = new Team(StartingPosition.마상마상, Country.한나라);

        // expected
        assertThatCode(() -> new Board(team1,team2))
                .doesNotThrowAnyException();
    }

    @Test
    void 보드를_통해_전체_기물_위치를_알_수_있다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, Country.초나라);
        final Team team2 = new Team(StartingPosition.마상마상, Country.한나라);
        final Board board = new Board(team1, team2);

        // when
        final Map<Position, PieceType> result = board.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸),

                Map.entry(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.마),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.마),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.차),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.장),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.포),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.포),
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.졸),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.졸),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.졸),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.졸),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.졸)
        ));
    }

    @Test
    void 전체_기물_위치를_받았을_때_수정할_수_없다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, Country.초나라);
        final Team team2 = new Team(StartingPosition.마상마상, Country.한나라);
        final Board board = new Board(team1, team2);
        final Map<Position, PieceType> result = board.getBoard();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void 보드에_추가된_팀들은_서로_달라야_한다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, Country.초나라);
        final Team team2 = new Team(StartingPosition.마상마상, Country.초나라);

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 개의 장기판의 나라는 서로 달라야 합니다.");
    }

    @Test
    void 팀이_null이면_예외가_발생한다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, Country.초나라);
        final Team team2 = null;

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판은 필수값입니다.");
    }
}
