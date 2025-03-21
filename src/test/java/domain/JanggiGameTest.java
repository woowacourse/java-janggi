package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {

    @DisplayName("장가판을 가져온다")
    @Test
    void test1() {
        // given
        Cannon blueCannon = new Cannon(Team.BLUE);
        King blueKing = new King(Team.BLUE);

        Map<Position, Piece> beforeBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), blueCannon);
        beforeBoard.put(new Position(8, 5), blueKing);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiGame game = new JanggiGame(boardGenerator, List.of("플레이어1", "플레이어2"));

        // when
        Map<Position, Piece> boardState = game.getBoardState();

        // then
        assertThat(boardState).isEqualTo(beforeBoard);
    }

    @DisplayName("장기판의 말을 이동시킨다")
    @Test
    void test2() {
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        King blueKing = new King(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueKing);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), List.of("플레이어1", "플레이어2"));

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(2, 1), blueKing);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 1);
        // when
        game.move(startPosition, targetPosition);
        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }


    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test3() {
        Map<Position, Piece> beforeBoard = new HashMap<>();
        Chariot blueChariot = new Chariot(Team.BLUE);
        beforeBoard.put(new Position(1, 1), blueChariot);
        JanggiGame game = new JanggiGame(new FakeBoardGenerator(beforeBoard), List.of("플레이어1", "플레이어2"));

        assertThatThrownBy(() -> game.move(new Position(1, 1), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
    }
}
