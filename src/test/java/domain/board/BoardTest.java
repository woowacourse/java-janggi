package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.board.strategy.OutsideMaStrategy;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BoardTest {

    BasicBoardInitializer basicBoardInitializer = new BasicBoardInitializer(new OutsideMaStrategy(Side.HAN), new OutsideMaStrategy(Side.CHU));

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardInitializeTest() {
        // given - when - then
        assertDoesNotThrow(() -> new Board(basicBoardInitializer.initialize()));
    }

    @Test
    @DisplayName("장기판 범위 내의 좌표 입력은 정상 작동한다.")
    void movePieceTest() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position start = new Position(9, 0);
        Position destination = new Position(8, 0);

        // when - then
        assertDoesNotThrow(() -> board.movePiece(start, destination));
    }

    @Test
    @DisplayName("좌표 입력은 행은 0부터 8 열은 0 부터 9 범위여야 한다.")
    void isInvalid_True_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position position = new Position(4, 4);

        // when - then
        assertThat(board.isValidRange(position)).isTrue();
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력은 예외를 발생한다.")
    void isInvalid_Col_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position position = new Position(10, 4);

        // when - then
        assertThat(board.isValidRange(position)).isFalse();
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 행 좌표 입력은 예외를 발생한다.")
    void isInvalid_Row_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position position = new Position(4, 9);

        // when - then
        assertThat(board.isValidRange(position)).isFalse();
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position start = new Position(10, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position start = new Position(4, 9);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외에 대한 이동은 발생한다.")
    void boardRange_Negative_Error_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position start = new Position(-1, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 True를 반환한다.")
    void isEmpty_True_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position emptyPosition = new Position(8, 1);

        // when - then
        assertThat(board.isEmpty(emptyPosition)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표가 비어있지 않으면 False를 반환한다.")
    void isEmpty_False_Test() {
        // given
        Board board = new Board(basicBoardInitializer.initialize());
        Position notEmptyPosition = new Position(7, 1);

        // when - then
        assertThat(board.isEmpty(notEmptyPosition)).isFalse();
    }
}
