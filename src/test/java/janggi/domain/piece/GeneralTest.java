package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_GENERAL;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GeneralTest {

    @DisplayName("궁은 궁성 안에서 이동 가능")
    @ParameterizedTest
    @CsvSource({
            "8, 4",
            "8, 5",
            "8, 6",
            "9, 4",
            "9, 6",
            "0, 4",
            "0, 5",
            "0, 6",
    })
    void General_canMove_inPalace(int destinationRow, int destinationCol) {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(9, 5);
        Position destination = Position.of(destinationRow, destinationCol);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("왼쪽위 모서리에서 이동 가능한 곳 - 오른쪽, 아래쪽, 오른쪽아래대각선")
    @ParameterizedTest
    @CsvSource({
            "8, 5",
            "9, 4",
            "9, 5",
    })
    void General_canMove_inPalace2(int destinationRow, int destinationCol) {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(8, 4);
        Position destination = Position.of(destinationRow, destinationCol);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("오른쪽위 모서리에서 이동 가능한 곳 - 왼쪽, 아래쪽, 왼쪽아래대각선")
    @ParameterizedTest
    @CsvSource({
            "8, 5",
            "9, 6",
            "9, 5",
    })
    void General_canMove_inPalace3(int destinationRow, int destinationCol) {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(8, 6);
        Position destination = Position.of(destinationRow, destinationCol);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("왼쪽아래 모시러에서 이동 가능한 곳 - 위쪽, 오른쪽, 오른쪽위대각선")
    @ParameterizedTest
    @CsvSource({
            "9, 4",
            "0, 5",
            "9, 5",
    })
    void General_canMove_inPalace4(int destinationRow, int destinationCol) {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(0, 4);
        Position destination = Position.of(destinationRow, destinationCol);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("오른쪽아래 모서리에서 이동 가능한 곳 - 위쪽, 왼쪽, 왼쪽위대각선")
    @ParameterizedTest
    @CsvSource({
            "9, 6",
            "0, 5",
            "9, 5",
    })
    void General_canMove_inPalace5(int destinationRow, int destinationCol) {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(0, 6);
        Position destination = Position.of(destinationRow, destinationCol);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("선이 없는 대각선으로는 이동 불가능")
    @Test
    void cannotMove_notLine() {
        // given
        Piece general = BLUE_GENERAL;

        Position source = Position.of(8, 5);
        Position destination = Position.of(9, 6);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean validMovement = general.isValidMovement(path);

        // then
        assertThat(validMovement).isFalse();
    }
}
