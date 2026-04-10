package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.board.formation.OutsideMaFormation;
import domain.coordinate.Position;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Pawn;
import domain.state.Side;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BoardTest {

    BoardFixtureInitializer boardFixtureInitializer = new BoardFixtureInitializer();

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardInitializeTest() {
        // given - when - then
        assertDoesNotThrow(() -> boardFixtureInitializer.build());
    }

    @Test
    @DisplayName("장기판 범위 내의 좌표 입력은 정상 작동한다.")
    void movePieceTest() {
        // given
        Board board = boardFixtureInitializer.build();
        Position start = Position.unsafe(9, 0);
        Position destination = Position.unsafe(8, 0);

        // when - then
        assertDoesNotThrow(() -> board.movePiece(start, destination));
    }

    @Test
    @DisplayName("0부터 9 범위를 넘어간 열 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Col_Error_Test() {
        // given
        Board board = boardFixtureInitializer.build();
        Position start = Position.unsafe(10, 4);
        Position destination = Position.unsafe(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("0부터 8 범위를 넘어간 행 좌표 입력에 대한 이동은 예외를 발생한다.")
    void boardRange_Row_Error_Test() {
        // given
        Board board = boardFixtureInitializer.build();
        Position start = Position.unsafe(4, 9);
        Position destination = Position.unsafe(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("음수 좌표 입력은 예외에 대한 이동은 발생한다.")
    void boardRange_Negative_Error_Test() {
        // given
        Board board = boardFixtureInitializer.build();
        Position start = Position.unsafe(-1, 4);
        Position destination = Position.unsafe(4, 4);

        // when - then
        assertThatThrownBy(() -> board.movePiece(start, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 좌표가 비어있으면 True를 반환한다.")
    void isEmpty_True_Test() {
        // given
        Position position = Position.of(8, 1);
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isEmpty(position)).isTrue();
    }

    @Test
    @DisplayName("해당 좌표가 비어있지 않으면 False를 반환한다.")
    void isEmpty_False_Test() {
        // given
        Position position = Position.of(8, 1);
        boardFixtureInitializer.put(position, new Pawn(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isEmpty(position)).isFalse();
    }

    @Test
    @DisplayName("장기판에 장이 있으면 true를 반환한다.")
    void hasKing_True_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.hasKing(Side.HAN)).isTrue();
    }

    @Test
    @DisplayName("장기판에 장이 없으면 false를 반환한다.")
    void hasKing_False_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.hasKing(Side.CHU)).isFalse();
    }

    @Test
    @DisplayName("장기의 시작 기물 점수는 72 점이다.")
    void calculateScoreChuSideTest() {
        // given
        Board board = new Board(new BasicBoardInitializer(new OutsideMaFormation(Side.HAN), new OutsideMaFormation(Side.CHU)).initialize());

        // when - then
        assertThat(board.calculateScore(Side.CHU)).isEqualTo(72);
    }

    @Test
    @DisplayName("장기의 시작 기물 점수는 72 점이다.")
    void calculateScoreHanSideTest() {
        // given
        Board board = new Board(new BasicBoardInitializer(new OutsideMaFormation(Side.HAN), new OutsideMaFormation(Side.CHU)).initialize());

        // when - then
        assertThat(board.calculateScore(Side.HAN)).isEqualTo(72);
    }

    @Test
    @DisplayName("장이 공격받고 있지 않다면 false를 반환한다")
    void isSafe_False_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        boardFixtureInitializer.put(Position.of(1, 0), new Chariot(Side.CHU));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isSafe(Side.HAN)).isFalse();
    }

    @Test
    @DisplayName("장이 공격당하고 있다면 true를 반환한다")
    void isSafe_True_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isSafe(Side.HAN)).isTrue();
    }

    @Test
    @DisplayName("왕이 공격받고 있으며, 다음 수에 장군을 피할 수 없다면 true를 반환한다.")
    void isSafeMate_True_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        boardFixtureInitializer.put(Position.of(8, 3), new King(Side.CHU));
        boardFixtureInitializer.put(Position.of(4, 3), new Chariot(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 4), new Chariot(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isCheckmate(Side.CHU)).isTrue();
    }

    @Test
    @DisplayName("왕이 공격받고 있지 않으며, 다음 수에 장군을 피할 수 있다면 false를 반환한다.")
    void isSafeMate_False_Test() {
        // given
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        boardFixtureInitializer.put(Position.of(8, 3), new King(Side.CHU));
        boardFixtureInitializer.put(Position.of(4, 3), new Chariot(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 4), new Chariot(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when - then
        assertThat(board.isCheckmate(Side.HAN)).isFalse();
    }

    @Test
    @DisplayName("현재의 움직임으로 인해 장군이 되는 경우 이동을 제한한다.")
    void calculateLegalMovesTest() {
        // given
        Position position = Position.of(8, 3);
        boardFixtureInitializer.put(position, new King(Side.CHU));
        boardFixtureInitializer.put(Position.of(1, 4), new King(Side.HAN));
        boardFixtureInitializer.put(Position.of(4, 4), new Chariot(Side.HAN));
        Board board = boardFixtureInitializer.build();

        // when
        List<Position> possibleMoves = board.calculateLegalMoves(position);

        // then
        Assertions.assertThat(possibleMoves).containsOnly(
                new Position(7, 3),
                new Position(9, 3)
        );

        Assertions.assertThat(possibleMoves).doesNotContain(
                new Position(8, 4)
        );
    }
}
