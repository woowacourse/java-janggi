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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ElephantTest {

    @DisplayName("상이 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(4, 5);
        Elephant elephant = new Elephant();
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, elephant));

        Map<Position, PieceState> map = Map.of(position, pieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = elephant.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(8);
    }

    @DisplayName("상 앞에 팀의 기물이 있다면 갈 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"4,5,5,5,6", "4,5,6,6,7"})
    void test2(int elephantRow, int elephantColumn, int otherRow, int otherColumn, int expected) {
        // given
        Position position = Position.of(elephantRow, elephantColumn);
        Elephant elephant = new Elephant();
        Position soldierPosition = Position.of(otherRow, otherColumn);
        Soldier soldier = new Soldier();
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, elephant));
        PieceState soldierPieceState = new PieceState(soldierPosition, new Piece(Side.HAN, soldier));

        Map<Position, PieceState> map = Map.of(position, pieceState, soldierPosition, soldierPieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = elephant.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(expected);
    }

    @DisplayName("상의 최종 목적지에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(4, 5);
        Elephant elephant = new Elephant();
        Position soldierPosition = Position.of(7, 3);
        Soldier soldier = new Soldier();
        PieceState pieceState = new PieceState(position, new Piece(Side.HAN, elephant));
        PieceState soldierPieceState = new PieceState(soldierPosition, new Piece(Side.CHO, soldier));

        Map<Position, PieceState> map = Map.of(position, pieceState, soldierPosition, soldierPieceState);

        // when
        Board board = new Board(new HashMap<>(map));
        Set<Position> actual = elephant.generateMovePosition(board, Side.HAN, position);

        // then
        assertThat(actual.size()).isEqualTo(8);
    }
}
