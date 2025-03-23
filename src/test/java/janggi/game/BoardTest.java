package janggi.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Byeong;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Point;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("장기 판 초기화 및 팀 테스트")
    class InitBoardTest {

        @Test
        @DisplayName("32개의 기물을 가진 장기판을 생성할 수 있다.")
        void createBoardWithPieces() {
            Board board = Board.init(Team.CHO);

            assertThat(board.getRunningPieces()).hasSize(32);
        }

        @Test
        @DisplayName("시작 팀을 지정하여 장기판을 생성할 수 있다.")
        void createBoardWithStartTeam() {
            Board board = Board.init(Team.CHO);

            assertThat(board.getTurn()).isEqualTo(Team.CHO);
        }

        @Test
        @DisplayName("팀을 변경할 수 있다.")
        void changeTeam() {
            Board board = Board.init(Team.CHO);

            board.reverseTurn();

            assertThat(board.getTurn()).isEqualTo(Team.HAN);
        }
    }

    @Nested
    @DisplayName("포를 제외한 기물 이동 테스트")
    class MoveExceptPoTest {

        @Test
        @DisplayName("다른 팀의 기물을 이동 시킬 수 없다.")
        void notMovePieceOfSameTeam() {
            Map<Point, Movable> pieces = new HashMap<>(
                Map.of(new Point(6, 4), new Byeong(Team.HAN)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(board.getTurn().getText() + "의 기물만 이동할 수 있습니다.");
        }

        @Test
        @DisplayName("기물의 이동 범위를 벗어나면 이동 시킬 수 없다.")
        void notMovePieceOutOfRange() {
            Map<Point, Movable> pieces = new HashMap<>(
                Map.of(new Point(6, 4), new Byeong(Team.CHO)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("기물은 이동 경로에 다른 기물이 있으면 이동 시킬 수 없다.")
        void notMovePieceWithHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Ma(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 5);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("기물을 이동 시킬 수 있다.")
        void movePiece() {
            Map<Point, Movable> pieces = new HashMap<>(
                Map.of(new Point(6, 4), new Byeong(Team.CHO)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = board.getRunningPieces();

            assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
        }

        @Test
        @DisplayName("이동 하는 위치에 같은 팀의 기물이 있으면 공격할 수 없다.")
        void notAttackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("이동 하는 위치에 다른 팀의 기물이 있으면 공격할 수 있다.")
        void attackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Byeong(Team.CHO),
                new Point(5, 4), new Byeong(Team.HAN)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = board.getRunningPieces();

            assertAll(() -> {
                assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
                assertThat(updatePieces).hasSize(1);
            });
        }
    }

    @Nested
    @DisplayName("포 이동 테스트")
    class PoMoveTest {

        @Test
        @DisplayName("다른 팀의 기물을 이동 시킬 수 없다.")
        void notMovePieceOfSameTeam() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(new Point(6, 4), new Po(Team.HAN)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(board.getTurn().getText() + "의 기물만 이동할 수 있습니다.");
        }

        @Test
        @DisplayName("기물의 이동 범위를 벗어나면 이동 시킬 수 없다.")
        void notMovePieceOutOfRange() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(new Point(6, 4), new Po(Team.CHO)));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 3);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("이동 하는 위치에 같은 팀의 기물이 있으면 공격할 수 없다.")
        void notAttackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(5, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 위치로 이동할 수 없습니다.");
        }

        @Test
        @DisplayName("포는 포를 제외한 기물이 경로에 하나가 없다면 공격할 수 없다.")
        void notAttackPieceWithoutHurdle() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Po(Team.HAN),
                new Point(4, 4), new Byeong(Team.HAN)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 제외한 하나의 기물만 필요합니다.");
        }

        @Test
        @DisplayName("포는 포를 공격할 수 없다.")
        void notAttackPieceWithPo() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO),
                new Point(4, 4), new Po(Team.HAN)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);

            assertThatThrownBy(() -> board.move(startPoint, targetPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("포는 포를 잡을 수 없습니다.");
        }

        @Test
        @DisplayName("기물을 이동 시킬 수 있다.")
        void movePiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = board.getRunningPieces();

            assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
        }

        @Test
        @DisplayName("이동 하는 위치에 다른 팀의 기물이 있으면 공격할 수 있다.")
        void attackPiece() {
            Map<Point, Movable> pieces = new HashMap<>(Map.of(
                new Point(6, 4), new Po(Team.CHO),
                new Point(5, 4), new Byeong(Team.CHO),
                new Point(4, 4), new Byeong(Team.HAN)
            ));
            Board board = new Board(pieces, Team.CHO);

            Point startPoint = new Point(6, 4);
            Point targetPoint = new Point(4, 4);
            board.move(startPoint, targetPoint);

            Map<Point, Movable> updatePieces = board.getRunningPieces();

            assertAll(() -> {
                assertThat(updatePieces.get(targetPoint)).isInstanceOf(Movable.class);
                assertThat(updatePieces).hasSize(2);
            });
        }
    }
}
