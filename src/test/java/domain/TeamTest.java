package domain;

import domain.position.Position;
import domain.position.PositionFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testUtil.TestConstant;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class TeamTest {

    @ParameterizedTest
    @CsvSource({"마상마상", "상마상마", "상마마상", "마상상마"})
    void 장기판을_초기화할_때_마상배치를_선택한다(StartingPosition startingPosition) {
        // given

        // expected
        assertThatCode(() -> new Team(startingPosition, Country.초나라))
                .doesNotThrowAnyException();
    }

    @Test
    void 초나라와_마상마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final Team team = new Team(StartingPosition.마상마상, Country.초나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

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
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        ));
    }

    @Test
    void 초나라와_상마상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.상마상마;
        final Team team = new Team(startingPosition, Country.초나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        ));
    }

    @Test
    void 초나라와_상마마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.상마마상;
        final Team team = new Team(startingPosition, Country.초나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.마),
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
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        ));
    }

    @Test
    void 초나라와_마상상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, Country.초나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.상),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.마),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Map.entry(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        ));
    }

    @Test
    void 한나라와_마상마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상마상;
        final Team team = new Team(startingPosition, Country.한나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
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
    void 한나라와_상마상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.상마상마;
        final Team team = new Team(startingPosition, Country.한나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.마),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.마),
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
    void 한나라와_상마마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.상마마상;
        final Team team = new Team(startingPosition, Country.한나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.마),
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
    void 한나라와_마상상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, Country.한나라);

        // when
        final Map<Position, PieceType> result =  team.getBoard();

        // then
        assertThat(result).containsExactlyInAnyOrderEntriesOf(Map.ofEntries(
                Map.entry(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Map.entry(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.마),
                Map.entry(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Map.entry(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.상),
                Map.entry(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.마),
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
    void 장기판_상태를_받았을_때_불변_맵이다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, Country.한나라);
        final Map<Position, PieceType> result =  team.getBoard();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
