package domain;

import domain.piece.Cannon;
import domain.piece.King;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
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
        Map<Position, Piece> afterBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), blueCannon);
        beforeBoard.put(new Position(8, 5), blueKing);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiGame game = new JanggiGame(boardGenerator);

        // when
        Map<Position, Piece> boardState = game.getBoardState();

        // then
        Assertions.assertThat(boardState).hasSize(2);
    }

    @DisplayName("ㅈ")
    @Test
    void test2() {
        // given
//        JanggiGame game = new JanggiGame();

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(2, 1);
        // when
//        game.move(startPosition, targetPosition);
        // then
    }
}
