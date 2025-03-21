package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece_initiaizer.StaticPieceInitializer;
import domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static domain.piece.PieceType.*;
import static domain.position.PositionFile.*;
import static domain.position.PositionRank.*;
import static org.assertj.core.api.Assertions.*;

public class TeamTest {

    @ParameterizedTest
    @CsvSource({"마상마상", "상마상마", "상마마상", "마상상마"})
    void 장기판을_초기화할_때_마상배치를_선택한다(StartingPosition startingPosition) {
        // given

        // expected
        assertThatCode(() -> new Team(startingPosition, new StaticPieceInitializer(), Country.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 장기판_상태를_받았을_때_불변_맵이다() {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, new StaticPieceInitializer(), Country.HAN);
        final Map<Position, Piece> result =  team.getPieces();

        // expected
        assertThatThrownBy(() -> result.put(new Position(FILE_1, RANK_10), new Piece(new Position(FILE_5, RANK_5), 상)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @ParameterizedTest
    @CsvSource({"HAN","CHO"})
    void 장기판의_나라가_존재한다(Country country) {
        // given
        final StartingPosition startingPosition = StartingPosition.마상상마;
        final Team team = new Team(startingPosition, new StaticPieceInitializer(), country);

        // expected
        assertThat(team).extracting("country").isEqualTo(country);
    }

    @ParameterizedTest
    @MethodSource("provide초나라마상마상PositionAndPieceTypeOfAllPieces")
    void 초나라와_마상마상으로_장기판이_초기화됐을_때_말들이_올바른_위치에_배치된다(Position position, PieceType pieceType) {
        // given
        final Team team = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.상마상마, new StaticPieceInitializer(), Country.CHO);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.마상상마, new StaticPieceInitializer(), Country.CHO);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.상마마상, new StaticPieceInitializer(), Country.CHO);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.HAN);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.상마상마, new StaticPieceInitializer(), Country.HAN);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.마상상마, new StaticPieceInitializer(), Country.HAN);

        // when
        final Map<Position, Piece> result =  team.getPieces();

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
        final Team team = new Team(StartingPosition.상마마상, new StaticPieceInitializer(), Country.HAN);

        // when
        final Map<Position, Piece> result =  team.getPieces();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(
                position, pieceType
        );
    }

    public static Stream<Arguments> provide초나라마상마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_1), 차),
                Arguments.of(new Position(FILE_2, RANK_1), 마),
                Arguments.of(new Position(FILE_3, RANK_1), 상),
                Arguments.of(new Position(FILE_4, RANK_1), 사),
                Arguments.of(new Position(FILE_6, RANK_1), 사),
                Arguments.of(new Position(FILE_7, RANK_1), 마),
                Arguments.of(new Position(FILE_8, RANK_1), 상),
                Arguments.of(new Position(FILE_9, RANK_1), 차),
                Arguments.of(new Position(FILE_5, RANK_2), 장),
                Arguments.of(new Position(FILE_2, RANK_3), 포),
                Arguments.of(new Position(FILE_8, RANK_3), 포),
                Arguments.of(new Position(FILE_1, RANK_4), 졸),
                Arguments.of(new Position(FILE_3, RANK_4), 졸),
                Arguments.of(new Position(FILE_5, RANK_4), 졸),
                Arguments.of(new Position(FILE_7, RANK_4), 졸),
                Arguments.of(new Position(FILE_9, RANK_4), 졸)
        );
    }

    public static Stream<Arguments> provide초나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_1), 차),
                Arguments.of(new Position(FILE_2, RANK_1), 상),
                Arguments.of(new Position(FILE_3, RANK_1), 마),
                Arguments.of(new Position(FILE_4, RANK_1), 사),
                Arguments.of(new Position(FILE_6, RANK_1), 사),
                Arguments.of(new Position(FILE_7, RANK_1), 상),
                Arguments.of(new Position(FILE_8, RANK_1), 마),
                Arguments.of(new Position(FILE_9, RANK_1), 차),
                Arguments.of(new Position(FILE_5, RANK_2), 장),
                Arguments.of(new Position(FILE_2, RANK_3), 포),
                Arguments.of(new Position(FILE_8, RANK_3), 포),
                Arguments.of(new Position(FILE_1, RANK_4), 졸),
                Arguments.of(new Position(FILE_3, RANK_4), 졸),
                Arguments.of(new Position(FILE_5, RANK_4), 졸),
                Arguments.of(new Position(FILE_7, RANK_4), 졸),
                Arguments.of(new Position(FILE_9, RANK_4), 졸)
        );
    }

    public static Stream<Arguments> provide초나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_1), 차),
                Arguments.of(new Position(FILE_2, RANK_1), 상),
                Arguments.of(new Position(FILE_3, RANK_1), 마),
                Arguments.of(new Position(FILE_4, RANK_1), 사),
                Arguments.of(new Position(FILE_6, RANK_1), 사),
                Arguments.of(new Position(FILE_7, RANK_1), 마),
                Arguments.of(new Position(FILE_8, RANK_1), 상),
                Arguments.of(new Position(FILE_9, RANK_1), 차),
                Arguments.of(new Position(FILE_5, RANK_2), 장),
                Arguments.of(new Position(FILE_2, RANK_3), 포),
                Arguments.of(new Position(FILE_8, RANK_3), 포),
                Arguments.of(new Position(FILE_1, RANK_4), 졸),
                Arguments.of(new Position(FILE_3, RANK_4), 졸),
                Arguments.of(new Position(FILE_5, RANK_4), 졸),
                Arguments.of(new Position(FILE_7, RANK_4), 졸),
                Arguments.of(new Position(FILE_9, RANK_4), 졸)
        );
    }

    public static Stream<Arguments> provide초나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_1), 차),
                Arguments.of(new Position(FILE_2, RANK_1), 마),
                Arguments.of(new Position(FILE_3, RANK_1), 상),
                Arguments.of(new Position(FILE_4, RANK_1), 사),
                Arguments.of(new Position(FILE_6, RANK_1), 사),
                Arguments.of(new Position(FILE_7, RANK_1), 상),
                Arguments.of(new Position(FILE_8, RANK_1), 마),
                Arguments.of(new Position(FILE_9, RANK_1), 차),
                Arguments.of(new Position(FILE_5, RANK_2), 장),
                Arguments.of(new Position(FILE_2, RANK_3), 포),
                Arguments.of(new Position(FILE_8, RANK_3), 포),
                Arguments.of(new Position(FILE_1, RANK_4), 졸),
                Arguments.of(new Position(FILE_3, RANK_4), 졸),
                Arguments.of(new Position(FILE_5, RANK_4), 졸),
                Arguments.of(new Position(FILE_7, RANK_4), 졸),
                Arguments.of(new Position(FILE_9, RANK_4), 졸)
        );
    }

    public static Stream<Arguments> provide한나라마상마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_10), 차),
                Arguments.of(new Position(FILE_2, RANK_10), 마),
                Arguments.of(new Position(FILE_3, RANK_10), 상),
                Arguments.of(new Position(FILE_4, RANK_10), 사),
                Arguments.of(new Position(FILE_6, RANK_10), 사),
                Arguments.of(new Position(FILE_7, RANK_10), 마),
                Arguments.of(new Position(FILE_8, RANK_10), 상),
                Arguments.of(new Position(FILE_9, RANK_10), 차),
                Arguments.of(new Position(FILE_5, RANK_9), 장),
                Arguments.of(new Position(FILE_2, RANK_8), 포),
                Arguments.of(new Position(FILE_8, RANK_8), 포),
                Arguments.of(new Position(FILE_1, RANK_7), 병),
                Arguments.of(new Position(FILE_3, RANK_7), 병),
                Arguments.of(new Position(FILE_5, RANK_7), 병),
                Arguments.of(new Position(FILE_7, RANK_7), 병),
                Arguments.of(new Position(FILE_9, RANK_7), 병)
        );
    }

    public static Stream<Arguments> provide한나라상마상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_10), 차),
                Arguments.of(new Position(FILE_2, RANK_10), 상),
                Arguments.of(new Position(FILE_3, RANK_10), 마),
                Arguments.of(new Position(FILE_4, RANK_10), 사),
                Arguments.of(new Position(FILE_6, RANK_10), 사),
                Arguments.of(new Position(FILE_7, RANK_10), 상),
                Arguments.of(new Position(FILE_8, RANK_10), 마),
                Arguments.of(new Position(FILE_9, RANK_10), 차),
                Arguments.of(new Position(FILE_5, RANK_9), 장),
                Arguments.of(new Position(FILE_2, RANK_8), 포),
                Arguments.of(new Position(FILE_8, RANK_8), 포),
                Arguments.of(new Position(FILE_1, RANK_7), 병),
                Arguments.of(new Position(FILE_3, RANK_7), 병),
                Arguments.of(new Position(FILE_5, RANK_7), 병),
                Arguments.of(new Position(FILE_7, RANK_7), 병),
                Arguments.of(new Position(FILE_9, RANK_7), 병)
        );
    }

    public static Stream<Arguments> provide한나라마상상마PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_10), 차),
                Arguments.of(new Position(FILE_2, RANK_10), 마),
                Arguments.of(new Position(FILE_3, RANK_10), 상),
                Arguments.of(new Position(FILE_4, RANK_10), 사),
                Arguments.of(new Position(FILE_6, RANK_10), 사),
                Arguments.of(new Position(FILE_7, RANK_10), 상),
                Arguments.of(new Position(FILE_8, RANK_10), 마),
                Arguments.of(new Position(FILE_9, RANK_10), 차),
                Arguments.of(new Position(FILE_5, RANK_9), 장),
                Arguments.of(new Position(FILE_2, RANK_8), 포),
                Arguments.of(new Position(FILE_8, RANK_8), 포),
                Arguments.of(new Position(FILE_1, RANK_7), 병),
                Arguments.of(new Position(FILE_3, RANK_7), 병),
                Arguments.of(new Position(FILE_5, RANK_7), 병),
                Arguments.of(new Position(FILE_7, RANK_7), 병),
                Arguments.of(new Position(FILE_9, RANK_7), 병)
        );
    }

    public static Stream<Arguments> provide한나라상마마상PositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(FILE_1, RANK_10), 차),
                Arguments.of(new Position(FILE_2, RANK_10), 상),
                Arguments.of(new Position(FILE_3, RANK_10), 마),
                Arguments.of(new Position(FILE_4, RANK_10), 사),
                Arguments.of(new Position(FILE_6, RANK_10), 사),
                Arguments.of(new Position(FILE_7, RANK_10), 마),
                Arguments.of(new Position(FILE_8, RANK_10), 상),
                Arguments.of(new Position(FILE_9, RANK_10), 차),
                Arguments.of(new Position(FILE_5, RANK_9), 장),
                Arguments.of(new Position(FILE_2, RANK_8), 포),
                Arguments.of(new Position(FILE_8, RANK_8), 포),
                Arguments.of(new Position(FILE_1, RANK_7), 병),
                Arguments.of(new Position(FILE_3, RANK_7), 병),
                Arguments.of(new Position(FILE_5, RANK_7), 병),
                Arguments.of(new Position(FILE_7, RANK_7), 병),
                Arguments.of(new Position(FILE_9, RANK_7), 병)
        );
    }
}
