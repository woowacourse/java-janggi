package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Position;
import domain.TableSetting;
import domain.piece.PieceInfo;
import domain.piece.PieceType;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {
    private final Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
    private final Map<Position, PieceInfo> initPieceInfos = board.getPieceInfos();

    @Test
    @DisplayName("from 좌표에 기물이 존재하지 않는 경우 예외가 발생한다.")
    void existPieceFromPositionExceptionTest() {
        assertThatThrownBy(() -> board.validateFromPosition(new Position(1, 1), Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("from 좌표의 기물이 본인 진영이 아닌 경우 예외가 발생한다.")
    void notMyCountryFromPositionExceptionTest() {
        assertThatThrownBy(() -> board.validateFromPosition(new Position(0, 9), Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 본인 진영의 기물이 아닙니다.");
    }


    @Test
    @DisplayName("초나라의 졸・병(卒·兵) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void choSoldierPositionTest() {
        assertThat(initPieceInfos.get(new Position(0, 3)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(0, 3)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(2, 3)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(2, 3)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(4, 3)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(4, 3)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(6, 3)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(6, 3)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(8, 3)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(8, 3)).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("한나라의 졸・병(卒·兵) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void hanSoldierPositionTest() {
        assertThat(initPieceInfos.get(new Position(0, 6)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(0, 6)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(2, 6)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(2, 6)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(4, 6)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(4, 6)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(6, 6)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(6, 6)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(8, 6)).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(initPieceInfos.get(new Position(8, 6)).country()).isEqualTo(Country.HAN);
    }

    @Test
    @DisplayName("사(士) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void guardPositionTest() {
        assertThat(initPieceInfos.get(new Position(3, 0)).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(initPieceInfos.get(new Position(3, 0)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(5, 0)).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(initPieceInfos.get(new Position(5, 0)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(3, 9)).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(initPieceInfos.get(new Position(3, 9)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(5, 9)).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(initPieceInfos.get(new Position(5, 9)).country()).isEqualTo(Country.HAN);
    }

    @ParameterizedTest
    @DisplayName("초나라의 상(象)과 마(馬) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    @MethodSource("tableSettings")
    void choElephantAndHorsePositionTest(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Board board = new Board(choTableSetting, hanTableSetting);
        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(new Position(1, 0)).pieceType()).isEqualTo(
                choTableSetting.getFormation(Country.CHO).getFirst());
        assertThat(pieceInfos.get(new Position(1, 0)).country()).isEqualTo(Country.CHO);

        assertThat(pieceInfos.get(new Position(2, 0)).pieceType()).isEqualTo(
                choTableSetting.getFormation(Country.CHO).get(1));
        assertThat(pieceInfos.get(new Position(2, 0)).country()).isEqualTo(Country.CHO);

        assertThat(pieceInfos.get(new Position(6, 0)).pieceType()).isEqualTo(
                choTableSetting.getFormation(Country.CHO).get(2));
        assertThat(pieceInfos.get(new Position(6, 0)).country()).isEqualTo(Country.CHO);

        assertThat(pieceInfos.get(new Position(7, 0)).pieceType()).isEqualTo(
                choTableSetting.getFormation(Country.CHO).getLast());
        assertThat(pieceInfos.get(new Position(7, 0)).country()).isEqualTo(Country.CHO);
    }

    @ParameterizedTest
    @DisplayName("한나라의 상(象)과 마(馬) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    @MethodSource("tableSettings")
    void hanElephantAndHorsePositionTest(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Board board = new Board(choTableSetting, hanTableSetting);
        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(new Position(1, 9)).pieceType()).isEqualTo(
                hanTableSetting.getFormation(Country.HAN).getFirst());
        assertThat(pieceInfos.get(new Position(1, 9)).country()).isEqualTo(Country.HAN);

        assertThat(pieceInfos.get(new Position(2, 9)).pieceType()).isEqualTo(
                hanTableSetting.getFormation(Country.HAN).get(1));
        assertThat(pieceInfos.get(new Position(2, 9)).country()).isEqualTo(Country.HAN);

        assertThat(pieceInfos.get(new Position(6, 9)).pieceType()).isEqualTo(
                hanTableSetting.getFormation(Country.HAN).get(2));
        assertThat(pieceInfos.get(new Position(6, 9)).country()).isEqualTo(Country.HAN);

        assertThat(pieceInfos.get(new Position(7, 9)).pieceType()).isEqualTo(
                hanTableSetting.getFormation(Country.HAN).getLast());
        assertThat(pieceInfos.get(new Position(7, 9)).country()).isEqualTo(Country.HAN);
    }

    static Stream<Arguments> tableSettings() {
        return Stream.of(
                Arguments.arguments(TableSetting.LEFT_TABLE, TableSetting.LEFT_TABLE),
                Arguments.arguments(TableSetting.RIGHT_TABLE, TableSetting.RIGHT_TABLE),
                Arguments.arguments(TableSetting.INSIDE_TABLE, TableSetting.INSIDE_TABLE),
                Arguments.arguments(TableSetting.OUTSIDE_TABLE, TableSetting.OUTSIDE_TABLE)
        );
    }

    @Test
    @DisplayName("포(包) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void cannonPositionTest() {
        assertThat(initPieceInfos.get(new Position(1, 2)).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(initPieceInfos.get(new Position(1, 2)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(7, 2)).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(initPieceInfos.get(new Position(7, 2)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(1, 7)).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(initPieceInfos.get(new Position(1, 7)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(7, 7)).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(initPieceInfos.get(new Position(7, 7)).country()).isEqualTo(Country.HAN);
    }

    @Test
    @DisplayName("차(車) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void chariotPositionTest() {
        assertThat(initPieceInfos.get(new Position(0, 0)).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(initPieceInfos.get(new Position(0, 0)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(8, 0)).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(initPieceInfos.get(new Position(8, 0)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(0, 9)).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(initPieceInfos.get(new Position(0, 9)).country()).isEqualTo(Country.HAN);

        assertThat(initPieceInfos.get(new Position(8, 9)).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(initPieceInfos.get(new Position(8, 9)).country()).isEqualTo(Country.HAN);
    }

    @Test
    @DisplayName("궁(漢·楚) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void generalPositionTest() {
        assertThat(initPieceInfos.get(new Position(4, 1)).pieceType()).isEqualTo(PieceType.GENERAL);
        assertThat(initPieceInfos.get(new Position(4, 1)).country()).isEqualTo(Country.CHO);

        assertThat(initPieceInfos.get(new Position(4, 8)).pieceType()).isEqualTo(PieceType.GENERAL);
        assertThat(initPieceInfos.get(new Position(4, 8)).country()).isEqualTo(Country.HAN);
    }

    @Test
    @DisplayName("초기 기물이 모두 잘 생성되었는지 확인한다.")
    void fullStateCountTest() {
        assertThat(initPieceInfos.size()).isEqualTo(32);
    }

    @Test
    @DisplayName("졸병 기물이 잘 이동했는지 확인한다.")
    void moveSoldierTest() {
        Position from = new Position(0, 3);
        Position to = new Position(1, 3);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("사 기물이 잘 이동했는지 확인한다.")
    void moveGuardTest() {
        Position from = new Position(3, 0);
        Position to = new Position(3, 1);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("상 기물이 잘 이동했는지 확인한다.")
    void moveElephantTest() {
        Position from = new Position(1, 0);
        Position to = new Position(3, 3);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("마 기물이 잘 이동했는지 확인한다.")
    void moveHorseTest() {
        Position from = new Position(2, 0);
        Position to = new Position(3, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.HORSE);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("차 기물이 잘 이동했는지 확인한다.")
    void moveChariotTest() {
        Position from = new Position(0, 0);
        Position to = new Position(0, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("궁 기물이 잘 이동했는지 확인한다.")
    void moveGeneralTest() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.GENERAL);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("포 기물이 잘 이동했는지 확인한다.")
    void moveCannonTest() {
        Position horseFrom = new Position(2, 0);
        Position horseTo = new Position(3, 2);
        board.move(horseFrom, horseTo);

        Position from = new Position(1, 2);
        Position to = new Position(4, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(pieceInfos.get(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("상대 기물을 정상적으로 잡는지 확인한다.")
    void killAnotherCountryPieceTest() {
        // 초나라 졸병 오른쪽으로 이동
        board.move(new Position(0, 3), new Position(1, 3));
        // 한나라 졸병 오른쪽으로 이동
        board.move(new Position(0, 6), new Position(1, 6));

        Position choChariotFrom = new Position(0, 0);
        Position hanChariotFrom = new Position(0, 9);
        board.move(choChariotFrom, hanChariotFrom);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.get(choChariotFrom)).isNull();
        assertThat(pieceInfos.get(hanChariotFrom).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(pieceInfos.get(hanChariotFrom).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("기물의 초기 위치와 이동 위치가 동일하면 예외가 발생한다.")
    void moveSamePositionExceptionTest() {
        Position from = new Position(0, 3);
        Position to = new Position(0, 3);

        assertThatThrownBy(() -> board.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기물을 동일한 위치로 이동시킬 수 없습니다.");
    }
}
