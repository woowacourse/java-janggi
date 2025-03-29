package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class ChariotTest {

    @CsvSource(value = {"4:0", "4:1", "4:2", "4:3", "4:5", "4:6", "4:7", "4:8", "4:9",
            "0:4", "1:4", "2:4", "3:4", "5:4", "6:4", "7:4", "8:4"}, delimiterString = ":")
    @ParameterizedTest
    void 차의_정상적인_움직임을_테스트한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @Test
    void 이동_경로에_다른_기물이_있을_경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(4, 9);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,6), new Chariot(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 경로 중 특정 위치에 다른 기물이 있어 해당 기물을 목적지로 이동할 수 없습니다.");
    }

    @CsvSource(value = {"5:5", "3:6", "3:3", "5:3"}, delimiterString = ":")
    @ParameterizedTest
    void 차의_이동이_상하좌우_일직선이_아닌경우_예외가_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 차는 상하좌우 일직선으로만 이동 가능합니다.");
    }

    @CsvSource(value = {"3:7:4:8", "3:7:5:9", "5:7:3:9", "5:7:4:8", "3:9:4:8", "3:9:5:7", "5:9:3:7", "5:9:4:8"}, delimiterString = ":")
    @ParameterizedTest
    void 상단부_궁성_내부에서_대각이동을_한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        Assertions.assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:9:6:6", "5:9:2:6"}, delimiterString = ":")
    @ParameterizedTest
    void 상단부_궁성에서_대각이동을_할_때_목적지가_궁성_밖이면_예외를_발생한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 상하좌우 일직선으로만 이동 가능합니다.");
    }

    @Test
    void 상단부_궁성에서_직선이동을_할_때_궁성_밖으로_나갈_수_있다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 9);
        Position goal = createPosition(4, 4);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        Assertions.assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:0:4:1", "3:0:5:2", "5:0:3:2", "5:0:4:1", "3:2:4:1", "3:2:5:0", "5:2:3:0", "5:2:4:1"}, delimiterString = ":")
    @ParameterizedTest
    void 하단부_궁성_내부에서_대각이동을_한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        Assertions.assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:0:6:3", "5:0:2:3"}, delimiterString = ":")
    @ParameterizedTest
    void 하단부_궁성에서_대각이동을_할_때_목적지가_궁성_밖이면_예외를_발생한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 차는 상하좌우 일직선으로만 이동 가능합니다.");
    }

    @Test
    void 하단부_궁성에서_직선이동을_할_때_궁성_밖으로_나갈_수_있다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 0);
        Position goal = createPosition(4, 5);
        Chariot piece = new Chariot(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        Assertions.assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }
}
