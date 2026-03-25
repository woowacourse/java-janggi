package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.piece.King;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BoardTest {
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardTest() {
        // given - when - then
        assertDoesNotThrow(() -> new Board(boardInitializer));
    }

    @Test
    @DisplayName("정해진 위치에 있는지 확인한다.")
    void isPlaceAt_HanKing_Test() {
        // given
        Board board = new Board(boardInitializer);

        // when
        Position hanKingPosition = new Position(1, 4);
        King king = new King(Side.HAN);

        // then
        assertThat(board.isPieceAt(hanKingPosition, king)).isTrue();
    }

    @Test
    @DisplayName("기물이 정해진 위치에 있는지 확인한다.")
    void isPlaceAt_ChuKing_Test() {
        // given
        Board board = new Board(boardInitializer);

        // when
        Position chuKingPosition = new Position(8, 4);
        King king = new King(Side.CHU);

        // then
        assertThat(board.isPieceAt(chuKingPosition, king)).isTrue();
    }

//    - [ ] 검증: 장기판 범위를 초과한 좌표 입력은 예외를 발생한다.
//    - [ ] 검증: 기물이 존재하지 않는 좌표 입력은 예외를 발생한다.
//    - [ ] 검증: 상대 기물이 위치하는 좌표 입력은 예외를 발생한다.

    @Test
    @DisplayName("장기판 범위 내의 좌표 입력은 정상 작동한다.")
    void moveTest() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(9, 0);
        Position destination = new Position(8, 0);

        // when - then
        assertDoesNotThrow(() -> board.move(start, destination));
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(10, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(4, 9);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외를 발생한다.")
    void boardRange_Negative_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(-1, 4);
        Position destination = new Position(4, 4);

        // when - then
        assertThatThrownBy(() ->board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기물이 존재하지 않는 시작 좌표 입력은 예외를 발생한다.")
    void emptyPiece_Start_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(8, 0);
        Position destination = new Position(7, 0);

        // when - then
        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상대 기물이 위치하는 시작 좌표 입력은 예외를 발생한다.")
    void oppositeSide_Piece_Start_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(3, 0);
        Position destination = new Position(4, 0);

        // when - then
        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아군 기물이 위치하는 도착 좌표 입력은 예외를 발생한다.")
    void ourSide_Piece_Destination_Error_Test() {
        // given
        Board board = new Board(boardInitializer);
        Position start = new Position(9, 0);
        Position destination = new Position(6, 0);

        // when - then
        assertThatThrownBy(() -> board.move(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
