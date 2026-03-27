package domain.board;


import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardInitializer.init(1, 2));
    }

    @Test
    @DisplayName("좌표에 기물이 있다면 기물을 반환한다.")
    void shouldReturnPiece_When_Piece_AtThePosition() {
        Piece elephantPiece = board.findBy(new Position(2, 1));
        Piece horsePiece = board.findBy(new Position(3, 1));
        Piece soldierPiece = board.findBy(new Position(1, 4));
        Piece guardPiece = board.findBy(new Position(4, 1));
        Piece cannonPiece = board.findBy(new Position(2, 3));
        Piece generalPiece = board.findBy(new Position(5, 2));
        Piece chariotPiece = board.findBy(new Position(1, 1));

        assertThat(elephantPiece.type()).isEqualTo(PieceType.ELEPHANT);
        assertThat(horsePiece.type()).isEqualTo(PieceType.HORSE);
        assertThat(soldierPiece.type()).isEqualTo(PieceType.SOLDIER);
        assertThat(guardPiece.type()).isEqualTo(PieceType.GUARD);
        assertThat(cannonPiece.type()).isEqualTo(PieceType.CANNON);
        assertThat(generalPiece.type()).isEqualTo(PieceType.GENERAL);
        assertThat(chariotPiece.type()).isEqualTo(PieceType.CHARIOT);
    }

    @Test
    @DisplayName("좌표에 기물이 존재하지 않는 경우 예외를 발생한다.")
    void throwException_When_PieceNotExist_AtThePosition() {
        assertThatThrownBy(() -> board.findBy(new Position(4, 4)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Nested
    class FindPiecesInPath {
        @Test
        @DisplayName("경로 중간에 기물이 있으면 해당 기물을 반환한다.")
        void check_If_PiecesInPath() {
            Map<Position, Piece> map = new HashMap<>();
            map.put(new Position(1, 3), new Piece(Camp.HAN, PieceType.SOLDIER));

            PathChecker pathChecker = new Board(map);

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = pathChecker.findPiecesInPath(path);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().type()).isEqualTo(PieceType.SOLDIER);
            assertThat(result.getFirst().camp()).isEqualTo(Camp.HAN);
        }

        @Test
        @DisplayName("경로에 기물이 없으면 빈 리스트를 반환한다.")
        void noPiecesInPath() {
            Board board = new Board(new HashMap<>());
            PathChecker pathChecker = board;

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = pathChecker.findPiecesInPath(path);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("경로에 여러 기물이 있으면 모두 반환한다.")
        void multiplePiecesInPath() {
            Map<Position, Piece> map = new HashMap<>();
            map.put(new Position(1, 2), new Piece(Camp.HAN, PieceType.SOLDIER));
            map.put(new Position(1, 4), new Piece(Camp.CHO, PieceType.CANNON));

            Board board = new Board(map);
            PathChecker pathChecker = board;

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = pathChecker.findPiecesInPath(path);

            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("빈 경로가 주어지면 빈 리스트를 반환한다.")
        void emptyPath() {
            Board board = new Board(new HashMap<>());
            PathChecker pathChecker = board;
            List<Position> path = List.of();

            List<Piece> result = pathChecker.findPiecesInPath(path);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    class IsSameCamp {
        Map<Position, Piece> dummyBoard;
        PathChecker pathChecker;

        @BeforeEach
        void setUp() {
            dummyBoard = new HashMap<>();

            dummyBoard.put(new Position(1, 1), new Piece(Camp.HAN, PieceType.CHARIOT));
            dummyBoard.put(new Position(1, 4), new Piece(Camp.HAN, PieceType.SOLDIER));
            dummyBoard.put(new Position(1, 7), new Piece(Camp.CHO, PieceType.SOLDIER));

            pathChecker = new Board(dummyBoard);
        }

        @Test
        @DisplayName("출발 위치의 기물과 도착 위치에 있는 기물의 진영이 동일하면 true를 반환한다.")
        void returnTrue_When_DestinationPieceCampIsSame() {
            Position from = new Position(1, 1);
            Position to = new Position(1, 4);


            assertTrue(pathChecker.isSameCamp(from, to));
        }

        @Test
        @DisplayName("출발 위치의 기물과 도착 위치에 있는 기물의 진영이 다르면 false를 반환한다.")
        void returnFalse_When_DestinationPieceCampIsDiffer() {
            Position from = new Position(1, 1);
            Position to = new Position(1, 7);

            assertFalse(pathChecker.isSameCamp(from, to));
        }
    }
}
