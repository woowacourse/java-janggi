package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("포가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(7, 5);
        Cannon cannon = new Cannon();
        Position soldierPosition = Position.of(5, 5);
        Soldier soldier = new Soldier();
        Piece cannonPiece = new Piece(Side.HAN, cannon);
        Piece soldierPiece = new Piece(Side.HAN, soldier);

        Map<Position, Piece> map = Map.of(position, cannonPiece, soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = cannon.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(4);
    }

    @DisplayName("기물을 넘어가고 같은 팀의 기물의 전까지 이동할 수 있다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(7, 5);
        Cannon cannon = new Cannon();
        Position soldierPosition1 = Position.of(5, 5);
        Soldier soldier1 = new Soldier();
        Position soldierPosition2 = Position.of(1, 5);
        Soldier soldier2 = new Soldier();
        Piece cannonPiece = new Piece(Side.HAN, cannon);

        Piece soldierPiece1 = new Piece(Side.HAN, soldier1);
        Piece soldierPiece2 = new Piece(Side.HAN, soldier2);

        Map<Position, Piece> map = Map.of(position, cannonPiece, soldierPosition1, soldierPiece1,
                soldierPosition2, soldierPiece2);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = cannon.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(3);
    }

    @DisplayName("기물을 넘어가고 상대 기물이 있는 위치까지 이동할 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(7, 5);
        Cannon cannon = new Cannon();
        Position soldierPosition1 = Position.of(6, 5);
        Soldier soldier1 = new Soldier();
        Position soldierPosition2 = Position.of(1, 5);
        Soldier soldier2 = new Soldier();
        Piece piece = new Piece(Side.HAN, cannon);
        Piece soldierPiece1 = new Piece(Side.HAN, soldier1);
        Piece soldierPiece2 = new Piece(Side.CHO, soldier2);

        Map<Position, Piece> map = Map.of(position, piece, soldierPosition1, soldierPiece1,
                soldierPosition2, soldierPiece2);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = cannon.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(5);
    }

    @DisplayName("이동 경로에 포가 있으면 넘어갈 수 없다.")
    @Test
    void test4() {
        // given
        Position position = Position.of(7, 5);
        Cannon cannon = new Cannon();
        Position cannon2Position = Position.of(5, 5);
        Cannon cannon2 = new Cannon();
        Piece piece = new Piece(Side.HAN, cannon);
        Piece cannon2Piece = new Piece(Side.HAN, cannon2);

        Map<Position, Piece> map = Map.of(position, piece, cannon2Position, cannon2Piece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = cannon.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("기물을 넘어가고 상대 기물이 포라면 지나갈 수 없다.")
    @Test
    void test5() {
        // given
        Position position = Position.of(10, 5);
        Cannon cannon = new Cannon();
        Piece piece = new Piece(Side.CHO, cannon);

        Position cannonPosition2 = Position.of(4, 5);
        Cannon cannon2 = new Cannon();
        Piece cannonPiece2 = new Piece(Side.HAN, cannon2);

        Position soldierPosition = Position.of(9, 5);
        Soldier soldier = new Soldier();
        Piece soldierPiece = new Piece(Side.HAN, soldier);

        Map<Position, Piece> map = Map.of(position, piece, cannonPosition2, cannonPiece2,
                soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = cannon.generateAvailableMovePositions(board, Side.CHO, position);

        // then
        assertThat(actual).hasSize(4);
    }
}
