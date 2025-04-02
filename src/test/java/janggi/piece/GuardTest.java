package janggi.piece;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import janggi.moving.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {
    @DisplayName("초나라_사가_궁성_밖으로_이동하면_예외를_발생한다")
    @Test
    void aa() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 2);
        Position goal = createPosition(4, 3);
        Path path = new Path(List.of(start, goal));
        Piece piece = PieceCreator.create(Team.GREEN, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validatePath(board, path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 궁성을 벗어날 수 없습니다.");
    }

    @DisplayName("한나라_사가_궁성_밖으로_이동하면_예외를_발생한다")
    @Test
    void aaa() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(3, 7);
        Position goal = createPosition(2, 7);
        Path path = new Path(List.of(start, goal));
        Piece piece = PieceCreator.create(Team.RED, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validatePath(board, path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사는 궁성을 벗어날 수 없습니다.");
    }

    @DisplayName("초나라_사가_궁성_내_선을_따라_대각으로_한칸_움직일_수_있다")
    @CsvSource(value = {"3:0:4:1", "3:2:4:1", "5:0:4:1", "5:2:4:1", "4:1:3:0", "4:1:3:2", "4:1:5:0", "4:1:5:2"},
            delimiterString = ":")
    @ParameterizedTest
    void validateMovable(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(Team.GREEN, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }

    @DisplayName("초나라_사가_두칸_이동_시_예외를_발생한다")
    @CsvSource(value = {"3:0:5:2", "3:0:5:0", "3:0:3:2", "3:2:5:0", "3:2:3:0", "3:2:5:2", "5:0:3:2", "5:0:3:0",
            "5:0:5:2", "5:2:3:0", "5:2:3:2", "5:2:5:0"}, delimiterString = ":")
    @ParameterizedTest
    void validateMovable11(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(Team.GREEN, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }

    @DisplayName("한나라_사가_궁성_내_선을_따라_대각으로_한칸_움직일_수_있다")
    @CsvSource(value = {"3:9:4:8", "3:7:4:8", "5:9:4:8", "5:7:4:8", "4:8:3:9", "4:8:3:7", "4:8:5:9", "4:8:5:7"},
            delimiterString = ":")
    @ParameterizedTest
    void validateMovable2(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(Team.RED, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }

    @DisplayName("한나라_사가_두칸_이동_시_예외를_발생한다")
    @CsvSource(value = {"3:9:5:7", "3:9:5:9", "3:9:3:7", "3:7:5:9", "3:7:3:9", "3:7:5:7", "5:9:3:7", "5:9:3:9",
            "5:9:5:7", "5:7:3:9", "5:7:3:7", "5:7:5:9"}, delimiterString = ":")
    @ParameterizedTest
    void validateMovable22(int startColumn, int startRow, int goalColumn, int goalRow) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(Team.RED, PieceType.GUARD);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }
}
