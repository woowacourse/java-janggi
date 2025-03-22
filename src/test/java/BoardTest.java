import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static testUtil.TestConstant.RANK_5;

import game.Board;
import game.Country;
import game.StartingPosition;
import game.Team;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piece.Piece;
import piece.PieceType;
import piece_initiaizer.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import testUtil.TestConstant;

public class BoardTest {

    public static Stream<Arguments> providePositionAndPieceTypeOfAllPieces() {
        return Stream.of(
                Arguments.of(new Position(PositionFile.FILE_1, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.FILE_2, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.FILE_3, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.FILE_4, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.FILE_6, TestConstant.RANK_1), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.FILE_7, TestConstant.RANK_1), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.FILE_8, TestConstant.RANK_1), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.FILE_9, TestConstant.RANK_1), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.FILE_5, TestConstant.RANK_2), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.FILE_2, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.FILE_8, TestConstant.RANK_3), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.FILE_1, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_3, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_5, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_7, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_9, TestConstant.RANK_4), PieceType.CHO_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_1, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.FILE_2, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.FILE_3, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.FILE_4, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.FILE_6, TestConstant.RANK_10), PieceType.GUARD),
                Arguments.of(new Position(PositionFile.FILE_7, TestConstant.RANK_10), PieceType.HORSE),
                Arguments.of(new Position(PositionFile.FILE_8, TestConstant.RANK_10), PieceType.ELEPHANT),
                Arguments.of(new Position(PositionFile.FILE_9, TestConstant.RANK_10), PieceType.ROOK),
                Arguments.of(new Position(PositionFile.FILE_5, TestConstant.RANK_9), PieceType.GENERAL),
                Arguments.of(new Position(PositionFile.FILE_2, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.FILE_8, TestConstant.RANK_8), PieceType.CANNON),
                Arguments.of(new Position(PositionFile.FILE_1, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_3, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_5, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_7, TestConstant.RANK_7), PieceType.HAN_SOLDIER),
                Arguments.of(new Position(PositionFile.FILE_9, TestConstant.RANK_7), PieceType.HAN_SOLDIER)
        );
    }

    @Test
    void 보드를_만들_수_있다() {
        // given
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);

        // expected
        assertThatCode(() -> new Board(team1, team2))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("providePositionAndPieceTypeOfAllPieces")
    void 보드를_통해_전체_기물_위치를_알_수_있다(Position position, PieceType pieceType) {
        // given
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);
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
        final Team team1 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(StartingPosition.RIGHT_ELEPHANT_SETUP, new StaticPieceInitializer(), Country.HAN);
        final Board board = new Board(team1, team2);
        final Map<Position, Piece> result = board.getBoard();

        // expected
        assertThatThrownBy(() -> result.put(new Position(PositionFile.FILE_1, TestConstant.RANK_1),
                new Piece(new Position(PositionFile.FILE_4, RANK_5), PieceType.HORSE)))
                .isInstanceOf(UnsupportedOperationException.class);
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
