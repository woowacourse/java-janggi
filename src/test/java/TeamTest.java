import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testUtil.TestConstant.RANK_5;

import game.Country;
import game.StartingPosition;
import game.Team;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import piece.Piece;
import piece.PieceType;
import piece_initiaizer.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import testUtil.TestConstant;

public class TeamTest {

    @ParameterizedTest
    @CsvSource({"마상마상", "상마상마", "상마마상", "마상상마"})
    void 장기판을_초기화할_때_마상배치를_선택한다(StartingPosition startingPosition) {
        // given

        // expected
        assertThatCode(() -> new Team(startingPosition, new StaticPieceInitializer(), Country.초나라))
                .doesNotThrowAnyException();
    }

    @Test
    void 장기판_상태를_받았을_때_불변_맵이다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, new StaticPieceInitializer(), Country.한나라);
        final Map<Position, Piece> result = team.getPieces();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.가, TestConstant.RANK_10),
                new Piece(new Position(PositionFile.마, RANK_5), PieceType.상)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @ParameterizedTest
    @CsvSource({"한나라", "초나라"})
    void 장기판의_나라가_존재한다(Country country) {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, new StaticPieceInitializer(), country);

        // when
        final Country result = team.getCountry();

        // then
        assertThat(result).isEqualTo(country);

    }

    @ParameterizedTest
    @MethodSource("provide초나라마상마상PositionAndPieceTypeOfAllPieces")
    void 초나라와_마상마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.초나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide초나라상마상마PositionAndPieceTypeOfAllPieces")
    void 초나라와_상마상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.상마상마, new StaticPieceInitializer(), Country.초나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide초나라마상상마PositionAndPieceTypeOfAllPieces")
    void 초나라와_마상상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.마상상마, new StaticPieceInitializer(), Country.초나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide초나라상마마상PositionAndPieceTypeOfAllPieces")
    void 초나라와_상마마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.상마마상, new StaticPieceInitializer(), Country.초나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide한나라마상마상PositionAndPieceTypeOfAllPieces")
    void 한나라와_마상마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.한나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide한나라상마상마PositionAndPieceTypeOfAllPieces")
    void 한나라와_상마상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.상마상마, new StaticPieceInitializer(), Country.한나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide한나라마상상마PositionAndPieceTypeOfAllPieces")
    void 한나라와_마상상마으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.마상상마, new StaticPieceInitializer(), Country.한나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    @ParameterizedTest
    @MethodSource("provide한나라상마마상PositionAndPieceTypeOfAllPieces")
    void 한나라와_상마마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.상마마상, new StaticPieceInitializer(), Country.한나라);

        // when
        final Map<Position, Piece> result = team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    public static Stream<Arguments> provide초나라마상마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        );
    }

    public static Stream<Arguments> provide초나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        );
    }

    public static Stream<Arguments> provide초나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        );
    }

    public static Stream<Arguments> provide초나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.상),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.마),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.졸),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸)
        );
    }

    public static Stream<Arguments> provide한나라마상마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.병)
        );
    }

    public static Stream<Arguments> provide한나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.병)
        );
    }

    public static Stream<Arguments> provide한나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.병)
        );
    }

    public static Stream<Arguments> provide한나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.사),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.마),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.상),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.차),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.장),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.포),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.병),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.병)
        );
    }
}
