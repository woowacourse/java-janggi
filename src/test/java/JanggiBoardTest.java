import domain.FakeBoardGenerator;
import domain.JanggiBoard;
import domain.Position;
import domain.Team;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("해당 위치에 기물이 있는지 확인한다.")
    @Test
    void test1() {
        Map<Position, Piece> board = Map.of(
                new Position(4, 1), new Pawn(Team.RED),
                new Position(4, 5), new Pawn(Team.RED)
        );
        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(board);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        boolean moveResult1 = janggiBoard.isPositionEmpty(new Position(4, 2));
        boolean moveResult2 = janggiBoard.isPositionEmpty(new Position(4, 5));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(true);
            softAssertions.assertThat(moveResult2).isEqualTo(false);
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

        boolean moveResult1 = janggiBoard.isPositionEmpty(new Position(1, 1));
        boolean moveResult3 = janggiBoard.isPositionEmpty(new Position(1, 2));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moveResult1).isEqualTo(false);
            softAssertions.assertThat(moveResult3).isEqualTo(false);
        });
    }

    @DisplayName("장기말은 이동시 목표 좌표로 위치가 바뀐다.")
    @Test
    void test3() {
        Pawn pawn = new Pawn(Team.RED);
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), pawn);
        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(5, 1), pawn);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(5, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        Assertions.assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("최종 좌표에 상대 말이 있으면 상대말을 없애고 해당 위치로 이동한다.")
    @Test
    void test4() {
        //given
        Chariot blueChariot = new Chariot(Team.BLUE);
        Chariot redChariot = new Chariot(Team.RED);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), blueChariot);
        beforeBoard.put(new Position(8, 1), redChariot);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(8, 1), blueChariot);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        Assertions.assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("최종 좌표에 아군 말이 있으면  위치로 이동하지 못한다.")
    @Test
    void test5() {
        //given
        Chariot blueChariot1 = new Chariot(Team.BLUE);
        Chariot blueChariot2 = new Chariot(Team.BLUE);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), blueChariot1);
        beforeBoard.put(new Position(8, 1), blueChariot2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
    }


}
