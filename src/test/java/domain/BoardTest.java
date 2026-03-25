package domain;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("졸・병(卒·兵) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void soldierPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.SOLDIER.getChoPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.SOLDIER.getHanPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.SOLDIER);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("사(士) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void guardPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.GUARD.getChoPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GUARD.getHanPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GUARD);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @ParameterizedTest
    @DisplayName("상(象)과 마(馬) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    @MethodSource("tableSettings")
    void elephantAndHorsePositionTest(TableSetting choTableSetting, TableSetting hanTableSetting) {
        Board board = new Board(choTableSetting, hanTableSetting);

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        List<Position> choPositions = List.of(new Position(1, 0), new Position(2, 0), new Position(6, 0),
                new Position(8, 0));
        List<Position> hanPositions = List.of(new Position(1, 9), new Position(2, 9), new Position(6, 9),
                new Position(7, 9));

        for (int index = 0; index < 4; index++) {
            PieceInfo pieceInfo = peaceInfos.get(choPositions.get(index));
            assertThat(pieceInfo.getPieceType()).isEqualTo(choTableSetting.getFormation(Country.CHO).get(index));
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }

        for (int index = 0; index < 4; index++) {
            PieceInfo pieceInfo = peaceInfos.get(hanPositions.get(index));
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

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.CANNON.getChoPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CANNON.getHanPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CANNON);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("차(車) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void chariotPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.CHARIOT.getChoPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.CHARIOT.getHanPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.CHARIOT);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }

    @Test
    @DisplayName("궁(漢·楚) 기물이 자신의 초기 위치에 정확히 존재하는지 확인한다.")
    void generalPositionTest() {
        Board board = new Board(TableSetting.LEFT_TABLE, TableSetting.RIGHT_TABLE);

        Map<Position, PieceInfo> peaceInfos = board.getPieceInfos();
        for (Position choPosition : InitialPosition.GENERAL.getChoPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(choPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.CHO);
        }
        for (Position hanPosition : InitialPosition.GENERAL.getHanPositions()) {
            PieceInfo pieceInfo = peaceInfos.get(hanPosition);
            assertThat(pieceInfo.getPieceType()).isEqualTo(PieceType.GENERAL);
            assertThat(pieceInfo.getCountry()).isEqualTo(Country.HAN);
        }
    }
}
