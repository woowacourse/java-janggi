package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_ELEPHANT;
import static janggi.domain.TestFixture.RED_CHARIOT;
import static janggi.domain.TestFixture.RED_ELEPHANT;
import static janggi.domain.TestFixture.RED_HORSE;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChariotTest {
    @Test
    void 차는_가로로_움직일_수_있다() {
        // given
        Piece chariot = RED_CHARIOT;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.ZERO, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_세로로_움직일_수_있다() {
        // given
        Piece chariot = RED_CHARIOT;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_가로_세로가_아닌_위치로_움직일_수_없다() {
        // given
        Piece chariot = RED_CHARIOT;

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.ZERO, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        // given
        Piece chariot = RED_CHARIOT;

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);
        PiecePath path = new PiecePath(source, destination);

        // when
        List<Position> allRoute = chariot.findAllRoute(path);

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(allRoute).hasSize(3);
        softly.assertThat(allRoute.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO));
        softly.assertThat(allRoute.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE));
        softly.assertThat(allRoute.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR));

        softly.assertAll();
    }

    @Test
    void 차의_목적지에_같은팀이_있으면_이동불가() {
        Piece chariot = RED_CHARIOT;
        Piece elephant = RED_ELEPHANT;
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_있으면_이동불가() {
        Piece chariot = RED_CHARIOT;
        Piece elephant = RED_ELEPHANT;
        List<Piece> piecesOnRoute = List.of(RED_HORSE);

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece chariot = RED_CHARIOT;
        Piece elephant = BLUE_ELEPHANT;
        List<Piece> piecesOnRoute = List.of();

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @DisplayName("차는 궁성 안에서 대각선으로 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "8,4,0,6",
            "8,6,0,4",
            "0,4,8,6",
            "0,6,8,4"
    })
    void Chariot_canMoveDiagonal_inPalace(int srcRow, int srcCol, int dstRow, int dstCol) {
        // given
        Piece chariot = RED_CHARIOT;
        PiecePath path = new PiecePath(Position.of(srcRow, srcCol), Position.of(dstRow, dstCol));

        // when
        boolean validMovement = chariot.isValidMovement(path);

        // then
        assertThat(validMovement).isTrue();
    }

    @DisplayName("차는 궁성 안에서 선이 없는 대각선 경로로는 움직일 수 없다.")
    @Test
    void cannotMove_notPalaceLine() {
        // given
        Piece chariot = RED_CHARIOT;
        PiecePath path = new PiecePath(Position.of(8, 5), Position.of(9,6));

        // when
        boolean validMovement = chariot.isValidMovement(path);

        // then
        assertThat(validMovement).isFalse();
    }
}
