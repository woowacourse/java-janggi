package janggi.piece;

import janggi.Team;
import janggi.board.Board;
import janggi.board.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class CanonTest {
    @Test
    void 포가_포를_넘어갈_경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 3);
        Position goal = createPosition(4, 5);
        Canon piece = new Canon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Canon(Team.GREEN));
        Board board = new Board(initialBoard);

        // when
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
    }

    @Test
    void 포가_기물_두_개_이상을_뛰어_넘을경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 3);
        Position goal = createPosition(4, 6);
        Canon piece = new Canon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Soldier(Team.GREEN));
        initialBoard.put(createPosition(4,5), new Soldier(Team.GREEN));
        Board board = new Board(initialBoard);

        // when
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
    }

    @Test
    void 포가_아무_기물도_뛰어_넘지_않을경우_예외가_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 3);
        Position goal = createPosition(4, 6);
        Canon piece = new Canon(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // when
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 다른 기물 1개를 넘어가야 합니다.");
    }

    @Test
    void 포가_포를_공격할_경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 3);
        Position goal = createPosition(4, 6);
        Canon piece = new Canon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Soldier(Team.GREEN));
        initialBoard.put(createPosition(4,6), new Canon(Team.RED));
        Board board = new Board(initialBoard);

        // when
        // then
        Assertions.assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포는 포를 잡을 수 없습니다.");
    }

    @CsvSource(value = {"4:6", "4:2", "6:4", "2:4"}, delimiterString = ":")
    @ParameterizedTest
    void 포의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Canon piece = new Canon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4,5), new Soldier(Team.GREEN));
        initialBoard.put(new Position(4,3), new Soldier(Team.GREEN));
        initialBoard.put(new Position(3,4), new Soldier(Team.GREEN));
        initialBoard.put(new Position(5,4), new Soldier(Team.GREEN));
        Board board = new Board(initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }
}
