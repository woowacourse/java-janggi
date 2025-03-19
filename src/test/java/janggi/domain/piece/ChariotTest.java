package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Piece;
import janggi.domain.PieceState;
import janggi.domain.Position;
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
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, chariot));

        Map<Position, PieceState> map = Map.of(position, pieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(17);
    }

    @DisplayName("차 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, chariot));
        PieceState soldierPieceState = new PieceState(soldierPosition, new Piece(Side.HAN, soldier));

        Map<Position, PieceState> map = Map.of(position, pieceState, soldierPosition, soldierPieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(8);
    }

    @DisplayName("차 앞에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(10, 1);
        Chariot chariot = new Chariot();
        Position soldierPosition = Position.of(9, 1);
        Soldier soldier = new Soldier();
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, chariot));
        PieceState soldierPieceState = new PieceState(soldierPosition, new Piece(Side.CHO, soldier));

        Map<Position, PieceState> map = Map.of(position, pieceState, soldierPosition, soldierPieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = chariot.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(9);
    }
}
