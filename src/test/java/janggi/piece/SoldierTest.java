package janggi.piece;

import janggi.PieceType;
import janggi.Team;
import janggi.board.Board;
import janggi.board.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {
    @DisplayName("졸병의_정상적인_움직임을_테스트한다")
    @CsvSource(value = {"GREEN:4:5", "GREEN:5:4", "GREEN:3:4", "RED:4:3", "RED:5:4", "RED:3:4",}, delimiterString = ":")
    @ParameterizedTest
    void 졸병의_정상적인_움직임을_테스트한다(Team team, int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(column, row);
        Piece piece = PieceCreator.create(team, PieceType.SOLDIER);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }

    @DisplayName("졸병이_뒤로_움직이면_예외를_발생한다")
    @CsvSource(value = {"RED:4:5", "GREEN:4:3"}, delimiterString = ":")
    @ParameterizedTest
    void should_ThrowException_WhenMoveBackward(Team team, int column, int row) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(column, row);
        Piece piece = PieceCreator.create(team, PieceType.SOLDIER);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }

    @DisplayName("졸병이_궁성_내에서_대각으로_뒤를_향해_움직이면_예외를_발생한다")
    @CsvSource(value = {"RED:3:0:4:1", "RED:4:1:5:2", "RED:5:0:4:1", "RED:4:1:3:2", "GREEN:3:9:4:8",
            "GREEN:4:8:5:7", "GREEN:5:9:4:8", "GREEN:4:8:3:7"}, delimiterString = ":")
    @ParameterizedTest
    void should_ThrowException_WhenMoveDiagonalBackward(
            Team team,
            int startColumn,
            int startRow,
            int goalColumn,
            int goalRow
    ) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(team, PieceType.SOLDIER);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }

    @DisplayName("졸병은_궁성_내에서_앞을_향해_대각으로_움직일_수_있다")
    @CsvSource(value = {"RED:3:2:4:1", "RED:4:1:5:0", "RED:5:2:4:1", "RED:4:1:3:0", "GREEN:3:7:4:8",
            "GREEN:4:8:5:9", "GREEN:5:7:4:8", "GREEN:4:8:3:9"}, delimiterString = ":")
    @ParameterizedTest
    void should_Not_ThrowException_WhenMoveDiagonalForwardInCastle(
            Team team,
            int startColumn,
            int startRow,
            int goalColumn,
            int goalRow
    ) {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(startColumn, startRow);
        Position goal = createPosition(goalColumn, goalRow);
        Piece piece = PieceCreator.create(team, PieceType.SOLDIER);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatCode(() -> piece.validateMovable(board, start, goal))
                .doesNotThrowAnyException();
    }

    @DisplayName("졸병은_궁성_밖에서_대각으로_움직일_수_없다")
    @Test
    void should_ThrowException_WhenMoveDiagonalOutOfCastle() {
        // given
        Map<Position, Piece> initialBoard = new HashMap<>();
        Position start = createPosition(4, 4);
        Position goal = createPosition(5, 5);
        Piece piece = PieceCreator.create(Team.GREEN, PieceType.SOLDIER);

        initialBoard.put(start, piece);
        Board board = new Board(initialBoard);

        // then
        assertThatThrownBy(() -> piece.validateMovable(board, start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }
}
