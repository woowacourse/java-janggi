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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

class CannonTest {

    @Test
    void 포가_포를_넘어갈_경우_예외를_발생한다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 3);
        Position goal = createPosition(4, 5);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Cannon(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when && then
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
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Soldier(Team.GREEN));
        initialBoard.put(createPosition(4,5), new Soldier(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when && then
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
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        Board board = new Board(() -> initialBoard);

        // when && then
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
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(createPosition(4,4), new Soldier(Team.GREEN));
        initialBoard.put(createPosition(4,6), new Cannon(Team.RED));
        Board board = new Board(() -> initialBoard);

        // when && then
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
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4,5), new Soldier(Team.GREEN));
        initialBoard.put(new Position(4,3), new Soldier(Team.GREEN));
        initialBoard.put(new Position(3,4), new Soldier(Team.GREEN));
        initialBoard.put(new Position(5,4), new Soldier(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"2:2", "2:6", "5:3", "6:2", "6:6"}, delimiterString = ":")
    @ParameterizedTest
    void 포의_이동_규칙을_벗어나_움직일_경우_예외를_발생한다(int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 4);
        Position goal = createPosition(column, row);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(3,3), new Soldier(Team.RED));
        initialBoard.put(new Position(3,5), new Soldier(Team.RED));
        initialBoard.put(new Position(5,3), new Soldier(Team.RED));
        initialBoard.put(new Position(5,5), new Soldier(Team.RED));

        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포의 이동 규칙에 어긋나는 움직임입니다.");
    }

    @CsvSource(value = {"3:7:5:9", "5:7:3:9", "3:9:5:7", "5:9:3:7"}, delimiterString = ":")
    @ParameterizedTest
    void 상단부_궁성_내부에서_대각이동을_한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 8), new General(Team.RED));
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:9:5:5", "3:9:6:6", "3:9:7:7", "5:9:1:7", "5:9:2:6", "5:9:3:5"}, delimiterString = ":")
    @ParameterizedTest
    void 상단부_궁성에서_대각이동을_할_때_목적지가_궁성_밖이면_예외를_발생한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 8), new General(Team.RED));
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포의 이동 규칙에 어긋나는 움직임입니다.");
    }

    @Test
    void 상단부_궁성에서_직선이동을_할_때_궁성_밖으로_나갈_수_있다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 9);
        Position goal = createPosition(4, 4);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 8), new General(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:0:5:2", "5:0:3:2", "3:2:5:0", "5:2:3:0"}, delimiterString = ":")
    @ParameterizedTest
    void 하단부_궁성_내부에서_대각이동을_한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 1), new General(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }

    @CsvSource(value = {"3:0:5:4", "3:0:6:3", "3:0:7:2", "5:0:1:2", "5:0:2:3", "5:0:3:4"}, delimiterString = ":")
    @ParameterizedTest
    void 하단부_궁성에서_대각이동을_할_때_목적지가_궁성_밖이면_예외를_발생한다(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 1), new General(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when && then
        assertThatThrownBy(() -> board.movePiece(start, goal, Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 포의 이동 규칙에 어긋나는 움직임입니다.");
    }

    @Test
    void 하단부_궁성에서_직선이동을_할_때_궁성_밖으로_나갈_수_있다() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = new Position(4, 0);
        Position goal = createPosition(4, 5);
        Cannon piece = new Cannon(Team.GREEN);

        initialBoard.put(start, piece);
        initialBoard.put(new Position(4, 1), new General(Team.GREEN));
        Board board = new Board(() -> initialBoard);

        // when
        board.movePiece(start, goal, Team.GREEN);

        // then
        assertThat(board).extracting("board")
                .asInstanceOf(MAP)
                .containsEntry(goal, piece);
    }
}
