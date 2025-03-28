package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.board.Board;
import janggi.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직이지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,5,7",
            "HAN,7,5",
            "HAN,3,5",
            "HAN,7,7",
            "HAN,5,5",
            "HAN,6,8",
    })
    void shouldThrowException_WhenInvalidMove(Camp camp, int toX, int toY) {
        // given
        Horse horse = new Horse(camp);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint, Set.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,6",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,4,7",
            "HAN,3,6",
            "HAN,3,4",
            "HAN,4,3,",
    })
    void validateMoveTest(Camp camp, int toX, int toY) {
        // given
        Horse horse = new Horse(camp);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint, Set.of()))
                .doesNotThrowAnyException();
    }

    @DisplayName("마는 직선으로 한 칸, 대각선으로 한 칸 움직일 때 기물에 막힌 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN,6,7",
            "HAN,7,4",
            "HAN,6,3",
            "HAN,3,4",
    })
    void shouldThrowException_WhenBlocked(Camp camp, int toX, int toY) {
        // given
        Board board = new Board();
        Horse horse = new Horse(camp);
        Point fromPoint = new Point(5, 5);
        Point toPoint = new Point(toX, toY);
        board.placePiece(new Point(5, 6), new SoldierJol());
        board.placePiece(new Point(6, 5), new SoldierJol());
        board.placePiece(new Point(4, 5), new SoldierJol());
        board.placePiece(new Point(5, 4), new SoldierJol());
        Set<Piece> piecesOnRoute = board.getPiecesByPoint(horse.findRoute(fromPoint, toPoint));

        // when & then
        assertThatCode(() -> horse.validateMove(fromPoint, toPoint, piecesOnRoute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("마는 기물을 넘어서 이동할 수 없습니다.");
    }

    @DisplayName("마는 같은 진영의 기물을 잡을 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "CHU, false",
            "HAN, true"
    })
    void canCaptureTest(Camp camp, boolean expected) {
        // given
        Horse horse = new Horse(camp);

        // when
        boolean canCapture = horse.canCapture(new SoldierJol());

        // then
        assertThat(canCapture)
                .isSameAs(expected);
    }

    @DisplayName("마는 같은 진영의 기물을 잡을 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenCatchSameCamp() {
        // given
        Horse horse = new Horse(Camp.CHU);

        // when & then
        assertThatCode(() -> horse.validateCatch(new SoldierJol()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물을 잡을 수 없습니다.");
    }

    @DisplayName("자신의 기물 형태를 반환한다.")
    @Test
    void getPieceSymbolTest() {
        // given
        Horse horse = new Horse(Camp.CHU);

        // when
        PieceSymbol pieceSymbol = horse.getPieceSymbol();

        // then
        assertThat(pieceSymbol)
                .isSameAs(PieceSymbol.HORSE);
    }

    @DisplayName("자신의 점수를 반환한다.")
    @Test
    void getPointTest() {
        // given
        Horse horse = new Horse(Camp.CHU);

        // when
        int point = horse.getPoint();

        // then
        assertThat(point)
                .isEqualTo(5);
    }
}
