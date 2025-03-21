package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece_initiaizer.StaticPieceInitializer;
import domain.position.Position;
import domain.position.PositionFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test_util.TestConstant;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static test_util.TestConstant.RANK_5;

public class BoardTest {

    public static Stream<Arguments> providePositionAndPieceTypeOfAllPieces() {
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
                Arguments.of(new Position(PositionFile.자, TestConstant.RANK_4), PieceType.졸),
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

    @Test
    void 보드를_만들_수_있다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.HAN);

        // expected
        assertThatCode(() -> new Board(team1,team2))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("providePositionAndPieceTypeOfAllPieces")
    void 보드를_통해_전체_기물_위치를_알_수_있다(Position position, PieceType pieceType) {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.HAN);
        final Board board = new Board(team1, team2);

        // when
        final Map<Position, Piece> result = board.getBoard();

        // then
        assertThat(result.get(position)).extracting(
                "position", "type"
        ).containsExactly(position, pieceType);
    }

    @Test
    void 전체_기물_위치를_받았을_때_수정할_수_없다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.HAN);
        final Board board = new Board(team1, team2);
        final Map<Position, Piece> result = board.getBoard();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.가, TestConstant.RANK_1), new Piece(new Position(PositionFile.라, RANK_5), PieceType.마)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void 보드에_추가된_팀들은_서로_달라야_한다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("두 개의 장기판의 나라는 서로 달라야 합니다.");
    }

    @Test
    void 팀이_null이면_예외가_발생한다() {
        // given
        final Team team1 = new Team(StartingPosition.마상마상, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = null;

        // expected
        assertThatThrownBy(() -> new Board(team1, team2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장기판은 필수값입니다.");
    }
}
