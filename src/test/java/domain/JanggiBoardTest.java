package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class JanggiBoardTest {

    @DisplayName("해당 위치에 기물이 있는지 확인한다.")
    @Test
    void test1() {
        Map<Position, Piece> board = Map.of(
                new Position(4, 1), new Byeong(Team.HAN),
                new Position(4, 5), new Byeong(Team.HAN)
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
                new Position(1, 1), new Cha(Team.HAN),
                new Position(1, 2), new Ma(Team.HAN)
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
        Byeong byeong = new Byeong(Team.HAN);
        // given
        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), byeong);
        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(5, 1), byeong);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(5, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("최종 좌표에 상대 말이 있으면 상대말을 없애고 해당 위치로 이동한다.")
    @Test
    void test4() {
        //given
        Cha choCha = new Cha(Team.CHO);
        Cha hanCha = new Cha(Team.HAN);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choCha);
        beforeBoard.put(new Position(8, 1), hanCha);

        Map<Position, Piece> afterBoard = new HashMap<>();
        afterBoard.put(new Position(8, 1), choCha);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when
        janggiBoard.move(startPosition, targetPosition);

        // then
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("최종 좌표에 아군 말이 있으면  위치로 이동하지 못한다.")
    @Test
    void test5() {
        //given
        Cha choCha1 = new Cha(Team.CHO);
        Cha choCha2 = new Cha(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choCha1);
        beforeBoard.put(new Position(8, 1), choCha2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
    }

    @DisplayName("포가 건너뛸 말이 없으면 예외를 발생시킨다.")
    @Test
    void test6() {
        //given
        Po choPo = new Po(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(4, 1), choPo);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(4, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 다른 말 하나를 뛰어넘어야 합니다.");
    }

    @DisplayName("장기말 이동중 다른 장기말을 만나면 예외를 발생한다.")
    @Test
    void test7() {
        //given
        Cha choCha = new Cha(Team.CHO);
        Byeong choByeong = new Byeong(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(1, 1), choCha);
        beforeBoard.put(new Position(4, 1), choByeong);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(1, 1);
        Position targetPosition = new Position(8, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
    }

    @DisplayName("뛰어넘을 장기말이 포라면 예외를 발생한다.")
    @Test
    void test8() {
        //given
        Po choPo1 = new Po(Team.CHO);
        Po choPo2 = new Po(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(8, 2), choPo1);
        beforeBoard.put(new Position(8, 8), choPo2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 9);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 건너뛸 수 없습니다.");
    }

    @DisplayName("목표 좌표의 장기말이 포라면 예외를 발생한다.")
    @Test
    void test9() {
        //given
        Po choPo1 = new Po(Team.CHO);
        Po choPo2 = new Po(Team.HAN);
        Gung choGung = new Gung(Team.HAN);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        beforeBoard.put(new Position(8, 2), choPo1);
        beforeBoard.put(new Position(8, 5), choGung);
        beforeBoard.put(new Position(8, 8), choPo2);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 8);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.move(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("포는 포끼리 잡을 수 없습니다");
    }

    @DisplayName("포는 장기말을 뛰어넘어 이동한다.")
    @Test
    void test10() {
        //given
        Po choPo = new Po(Team.CHO);
        Gung choGung = new Gung(Team.CHO);

        Map<Position, Piece> beforeBoard = new HashMap<>();
        Map<Position, Piece> afterBoard = new HashMap<>();

        beforeBoard.put(new Position(8, 2), choPo);
        beforeBoard.put(new Position(8, 5), choGung);
        afterBoard.put(new Position(8, 8), choPo);
        afterBoard.put(new Position(8, 5), choGung);

        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(beforeBoard);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        Position startPosition = new Position(8, 2);
        Position targetPosition = new Position(8, 8);

        // when & then
        janggiBoard.move(startPosition, targetPosition);
        assertThat(beforeBoard).isEqualTo(afterBoard);
    }

    @DisplayName("특정 팀의 궁이 생존했는지 알 수 있다")
    @ParameterizedTest
    @CsvSource({
            "HAN, HAN, true",
            "CHO, CHO, true",
            "CHO, HAN, false",
            "HAN, CHO, false",
    })
    void test10(Team gungTeam, Team team, boolean expected) {
        //given
        Map<Position, Piece> board = new HashMap<>();
        board.put(new Position(1, 1), new Gung(gungTeam));
        FakeBoardGenerator boardGenerator = new FakeBoardGenerator(board);
        JanggiBoard janggiBoard = new JanggiBoard(boardGenerator);

        // when
        boolean actual = janggiBoard.existGung(team);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 시작_위치에_기물이_존재하지_않는_경우_예외를_발생시킨다() {
        Map<Position, Piece> board = new HashMap<>();
        JanggiBoard janggiBoard = new JanggiBoard(new FakeBoardGenerator(board));

        assertThatThrownBy(() -> janggiBoard.move(new Position(1, 1), new Position(1, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물이 존재하지 않는 위치입니다.");
    }
}
