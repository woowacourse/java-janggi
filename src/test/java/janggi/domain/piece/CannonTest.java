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

class CannonTest {

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
        Cannon cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(2, 5);
        Soldier otherSoldier1 = new Soldier(new Position(3, 5), Team.BLUE);
        Soldier otherSoldier2 = new Soldier(new Position(1, 1), Team.BLUE);
        pieces.put(otherSoldier1.getPosition(), otherSoldier1);
        pieces.put(otherSoldier2.getPosition(), otherSoldier2);
        Piece movedCannon = cannon.move(pieces, positionToMove);
        assertThat(movedCannon.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("포의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(int x, int y) {
        Piece cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> cannon.move(pieces, positionToMove))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 초기 위치와 이동 위치 사이에 포가 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Cannon cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Cannon otherCannon = new Cannon(new Position(3, 5), Team.BLUE);
        pieces.put(otherCannon.getPosition(), otherCannon);
        assertThatThrownBy(() ->
            cannon.move(pieces, new Position(2, 5)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 이동 위치에 같은 편 기물이 있으면 이동하지 못한다")
    @Test
    void move4() {
        Cannon cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), Team.BLUE);
        pieces.put(cannon.getPosition(), cannon);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        Board board = new Board(pieces, Turn.First());
        board.movePiece(cannon.getPosition(), otherSoldier.getPosition());
        assertThat(
            board.getPieceByPosition(otherSoldier.getPosition()))
            .isInstanceOf(Soldier.class);
    }

    @DisplayName("포의 이동 위치에 상대편 포가 있으면 예외를 던진다")
    @Test
    void move5() {
        Cannon cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Cannon otherCannon = new Cannon(new Position(3, 5), Team.RED);
        pieces.put(otherCannon.getPosition(), otherCannon);
        assertThatThrownBy(() ->
            cannon.move(pieces, otherCannon.getPosition()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 모든 이동 경로가 가능하다")
    @CsvSource(value = {
        "2,5",
        "5,2",
        "7,5",
        "5,7"
    })
    @ParameterizedTest
    void move6(int x, int y) {
        Cannon cannon = new Cannon(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(x, y);

        Soldier soldier1 = new Soldier(new Position(3, 5), Team.BLUE);
        Soldier soldier2 = new Soldier(new Position(5, 3), Team.BLUE);
        Soldier soldier3 = new Soldier(new Position(6, 5), Team.BLUE);
        Soldier soldier4 = new Soldier(new Position(5, 6), Team.BLUE);
        pieces.put(soldier1.getPosition(), soldier1);
        pieces.put(soldier2.getPosition(), soldier2);
        pieces.put(soldier3.getPosition(), soldier3);
        pieces.put(soldier4.getPosition(), soldier4);

        Piece movedHorse = cannon.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("포가 궁성 안에 있는 경우 간선을 타고 이동할 수 있다")
    @Test
    void testPalace() {
        Piece cannon = new Cannon(new Position(8, 4), Team.BLUE);
        Piece soldier = new Soldier(new Position(9, 5), Team.BLUE);
        pieces.put(cannon.getPosition(), cannon);
        pieces.put(soldier.getPosition(), soldier);

        Board board = new Board(pieces, Turn.First());
        board.movePiece(cannon.getPosition(), new Position(10, 6));
        assertThat(board.getPieceByPosition(new Position(10, 6))).isInstanceOf(Cannon.class);
    }
}
