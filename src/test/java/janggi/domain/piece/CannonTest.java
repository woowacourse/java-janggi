package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonTest {
    Map<Position, Piece> board;
    Position beforePosition = new Position(5, 5);

    @BeforeEach
    void setUp() {
        board = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                board.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Cannon cannon = new Cannon(Team.BLUE);
        Position betweenPosition = new Position(3, 5);
        Position afterPosition = new Position(2, 5);

        board.put(betweenPosition, new Soldier(Team.RED));

        assertThatCode(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .doesNotThrowAnyException();
    }

    @DisplayName("포의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(final int x, final int y) {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(x, y);

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 초기 위치와 이동 위치 사이에 포가 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        Position betweenPosition = new Position(3, 5);

        board.put(betweenPosition, new Cannon(Team.BLUE));

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 초기 위치와 이동 위치 사이에 2개 이상의 기물이 존재하는 경우 예외를 던진다.")
    @Test
    void move33() {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        Position betweenPosition = new Position(3, 5);
        Position betweenPosition2 = new Position(4, 5);

        board.put(betweenPosition, new Soldier(Team.BLUE));
        board.put(betweenPosition2, new Soldier(Team.BLUE));

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        Position betweenPosition = new Position(3, 5);

        board.put(betweenPosition, new Soldier(Team.BLUE));
        board.put(afterPosition, new Soldier(Team.BLUE));

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 이동 위치에 상대편 포가 있으면 예외를 던진다")
    @Test
    void move5() {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(3, 5);
        board.put(afterPosition, new Cannon(Team.RED));

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("원래 위치로 이동하려할 경우 예외를 던진다")
    @Test
    void move55() {
        Cannon cannon = new Cannon(Team.BLUE);

        assertThatThrownBy(() ->
                cannon.getMovableValidator(beforePosition, beforePosition).accept(board))
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
    void move6(final int x, final int y) {
        Cannon cannon = new Cannon(Team.BLUE);
        Position afterPosition = new Position(x, y);
        Position betweenPosition1 = new Position(3, 5);
        Position betweenPosition2 = new Position(5, 3);
        Position betweenPosition3 = new Position(6, 5);
        Position betweenPosition4 = new Position(5, 6);
        board.put(betweenPosition1, new Soldier(Team.RED));
        board.put(betweenPosition2, new Soldier(Team.RED));
        board.put(betweenPosition3, new Soldier(Team.RED));
        board.put(betweenPosition4, new Soldier(Team.RED));

        assertThatCode(() ->
                cannon.getMovableValidator(beforePosition, afterPosition).accept(board))
                .doesNotThrowAnyException();
    }
}