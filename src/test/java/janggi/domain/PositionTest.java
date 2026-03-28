package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {
    @DisplayName("범위 내의 행과 열에 맞는 객체를 생성한다.")
    @Test
    void 객체_정상_생성_테스트() {
        // given
        int row = 4;
        int column = 4;

        // when
        Position position = new Position(row, column);

        // then
        assertThat(position.row()).isEqualTo(row);
        assertThat(position.column()).isEqualTo(column);
    }

    @DisplayName("행과 열이 범위를 벗어나는 경우, IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 4", "4, 9", "-1, 1", "1, -1"})
    void 범위_예외_객체_생성_테스트(int row, int column) {
        assertThatThrownBy(() -> new Position(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보드 범위 내로 이동하면, 이동된 좌표를 가진 새로운 Position 객체를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "4, 4, 1, 0, 5, 4",   // 아래로 1칸
            "4, 4, -1, 0, 3, 4",  // 위로 1칸
            "4, 4, 0, 1, 4, 5",   // 오른쪽으로 1칸
            "4, 4, 0, -1, 4, 3",  // 왼쪽으로 1칸
            "0, 0, 9, 8, 9, 8"    // 끝에서 끝으로 이동
    })
    void 이동_정상_테스트(int row, int column, int moveRow, int moveColumn, int expectedRow, int expectedColumn) {
        // given
        Position position = new Position(row, column);

        // when
        Position movedPosition = position.move(moveRow, moveColumn);

        // then
        assertThat(movedPosition.row()).isEqualTo(expectedRow);
        assertThat(movedPosition.column()).isEqualTo(expectedColumn);
    }

    @DisplayName("이동 결과가 보드 범위를 벗어나는 경우, IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0, 0, -1, 0",
            "9, 8, 1, 0",
            "0, 0, 0, -1",
            "9, 8, 0, 1"
    })
    void 이동_범위_예외_테스트(int row, int column, int moveRow, int moveColumn) {
        Position position = new Position(row, column);

        assertThatThrownBy(() -> position.move(moveRow, moveColumn))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
