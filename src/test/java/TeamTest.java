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
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import piece.Piece;
import piece.PieceType;
import piece_initiaizer.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import testUtil.TestConstant;

public class TeamTest {

    @ParameterizedTest(name = "상차림: {0}")
    @EnumSource(StartingPosition.class)
    void 장기판을_초기화할_때_상차림을_선택할_수_있다(StartingPosition startingPosition) {
        // given + when + then
        assertThatCode(() -> new Team(startingPosition, new StaticPieceInitializer(), Country.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 장기판_상태를_받았을_때_불변_맵이다() {
        // given
        final StartingPosition startingPosition = StartingPosition.INNER_ELEPHANT_SETUP;
        final Team team = new Team(startingPosition, new StaticPieceInitializer(), Country.HAN);
        final Map<Position, Piece> result = team.getPieces();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.가, TestConstant.RANK_10),
                new Piece(new Position(PositionFile.마, RANK_5), PieceType.ELEPHANT)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @ParameterizedTest
    @CsvSource({"CHO", "HAN"})
    void 장기판의_나라가_존재한다(Country country) {
        // given
        final StartingPosition startingPosition = StartingPosition.INNER_ELEPHANT_SETUP;
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
        final Team team = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);

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
        final Team team = new Team(StartingPosition.LEFT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);

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
        final Team team = new Team(StartingPosition.INNER_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);

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
        final Team team = new Team(StartingPosition.OUTER_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);

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
        final Team team = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

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
        final Team team = new Team(StartingPosition.LEFT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

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
        final Team team = new Team(StartingPosition.INNER_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

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
        final Team team = new Team(StartingPosition.OUTER_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

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
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.CHO_SOLDIER)
        );
    }

    public static Stream<Arguments> provide초나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.CHO_SOLDIER)
        );
    }

    public static Stream<Arguments> provide초나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.CHO_SOLDIER)
        );
    }

    public static Stream<Arguments> provide초나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_2), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.CHO_SOLDIER)
        );
    }

    public static Stream<Arguments> provide한나라마상마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.HAN_SOLDIER)
        );
    }

    public static Stream<Arguments> provide한나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.HAN_SOLDIER)
        );
    }

    public static Stream<Arguments> provide한나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.HAN_SOLDIER)
        );
    }

    public static Stream<Arguments> provide한나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.라, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.바, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_9), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.나, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.아, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.가, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.다, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.마, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.사, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_7), PieceType.HAN_SOLDIER)
        );
    }
}
