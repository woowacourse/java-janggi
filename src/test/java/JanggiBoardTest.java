import domain.FakeBoardGenerator;
import domain.JanggiBoard;
import domain.Position;
import domain.Team;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.Move;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("장기말은 앞으로 이동할 수 있다.")
    @Test
    void test1() {
        Map<Position, Piece> board = Map.of(
                new Position(4, 1), new Pawn(Team.RED),
                new Position(4, 5), new Pawn(Team.RED)
        );
        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(board);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        boolean moveResult1 = janggiBoard.canMove(new Position(4, 1), Move.FRONT);
        boolean moveResult2 = janggiBoard.canMove(new Position(4, 5), Move.FRONT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(true);
            softAssertions.assertThat(moveResult2).isEqualTo(true);
        });
    }

    @DisplayName("앞에 아군의 말이 있을 시 이동할 수 없다.")
    @Test
    void test2() {
        Map<Position, Piece> board = Map.of(
                new Position(1, 1), new Chariot(Team.RED),
                new Position(1, 2), new Horse(Team.RED)
        );
        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(board);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        boolean moveResult1 = janggiBoard.canMove(new Position(1, 1), Move.RIGHT);
        boolean moveResult3 = janggiBoard.canMove(new Position(1, 2), Move.LEFT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(false);
            softAssertions.assertThat(moveResult3).isEqualTo(false);
        });
    }

    @DisplayName("장기말은 이동시 목표 좌표로 위치가 바뀐다.")
    @Test
    void test3() {

        // given
        Map<Position, Piece> beforeBoard = Map.of(
                new Position(4, 1), new Pawn(Team.RED)
        );
        Map<Position, Piece> afterBoard = Map.of(
                new Position(4, 2), new Pawn(Team.RED)
        );

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(5, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        Assertions.assertThat(beforeBoard).isEqualTo(afterBoard);
    }
}
