package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardInitializer.init(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT));
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
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("[ERROR] 해당 좌표에 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대 기물이 있는 위치로 이동하면 잡힌 기물을 반환한다.")
    void returnCapturedPiece_When_MoveToOpponentPiece() {
        Board board = new Board(Map.of(
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL)
        ));

        Piece capturedPiece = board.movePiece(new Position(5, 5), new Position(5, 2))
                .orElseThrow();

        assertThat(capturedPiece.camp()).isEqualTo(Camp.HAN);
        assertThat(capturedPiece.type()).isEqualTo(PieceType.GENERAL);
    }

    @Nested
    class FindPiecesInPath {
        @Test
        @DisplayName("경로 중간에 기물이 있으면 해당 기물을 반환한다.")
        void check_If_PiecesInPath() {
            Map<Position, Piece> map = new HashMap<>();
            map.put(new Position(1, 3), new Piece(Camp.HAN, PieceType.SOLDIER));

            BoardChecker boardChecker = new Board(map);

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = boardChecker.findPiecesInPath(path);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().type()).isEqualTo(PieceType.SOLDIER);
            assertThat(result.getFirst().camp()).isEqualTo(Camp.HAN);
        }

        @Test
        @DisplayName("경로에 기물이 없으면 빈 리스트를 반환한다.")
        void noPiecesInPath() {
            BoardChecker boardChecker = new Board(new HashMap<>());

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = boardChecker.findPiecesInPath(path);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("경로에 여러 기물이 있으면 모두 반환한다.")
        void multiplePiecesInPath() {
            Map<Position, Piece> map = new HashMap<>();
            map.put(new Position(1, 2), new Piece(Camp.HAN, PieceType.SOLDIER));
            map.put(new Position(1, 4), new Piece(Camp.CHO, PieceType.CANNON));

            BoardChecker boardChecker = new Board(map);

            List<Position> path = List.of(
                    new Position(1, 2),
                    new Position(1, 3),
                    new Position(1, 4)
            );

            List<Piece> result = boardChecker.findPiecesInPath(path);

            assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("빈 경로가 주어지면 빈 리스트를 반환한다.")
        void emptyPath() {
            BoardChecker boardChecker = new Board(new HashMap<>());
            List<Position> path = List.of();

            List<Piece> result = boardChecker.findPiecesInPath(path);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    class IsSameCamp {
        Map<Position, Piece> dummyBoard;
        BoardChecker boardChecker;

        @BeforeEach
        void setUp() {
            dummyBoard = new HashMap<>();

            dummyBoard.put(
                    new Position(1, 1),
                    new Piece(Camp.HAN, PieceType.CHARIOT)
            );
            dummyBoard.put(
                    new Position(1, 4),
                    new Piece(Camp.HAN, PieceType.SOLDIER)
            );
            dummyBoard.put(
                    new Position(1, 7),
                    new Piece(Camp.CHO, PieceType.SOLDIER)
            );

            boardChecker = new Board(dummyBoard);
        }

        @Test
        @DisplayName("출발 위치의 기물과 도착 위치에 있는 기물의 진영이 동일하면 true를 반환한다.")
        void returnTrue_When_DestinationPieceCampIsSame() {
            Position from = new Position(1, 1);
            Position to = new Position(1, 4);

            assertTrue(boardChecker.isSameCamp(from, to));
        }

        @Test
        @DisplayName("출발 위치의 기물과 도착 위치에 있는 기물의 진영이 다르면 false를 반환한다.")
        void returnFalse_When_DestinationPieceCampIsDiffer() {
            Position from = new Position(1, 1);
            Position to = new Position(1, 7);

            assertFalse(boardChecker.isSameCamp(from, to));
        }
    }

    @Test
    @DisplayName("직선 이동이면 일반 경로를 반환한다.")
    void findMovePath_When_StraightMove() {
        Board board = new Board(new HashMap<>());

        assertThat(board.findMovePath(new Position(1, 1), new Position(1, 4)))
                .contains(List.of(new Position(1, 2), new Position(1, 3)));
    }

    @Test
    @DisplayName("궁성 대각선 이동이면 궁성 경로를 반환한다.")
    void findMovePath_When_PalaceDiagonalMove() {
        Board board = new Board(new HashMap<>());

        assertThat(board.findMovePath(new Position(4, 1), new Position(6, 3)))
                .contains(List.of(new Position(5, 2)));
    }

    @Test
    @DisplayName("직선 이동도 궁성 대각선 이동도 아니면 빈 값을 반환한다.")
    void findMovePath_When_InvalidMove() {
        Board board = new Board(new HashMap<>());

        assertThat(board.findMovePath(new Position(1, 1), new Position(2, 2)))
                .isEmpty();
    }

    @Test
    @DisplayName("특정 진영의 남아 있는 기물 점수 합을 반환한다.")
    void returnScoreOfCamp_When_CalculateRemainingPieceScore() {
        Board board = new Board(Map.of(
                new Position(1, 1), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(2, 1), new Piece(Camp.CHO, PieceType.SOLDIER),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(4, 2), new Piece(Camp.HAN, PieceType.GUARD)
        ));

        assertThat(board.scoreOf(Camp.CHO)).isEqualTo(15);
        assertThat(board.scoreOf(Camp.HAN)).isEqualTo(3);
    }

    @Test
    @DisplayName("현재 보드 위에 있는 기물들의 상태를 반환한다.")
    void returnBoardPieces() {
        Board board = new Board(Map.of(
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL)
        ));

        List<BoardPiece> boardPieces = board.pieces();

        assertThat(boardPieces).contains(
                new BoardPiece(new Position(5, 2), Camp.HAN, PieceType.GENERAL),
                new BoardPiece(new Position(5, 5), Camp.CHO, PieceType.CHARIOT)
        );
    }

    @Test
    @DisplayName("보드는 왕의 위치를 찾을 수 있다.")
    void findGeneralPosition() {
        Position generalPosition = board.findPositionOf(Camp.HAN, PieceType.GENERAL);

        assertThat(generalPosition).isEqualTo(new Position(5, 2));
    }

    @Test
    @DisplayName("기물을 찾을 수 없는 경우 예외를 발생한다.")
    void throwException_When_CanNotFoundPiece() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL)
        ));

        assertThatThrownBy(() -> board.findPositionOf(Camp.HAN, PieceType.GENERAL))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("[ERROR] 조건에 맞는 기물이 존재하지 않습니다.");
    }
}
