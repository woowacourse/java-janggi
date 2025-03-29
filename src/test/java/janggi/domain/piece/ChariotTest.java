package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.Turn;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    Map<Position, Piece> pieces;

    @BeforeEach
    void setUp() {
        pieces = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), new None(new Position(i, j)));
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Chariot chariot = new Chariot(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(5, 9);
        Soldier soldier = new Soldier(new Position(1, 1), Team.BLUE);
        pieces.put(soldier.getPosition(), soldier);
        Piece movedChariot = chariot.move(pieces, positionToMove);
        assertThat(movedChariot.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("차의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4"})
    @ParameterizedTest
    void move2(int x, int y) {
        Chariot chariot = new Chariot(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> chariot.move(pieces, positionToMove))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 초기 위치와 이동 위치 사이에 말이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Chariot chariot = new Chariot(new Position(5, 5), Team.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), Team.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
            chariot.move(pieces, new Position(1, 5)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차가 궁성 안에 있는 경우 간선을 타고 이동할 수 있다")
    @Test
    void testPalace() {
        Chariot chariot = new Chariot(new Position(8, 4), Team.BLUE);
        pieces.put(chariot.getPosition(), chariot);
        Board board = new Board(pieces, Turn.First());
        board.movePiece(chariot.getPosition(), new Position(10, 6));
        assertThat(board.getPieceByPosition(new Position(10, 6))).isInstanceOf(Chariot.class);
    }
}
