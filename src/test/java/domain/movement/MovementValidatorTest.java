package domain.movement;

import domain.board.Board;
import domain.board.Column;
import domain.board.Pieces;
import domain.board.Position;
import domain.board.Row;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MovementValidator 통합 테스트 (Board를 통한 검증)")
class MovementValidatorTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Board boardWith(Map<Position, Piece> pieceMap) {
        return new Board(new Pieces(pieceMap));
    }

    private Coordinate coord(String input) {
        return Coordinate.toCoordinate(input);
    }

    private Map<Position, Piece> pieces() {
        return new HashMap<>();
    }

    @Nested
    @DisplayName("차(CHARIOT) 이동 검증")
    class ChariotMovementValidation {

        @Test
        @DisplayName("차는 빈 경로로 몇칸이든 이동 가능하다")
        void chariotCanMoveAlongClearPath() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            Board moved = board.move(coord("a0 a9"), turn);

            assertThat(moved.isEmpty(pos(Column.A, Row.ZERO))).isTrue();
            assertThat(moved.pieceAt(pos(Column.A, Row.NINE))).isPresent();
        }

        @Test
        @DisplayName("차는 아군 기물이 있는 위치로 이동할 수 없다")
        void chariotCannotCaptureFriendlyPiece() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT));
            map.put(pos(Column.A, Row.FIVE), new Piece(Team.HAN, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a5"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("차는 경로 중간에 기물이 있으면 그 너머로 이동할 수 없다")
        void chariotCannotMoveThroughAnyPiece() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT));
            map.put(pos(Column.A, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a5"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("차는 후보 경로에 없는 위치로 이동할 수 없다")
        void chariotCannotMoveDiagonally() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.CHARIOT));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 f5"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("포(CANNON) 이동 검증")
    class CannonMovementValidation {

        @Test
        @DisplayName("포는 1개의 기물을 넘어서 빈 위치로 이동할 수 있다")
        void cannonCanJumpOverOneNonCannonToEmptyCell() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            map.put(pos(Column.A, Row.FOUR), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            Board moved = board.move(coord("a0 a7"), turn);

            assertThat(moved.isEmpty(pos(Column.A, Row.ZERO))).isTrue();
            assertThat(moved.pieceAt(pos(Column.A, Row.SEVEN))).isPresent();
        }

        @Test
        @DisplayName("포는 뛰어넘을 기물이 없으면 이동할 수 없다")
        void cannonCannotMoveWithoutJumpPiece() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a9"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포는 2개 이상의 기물을 뛰어넘을 수 없다")
        void cannonCannotJumpOverTwoPieces() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            map.put(pos(Column.A, Row.TWO), new Piece(Team.CHO, PieceType.SOLDIER));
            map.put(pos(Column.A, Row.FOUR), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a6"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포는 포를 넘어서 이동할 수 없다")
        void cannonCannotJumpOverAnotherCannon() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            map.put(pos(Column.A, Row.FOUR), new Piece(Team.CHO, PieceType.CANNON)); // cannon as jump target
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a7"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포는 포를 잡을 수 없다")
        void cannonCannotCaptureEnemyCannon() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            map.put(pos(Column.A, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
            map.put(pos(Column.A, Row.SIX), new Piece(Team.CHO, PieceType.CANNON));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a6"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("포는 아군 기물이 있는 위치로 이동할 수 없다")
        void cannonCannotCaptureFriendlyPiece() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CANNON));
            map.put(pos(Column.A, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
            map.put(pos(Column.A, Row.SIX), new Piece(Team.HAN, PieceType.CHARIOT));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("a0 a6"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("마(HORSE) 이동 검증")
    class HorseMovementValidation {

        @Test
        @DisplayName("마는 종점을 제외한 이동경로에 기물이 있으면 이동할 수 없다")
        void horseCannotMoveWhenBlockingPositionIsOccupied() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.HORSE));
            map.put(pos(Column.E, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 d2"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> board.move(coord("e4 f2"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("마는 아군 기물이 있는 위치로 이동할 수 없다")
        void horseCannotCaptureFriendlyPiece() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.HORSE));
            map.put(pos(Column.D, Row.TWO), new Piece(Team.HAN, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 d2"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("상(ELEPHANT) 이동 검증")
    class ElephantMovementValidation {

        @Test
        @DisplayName("상은 이동 경로가 모두 비어있으면 이동할 수 있다")
        void elephantCanMoveWhenBothBlockingPositionsAreEmpty() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.ELEPHANT));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            Board moved = board.move(coord("e4 c1"), turn);

            assertThat(moved.isEmpty(pos(Column.E, Row.FOUR))).isTrue();
            assertThat(moved.pieceAt(pos(Column.C, Row.ONE))).isPresent();
        }

        @Test
        @DisplayName("상은 이동 경로 중 첫 번째 발 좌표에 기물이 있으면 이동할 수 없다")
        void elephantCannotMoveWhenFirstBlockingPositionIsOccupied() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.ELEPHANT));
            map.put(pos(Column.E, Row.THREE), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 c1"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("상은 이동 경로 중 두 번째 발 좌표에 기물이 있으면 이동할 수 없다")
        void elephantCannotMoveWhenSecondBlockingPositionIsOccupied() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.ELEPHANT));
            map.put(pos(Column.D, Row.TWO), new Piece(Team.CHO, PieceType.SOLDIER)); // second blocking (e3->d2)
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 c1"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

    }

    @Nested
    @DisplayName("궁(GENERAL) / 사(GUARD) 이동 검증")
    class PalacePieceMovementValidation {

        @Test
        @DisplayName("궁은 궁성 내 인접한 빈 위치로 이동할 수 있다")
        void generalCanMoveToAdjacentEmptyPosition() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            Board moved = board.move(coord("e1 e0"), turn);

            assertThat(moved.isEmpty(pos(Column.E, Row.ONE))).isTrue();
            assertThat(moved.pieceAt(pos(Column.E, Row.ZERO))).isPresent();
        }

        @Test
        @DisplayName("궁은 인접한 아군 기물이 있는 위치로 이동할 수 없다")
        void generalCannotCaptureFriendly() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL));
            map.put(pos(Column.E, Row.ZERO), new Piece(Team.HAN, PieceType.GUARD));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e1 e0"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("사는 궁과 동일하게 궁성 내 인접한 빈 위치로 이동할 수 있다")
        void guardCanMoveToAdjacentEmptyPosition() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.D, Row.ONE), new Piece(Team.HAN, PieceType.GUARD));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            Board moved = board.move(coord("d1 e1"), turn);

            assertThat(moved.isEmpty(pos(Column.D, Row.ONE))).isTrue();
            assertThat(moved.pieceAt(pos(Column.E, Row.ONE))).isPresent();
        }
    }

    @Nested
    @DisplayName("졸(SOLDIER) 이동 검증")
    class SoldierMovementValidation {

        @Test
        @DisplayName("HAN 졸은 후진(row-1) 방향으로 이동할 수 없다")
        void hanSoldierCannotMoveBackward() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FOUR), new Piece(Team.HAN, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.HAN);

            assertThatThrownBy(() -> board.move(coord("e4 e3"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("CHO 졸은 후진(row+1) 방향으로 이동할 수 없다")
        void choSoldierCannotMoveBackward() {
            Map<Position, Piece> map = pieces();
            map.put(pos(Column.E, Row.FIVE), new Piece(Team.CHO, PieceType.SOLDIER));
            Board board = boardWith(map);
            Turn turn = new Turn(Team.CHO);

            assertThatThrownBy(() -> board.move(coord("e5 e6"), turn))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
