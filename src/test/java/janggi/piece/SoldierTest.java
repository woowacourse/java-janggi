package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class SoldierTest {

    @CsvSource(value = {"GREEN:4:5", "GREEN:5:4", "GREEN:3:4", "RED:4:3"}, delimiterString = ":")
    @ParameterizedTest
    void 병의_정상적인_움직임을_테스트한다(Team team, int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Soldier piece = new Soldier(team);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, team);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"RED:4:5", "GREEN:4:3", "GREEN:5:5", "GREEN:5:3", "GREEN:3:3", "GREEN:3:5"}, delimiterString = ":")
    @ParameterizedTest
    void 병의_이동_규칙을_벗어나_움직일_경우_예외를_발생한다(Team team, int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Soldier piece = new Soldier(team);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, team))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 병의 이동 규칙에 어긋나는 움직임입니다.");
    }

    @CsvSource(value = {"4:5", "3:4", "5:4"}, delimiterString = ":")
    @ParameterizedTest
    void 목적지에_같은_진영의_기물이_있을_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(column, row);
        Soldier piece = new Soldier(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(column,row), new Soldier(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
    }
}
