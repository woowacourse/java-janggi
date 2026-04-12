package janggi.domain.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.board.strategy.ArrangementStrategy;
import janggi.domain.board.strategy.BoardAssembler;
import janggi.support.TestArrangementStrategy;
import janggi.support.TestPiece;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {
    
    private static final Piece EMPTY = EmptyPiece.getInstance();

    @Test
    @DisplayName("보드가 전략에 맞춰 정상적으로 생성된다.")
    void shouldReturnBoardWithSelectedArrangementStrategy() {
        // given
        Side currentSide = Side.HAN;
        Piece testPiece = new TestPiece(PieceType.CHA, currentSide);
        Map<Location, Piece> initialPieces = Map.of(
                new Location(1, 1), testPiece,
                new Location(2, 2), EMPTY
        );
        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

        // when
        BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
        Board board = Board.create(assembler);
        List<List<Piece>> pieces = board.to2DArray();

        // then
        Assertions.assertThat(pieces.get(1).get(1)).isEqualTo(testPiece);
        Assertions.assertThat(pieces.get(2).get(2)).isEqualTo(EMPTY);
    }

    @Nested
    class ValidateLocationExistenceTest {
        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenLocationExists() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, currentSide)
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location location = new Location(1, 1);

            // when & then
            assertDoesNotThrow(() -> board.validateLocationOfPiece(currentSide, location));
        }

        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenLocationDoesNotExist() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, currentSide)
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location location = new Location(11, 11);

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocationOfPiece(currentSide, location))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class ValidateLocationOfPieceTest {
        @Test
        @DisplayName("출발 좌표에 같은 팀 기물이 존재하면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenPieceOnSameSideExistsAtLocation() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, currentSide)
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location location = new Location(1, 1);

            // when & then
            assertDoesNotThrow(() -> board.validateLocationOfPiece(currentSide, location));
        }

        @Test
        @DisplayName("출발 좌표에 상대 팀 기물이 존재하면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceOnOtherSideExistsAtLocation() {
            // given
            Side currentSide = Side.HAN;
            Side otherSide = Side.CHO;
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, currentSide)
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location location = new Location(1, 1);

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocationOfPiece(otherSide, location))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("출발 좌표에 기물이 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceDoesNotExistsAtLocation() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(5, 5), EMPTY
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location location = new Location(5, 5);

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocationOfPiece(Side.HAN, location))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class ValidateLocationToMoveTest {
        @Test
        @DisplayName("도착 좌표에 기물이 존재하지 않으면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenPieceDoesNotExistsAtLocation() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), EMPTY
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location from = new Location(0, 0);
            Location to = new Location(1, 1);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> board.validateLocationToMove(from, to));
        }

        @Test
        @DisplayName("도착 좌표에 상대 팀 기물이 존재하면 예외를 발생시키지 않는다.")
        void shouldThrowExceptionWhenPieceOnOtherSideExistsAtLocation() {
            // given
            Side otherSide = Side.CHO;
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, otherSide)
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);
            Location from = new Location(0, 0);
            Location to = new Location(1, 1);

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> board.validateLocationToMove(from, to));
        }
    }

    @Test
    @DisplayName("이동할 기물의 위치와 도착지 좌표를 받아 기물을 이동시킨다.")
    void shouldMovePieceToDestination() {
        // given
        Piece testPiece = new TestPiece(PieceType.CHA, Side.HAN);
        Map<Location, Piece> initialPieces = Map.of(
                new Location(1, 1), testPiece
        );
        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);

        BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
        Board board = Board.create(assembler);
        Location from = new Location(1, 1);
        Location to = new Location(0, 0);

        // when
        board.move(from, to);
        List<List<Piece>> board2DArray = board.to2DArray();

        // then
        Piece expectEmpty = board2DArray.get(from.row()).get(from.col());
        Assertions.assertThat(expectEmpty.isEmpty()).isTrue();

        Piece expectTestPiece = board2DArray.get(to.row()).get(to.col());
        Assertions.assertThat(expectTestPiece).isEqualTo(testPiece);
    }

    @Nested
    class IsNotEmptyTest {
        @Test
        @DisplayName("보드에 기물이 하나라도 있으면 true를 반환한다")
        void shouldReturnTrueForNoneEmptyBoard() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), new TestPiece(PieceType.CHA, Side.HAN),
                    new Location(1, 2), EMPTY
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);

            // when & then
            Assertions.assertThat(board.isNotEmpty()).isTrue();
        }

        @Test
        @DisplayName("보드에 기물이 존재하지 않으면 false를 반환한다.")
        void shouldReturnTrueForEmptyBoard() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    new Location(1, 1), EMPTY,
                    new Location(1, 2), EMPTY
            );
            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
            Board board = Board.create(assembler);

            // when & then
            Assertions.assertThat(board.isNotEmpty()).isFalse();
        }
    }

    @Test
    @DisplayName("보드판 위에 남아있는 기물의 목록을 반환한다.")
    void shouldReturnListOfRemainingAlivePieces() {
        // given
        Piece hanPiece = new TestPiece(PieceType.CHA, Side.HAN);
        Piece choPiece = new TestPiece(PieceType.PO, Side.CHO);
        Map<Location, Piece> initialPieces = Map.of(
                new Location(1, 1), hanPiece,
                new Location(8, 2), choPiece
        );
        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
        BoardAssembler assembler = BoardAssembler.from(List.of(strategy, strategy));
        Board board = Board.create(assembler);

        // when & then
        Assertions.assertThat(board.getAlivePieces()).containsExactly(hanPiece, choPiece);
    }
}
