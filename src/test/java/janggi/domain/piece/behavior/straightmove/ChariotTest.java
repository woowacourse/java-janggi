package janggi.domain.piece.behavior.straightmove;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.move.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.behavior.Soldier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @DisplayName("차가 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Piece chariotPiece = new Piece(Side.HAN, chariot);

        Map<Position, Piece> map = Map.of(position, chariotPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(17);
    }

    @DisplayName("차 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        Piece chariotPiece = new Piece(Side.HAN, chariot);
        Piece soldierPiece = new Piece(Side.HAN, soldier);

        Map<Position, Piece> map = Map.of(position, chariotPiece, soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(8);
    }

    @DisplayName("차 앞에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        Piece chariotPiece = new Piece(Side.HAN, chariot);
        Piece soldierPiece = new Piece(Side.CHO, soldier);

        Map<Position, Piece> map = Map.of(position, chariotPiece, soldierPosition, soldierPiece);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(9);
    }
}
