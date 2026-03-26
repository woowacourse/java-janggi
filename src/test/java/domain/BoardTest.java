package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.PieceInfo;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {
    @Test
    @DisplayName("from 좌표에 기물이 존재하지 않는 경우 예외가 발생한다.")
    void existPieceFromPositionExceptionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        assertThatThrownBy(() -> board.validateFromPosition(new Position(1, 1), Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("from 좌표의 기물이 본인 진영이 아닌 경우 예외가 발생한다.")
    void notMyCountryFromPositionExceptionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        assertThatThrownBy(() -> board.validateFromPosition(new Position(0, 9), Country.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 본인 진영의 기물이 아닙니다.");
    }


    @Test
    @DisplayName("졸・병(卒·兵) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void soldierPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("사(士) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void guardPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @ParameterizedTest
    @DisplayName("상(象)과 마(馬) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    @MethodSource("tableSettings")
    void elephantAndHorsePositionTest(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Board board = new Board(choTableSetting, hanTableSetting);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        List<Position> choPositions = List.of(new Position(1, 0), new Position(2, 0), new Position(6, 0),
                new Position(7, 0));
        List<Position> hanPositions = List.of(new Position(1, 9), new Position(2, 9), new Position(6, 9),
                new Position(7, 9));

        for (int index = 0; index < 4; index++) {
            PieceInfo pieceInfo = pieceInfos.get(choPositions.get(index));
            assertThat(pieceInfo.getPieceType()).isEqualTo(choTableSetting.getFormation(Country.CHO).get(index));
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }

        for (int index = 0; index < 4; index++) {
            PieceInfo pieceInfo = pieceInfos.get(hanPositions.get(index));
            assertThat(pieceInfo.getPieceType()).isEqualTo(hanTableSetting.getFormation(Country.HAN).get(index));
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
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
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("차(車) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void chariotPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("궁(漢·楚) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void generalPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            PieceInfo pieceInfo = pieceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("초기 기물이 모두 잘 생성되었는지 확인한다.")
    void fullStateCountTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();

        assertThat(pieceInfos.size()).isEqualTo(32);
    }

    @Test
    @DisplayName("졸병 기물이 잘 이동했는지 확인한다.")
    void moveSoldierTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(0, 3);
        Position to = new Position(1, 3);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("사 기물이 잘 이동했는지 확인한다.")
    void moveGuardTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(3, 0);
        Position to = new Position(3, 1);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.GUARD);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("상 기물이 잘 이동했는지 확인한다.")
    void moveElephantTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(1, 0);
        Position to = new Position(3, 3);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("마 기물이 잘 이동했는지 확인한다.")
    void moveHorseTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(2, 0);
        Position to = new Position(3, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.HORSE);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("차 기물이 잘 이동했는지 확인한다.")
    void moveChariotTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(0, 0);
        Position to = new Position(0, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("궁 기물이 잘 이동했는지 확인한다.")
    void moveGeneralTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Position from = new Position(4, 1);
        Position to = new Position(4, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.GENERAL);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("포 기물이 잘 이동했는지 확인한다.")
    void moveCannonTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        Position horseFrom = new Position(2, 0);
        Position horseTo = new Position(3, 2);
        board.move(horseFrom, horseTo);

        Position from = new Position(1, 2);
        Position to = new Position(4, 2);
        board.move(from, to);

        Map<Position, PieceInfo> pieceInfos = board.getPieceInfos();
        pieceInfos.get(to);

        assertThat(pieceInfos.get(from)).isNull();
        assertThat(pieceInfos.get(to).getPieceType()).isEqualTo(PieceType.CANNON);
        assertThat(pieceInfos.get(to).getCountry()).isEqualTo(Country.CHO);
    }
}
