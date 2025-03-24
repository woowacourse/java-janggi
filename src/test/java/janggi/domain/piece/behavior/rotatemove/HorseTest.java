package janggi.domain.piece.behavior.rotatemove;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.behavior.Soldier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @DisplayName("말이 움직일 수 있는 포지션들을 반환한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(3, 5);
        Horse horse = new Horse();
        Piece piece = new Piece(Side.HAN, horse);

        // when
        Board board = new Board(Map.of(position, piece));
        Set<Position> actual = horse.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(8).
                contains(Position.of(1, 4), Position.of(2, 3));
    }

    @DisplayName("말 앞에 팀의 기물이 있다면 갈 수 없다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(3, 5);
        Horse horse = new Horse();
        Position soldierPosition = Position.of(4, 5);
        Soldier soldier = new Soldier();
        Piece horsePiece = new Piece(Side.HAN, horse);
        Piece soldierPiece = new Piece(Side.HAN, soldier);

        // when
        Board board = new Board(Map.of(position, horsePiece, soldierPosition, soldierPiece));
        Set<Position> actual = horse.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(6);
    }

    @DisplayName("말의 최종 목적지에 상대의 기물이 있다면 해당 위치까지 갈 수 있다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(3, 5);
        Horse horse = new Horse();
        Position soldierPosition = Position.of(5, 4);
        Soldier soldier = new Soldier();
        Piece horsePiece = new Piece(Side.HAN, horse);
        Piece soldierPiece = new Piece(Side.CHO, soldier);

        // when
        Board board = new Board(Map.of(position, horsePiece, soldierPosition, soldierPiece));
        Set<Position> actual = horse.generateAvailableMovePositions(board, Side.HAN, position);

        // then
        assertThat(actual).hasSize(8);
    }
}
