package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
    }

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
    @DisplayName("졸・병(卒·兵) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void soldierPositionTest() {
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            assertThat(board.getSpecificPieceInfo(choPosition).pieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(board.getSpecificPieceInfo(choPosition).country()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            assertThat(board.getSpecificPieceInfo(hanPosition).pieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(board.getSpecificPieceInfo(hanPosition).country()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("사(士) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void guardPositionTest() {
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            assertThat(board.getSpecificPieceInfo(choPosition).pieceType()).isEqualTo(PieceType.GUARD);
            assertThat(board.getSpecificPieceInfo(choPosition).country()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            assertThat(board.getSpecificPieceInfo(hanPosition).pieceType()).isEqualTo(PieceType.GUARD);
            assertThat(board.getSpecificPieceInfo(hanPosition).country()).isEqualTo(Country.HAN);
        }
    }

    @ParameterizedTest
    @DisplayName("상(象)과 마(馬) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    @MethodSource("tableSettings")
    void elephantAndHorsePositionTest(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Board newBoard = Board.create(hanTableSetting, choTableSetting);
        List<Position> choPositions = List.of(new Position(1, 0), new Position(2, 0), new Position(6, 0),
                new Position(7, 0));
        List<Position> hanPositions = List.of(new Position(1, 9), new Position(2, 9), new Position(6, 9),
                new Position(7, 9));

        for (int index = 0; index < 4; index++) {
            assertThat(newBoard.getSpecificPieceInfo(choPositions.get(index)).pieceType())
                    .isEqualTo(choTableSetting.getFormation(Country.CHO).get(index));
            assertThat(newBoard.getSpecificPieceInfo(choPositions.get(index)).country())
                    .isEqualTo(Country.CHO);
        }

        for (int index = 0; index < 4; index++) {
            assertThat(newBoard.getSpecificPieceInfo(hanPositions.get(index)).pieceType())
                    .isEqualTo(hanTableSetting.getFormation(Country.HAN).get(index));
            assertThat(newBoard.getSpecificPieceInfo(hanPositions.get(index)).country())
                    .isEqualTo(Country.HAN);
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
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            assertThat(board.getSpecificPieceInfo(choPosition).pieceType()).isEqualTo(PieceType.CANNON);
            assertThat(board.getSpecificPieceInfo(choPosition).country()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            assertThat(board.getSpecificPieceInfo(hanPosition).pieceType()).isEqualTo(PieceType.CANNON);
            assertThat(board.getSpecificPieceInfo(hanPosition).country()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("차(車) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void chariotPositionTest() {
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            assertThat(board.getSpecificPieceInfo(choPosition).pieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(board.getSpecificPieceInfo(choPosition).country()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            assertThat(board.getSpecificPieceInfo(hanPosition).pieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(board.getSpecificPieceInfo(hanPosition).country()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("궁(漢·楚) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void generalPositionTest() {
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            assertThat(board.getSpecificPieceInfo(choPosition).pieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(board.getSpecificPieceInfo(choPosition).country()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            assertThat(board.getSpecificPieceInfo(hanPosition).pieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(board.getSpecificPieceInfo(hanPosition).country()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("초기 기물이 모두 잘 생성되었는지 확인한다.")
    void fullStateCountTest() {
        assertThat(board.pieceCount()).isEqualTo(32);
    }

    @Test
    @DisplayName("졸병 기물이 잘 이동했는지 확인한다.")
    void moveSoldierTest() {
        Position from = new Position(0, 3);
        Position to = new Position(1, 3);
        board.move(from, to);

        assertThat(board.isEmpty(from)).isTrue();
        assertThat(board.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.SOLDIER);
        assertThat(board.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("사 기물이 잘 이동했는지 확인한다.")
    void moveGuardTest() {
        Position from = new Position(3, 0);
        Position to = new Position(3, 1);
        board.move(from, to);

        assertThat(board.isEmpty(from)).isTrue();
        assertThat(board.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.GUARD);
        assertThat(board.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("상 기물이 잘 이동했는지 확인한다.")
    void moveElephantTest() {
        Board newBoard = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        Position from = new Position(1, 0);
        Position to = new Position(3, 3);
        newBoard.move(from, to);

        assertThat(newBoard.isEmpty(from)).isTrue();
        assertThat(newBoard.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(newBoard.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("마 기물이 잘 이동했는지 확인한다.")
    void moveHorseTest() {
        Board newBoard = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        Position from = new Position(2, 0);
        Position to = new Position(3, 2);
        newBoard.move(from, to);

        assertThat(newBoard.isEmpty(from)).isTrue();
        assertThat(newBoard.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.HORSE);
        assertThat(newBoard.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("차 기물이 잘 이동했는지 확인한다.")
    void moveChariotTest() {
        Position from = new Position(0, 0);
        Position to = new Position(0, 2);
        board.move(from, to);

        assertThat(board.isEmpty(from)).isTrue();
        assertThat(board.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(board.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("궁 기물이 잘 이동했는지 확인한다.")
    void moveGeneralTest() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 2);
        board.move(from, to);

        assertThat(board.isEmpty(from)).isTrue();
        assertThat(board.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.GENERAL);
        assertThat(board.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("포 기물이 잘 이동했는지 확인한다.")
    void moveCannonTest() {
        Board newBoard = Board.create(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);
        Position horseFrom = new Position(2, 0);
        Position horseTo = new Position(3, 2);
        newBoard.move(horseFrom, horseTo);

        Position from = new Position(1, 2);
        Position to = new Position(4, 2);
        newBoard.move(from, to);

        assertThat(newBoard.isEmpty(from)).isTrue();
        assertThat(newBoard.getSpecificPieceInfo(to).pieceType()).isEqualTo(PieceType.CANNON);
        assertThat(newBoard.getSpecificPieceInfo(to).country()).isEqualTo(Country.CHO);
    }

    @Test
    @DisplayName("상대 궁을 잡으면 true를 반환한다.")
    void catchGeneralTest() {
        board.move(new Position(0, 3), new Position(1, 3));
        board.move(new Position(0, 6), new Position(1, 6));
        board.move(new Position(4, 1), new Position(3, 1));
        board.move(new Position(0, 0), new Position(0, 8));

        assertThat(board.move(new Position(0, 8), new Position(4, 8))).isTrue();
    }

    @Test
    @DisplayName("초나라의 현재 점수를 계산한다.")
    void calculateChoCurrentScoreTest() {
        assertThat(board.calculateScore(Country.CHO)).isEqualTo(72.0);
    }

    @Test
    @DisplayName("한나라의 현재 점수를 계산한다.")
    void calculateHanCurrentScoreTest() {
        assertThat(board.calculateScore(Country.HAN)).isEqualTo(73.5);
    }
}
