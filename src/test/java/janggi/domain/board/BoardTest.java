package janggi.domain.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.BoardAssembler;
import janggi.domain.strategy.IntersectionInitializer;
import janggi.domain.strategy.PalaceIntersectionInitializer;
import janggi.exception.JanggiException;
import janggi.support.TestArrangementStrategy;
import janggi.support.TestPiece;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드가 전략에 맞춰 정상적으로 생성된다.")
    void shouldReturnBoardWithSelectedArrangementStrategy() {
        // given
        Side currentSide = Side.HAN;
        Piece testPiece = new TestPiece(currentSide);
        Piece emptyPiece = EmptyPiece.getInstance();
        Map<Location, Piece> initialPieces = Map.of(
                Location.of(1, 1), testPiece,
                Location.of(2, 2), emptyPiece
        );
        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();

        // when
        BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);
        Board board = Board.create(assembler);
        List<List<Piece>> pieces = board.to2DArray();

        // then
        Assertions.assertThat(pieces.get(1).get(1)).isEqualTo(testPiece);
        Assertions.assertThat(pieces.get(2).get(2)).isEqualTo(emptyPiece);
    }

    @Test
    @DisplayName("이동할 기물의 위치와 도착지 좌표를 받아 기물을 이동시킨다.")
    void shouldMovePieceToDestination() {
        // given
        Piece testPiece = new TestPiece(Side.HAN);
        Map<Location, Piece> initialPieces = Map.of(
                Location.of(1, 1), testPiece
        );

        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

        Board board = Board.create(assembler);
        Location from = Location.of(1, 1);
        Location to = Location.of(0, 0);

        // when
        board.move(from, to);
        List<List<Piece>> board2DArray = board.to2DArray();

        // then
        Assertions.assertThat(board2DArray.get(from.row()).get(from.col()).isEmpty()).isTrue();
        Assertions.assertThat(board2DArray.get(to.row()).get(to.col())).isEqualTo(testPiece);
    }

    @Test
    @DisplayName("보드에 기물이 하나라도 있으면 true를 반환한다")
    void shouldReturnTrueForNoneEmptyBoard() {
        // given
        Map<Location, Piece> initialPieces = Map.of(
                Location.of(1, 1), new TestPiece(Side.HAN),
                Location.of(1, 2), EmptyPiece.getInstance()
        );

        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

        Board board = Board.create(assembler);

        // when & then
        Assertions.assertThat(board.isNotEmpty()).isTrue();
    }

    @Test
    @DisplayName("보드에 기물이 존재하지 않으면 false를 반환한다.")
    void shouldReturnTrueForEmptyBoard() {
        // given
        Map<Location, Piece> initialPieces = Map.of(
                Location.of(1, 1), EmptyPiece.getInstance(),
                Location.of(1, 2), EmptyPiece.getInstance()
        );

        ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

        Board board = Board.create(assembler);

        // when & then
        Assertions.assertThat(board.isNotEmpty()).isFalse();
    }

    @Nested
    class ValidateLocationExistenceTest {
        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenLocationExists() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(1, 1), new TestPiece(currentSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

            // when & then
            assertDoesNotThrow(() -> board.validateLocationOfPiece(currentSide, location));
        }

        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenLocationDoesNotExist() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(1, 1), new TestPiece(currentSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(11, 11));

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
                    Location.of(1, 1), new TestPiece(currentSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

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
                    Location.of(1, 1), new TestPiece(currentSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocationOfPiece(otherSide, location))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("출발 좌표에 기물이 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceDoesNotExistsAtLocation() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(5, 5), EmptyPiece.getInstance()
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(5, 5));

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
                    Location.of(1, 1), EmptyPiece.getInstance()
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> board.validateLocationToMove(Side.HAN, location));
        }

        @Test
        @DisplayName("도착 좌표에 상대 팀 기물이 존재하면 예외를 발생시키지 않는다.")
        void shouldThrowExceptionWhenPieceOnOtherSideExistsAtLocation() {
            // given
            Side currentSide = Side.HAN;
            Side otherSide = Side.CHO;
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(1, 1), new TestPiece(otherSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

            // when & then
            Assertions.assertThatNoException()
                    .isThrownBy(() -> board.validateLocationToMove(currentSide, location));
        }

        @Test
        @DisplayName("도착 좌표에 같은 팀 기물이 존재하면 예외를 발생시킨다.")
        void shouldNotThrowExceptionWhenPieceOnSameSideExistsAtLocation() {
            // given
            Side currentSide = Side.HAN;
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(1, 1), new TestPiece(currentSide)
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1, 1));

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocationToMove(currentSide, location))
                    .isInstanceOf(JanggiException.class);
        }

        @Test
        @DisplayName("빈 칸을 제외하고 현재 보드에 존재하는 기물 목록을 반환한다.")
        void returnAlivePieces() {
            // given
            Map<Location, Piece> initialPieces = Map.of(
                    Location.of(1, 2), new TestPiece(Side.HAN),
                    Location.of(1, 3), new TestPiece(Side.HAN),
                    Location.of(1, 4), new TestPiece(Side.HAN),
                    Location.of(1, 5), new TestPiece(Side.CHO),
                    Location.of(1, 6), new TestPiece(Side.CHO),
                    Location.of(1, 7), new TestPiece(Side.CHO),
                    Location.of(1, 8), EmptyPiece.getInstance(),
                    Location.of(1, 9), EmptyPiece.getInstance()
            );

            ArrangementStrategy strategy = new TestArrangementStrategy(initialPieces);
            IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
            BoardAssembler assembler = BoardAssembler.of(List.of(strategy), intersectionInitializer);

            // when
            Board board = Board.create(assembler);
            List<Piece> alivePieces = board.getAlivePieces();

            // then
            Assertions.assertThat(alivePieces)
                    .hasSize(6)
                    .doesNotContain(EmptyPiece.getInstance());
        }
    }
}
