package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class GeneralTest {

    @CsvSource(value = {"3:0", "3:1", "3:2", "4:0", "4:2", "5:0", "5:1", "5:2"}, delimiterString = ":")
    @ParameterizedTest
    void 초나라_장의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 1);
        Position goal = createPosition(column, row);
        General piece = new General(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"4:0", "3:1", "5:1", "4:2"}, delimiterString = ":")
    @ParameterizedTest
    void 목적지에_같은_진영의_기물이_있을_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 1);
        Position goal = createPosition(column, row);
        Guard piece = new Guard(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(column,row), new Soldier(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
    }

    @CsvSource(value = {"2:2", "2:3", "3:3", "5:3", "6:2", "6:3"}, delimiterString = ":")
    @ParameterizedTest
    void 초나라_궁성_밖_목적지로_이동하려는_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 1);
        Position goal = createPosition(column, row);
        General piece = new General(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 초나라 장의 목적 지점은 하단부 궁성 내 좌표여야 합니다.");
    }

    @CsvSource(value = {"3:2", "4:2", "5:2"}, delimiterString = ":")
    @ParameterizedTest
    void 초나라_장이_한_칸을_초과하여_이동하려는_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(5, 0);
        Position goal = createPosition(column, row);
        General piece = new General(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장의 이동 규칙에 어긋나는 움직임입니다.");
    }

    @CsvSource(value = {"3:7", "3:8", "3:9", "4:7", "4:9", "5:7", "5:8", "5:9"}, delimiterString = ":")
    @ParameterizedTest
    void 한나라_장의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 8);
        Position goal = createPosition(column, row);
        General piece = new General(Team.RED);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.RED);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"2:6", "2:7", "3:6", "5:6", "6:6", "6:7"}, delimiterString = ":")
    @ParameterizedTest
    void 한나라_궁성_밖_목적지로_이동하려는_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 8);
        Position goal = createPosition(column, row);
        General piece = new General(Team.RED);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.RED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한나라 장의 목적 지점은 상단부 궁성 내 좌표여야 합니다.");
    }

    @CsvSource(value = {"3:7", "4:7", "5:7"}, delimiterString = ":")
    @ParameterizedTest
    void 한나라_장이_한_칸을_초과하여_이동하려는_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(5, 9);
        Position goal = createPosition(column, row);
        General piece = new General(Team.RED);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.RED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 장의 이동 규칙에 어긋나는 움직임입니다.");
    }
}
