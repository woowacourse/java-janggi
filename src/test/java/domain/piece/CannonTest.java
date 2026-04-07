package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import fixture.BoardFixtureFactory;
import static fixture.PiecePathFinder.piecesOnPath;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CannonTest {

    @ParameterizedTest
    @CsvSource(value = {
            "4,7",
            "2,7",
            "4,6",
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있지_않다면_에러를_반환한다(int column, int row) {
        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(3, 8);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateRule(from, to))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,7",
            "6,7",
            "4,9",
    })
    void 출발점을_기준으로_도착점이_수직_수평_위치에_있다면_에러를_반환하지_않는다(int column, int row) {
        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(4, 7);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> cannon.validateRule(from, to))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,4",
            "8,4"
    })
    void 움직이는_위치_사이에_기물이_1개가_아니라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 3), Coordination.of(2, 4))
                .map();

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(2, 4);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validatePath(piecesOnPath(cannon, from, to, board)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,4",
            "6,4",
            "4,2",
    })
    void 움직이는_위치_사이에_기물이_1개라면_에러를_반환하지_않는다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2,3), Coordination.of(4,4))
                .moveIgnoringValidation(Coordination.of(3,1), Coordination.of(4,3))
                .map();

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(4, 4);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> cannon.validatePath(piecesOnPath(cannon, from, to, board)))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,9",
            "9,3",
    })
    void 움직이는_위치_사이에_포라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .map();

        Cannon cannon = new Cannon(Team.HAN);
        Coordination from = Coordination.of(2, 3);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validatePath(piecesOnPath(cannon, from, to, board)))
                .isInstanceOf(PieceException.class);

    }

    @ParameterizedTest
    @CsvSource(value = {
            "5,7",
    })
    void 도착지의_기물이_아군이라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 8), Coordination.of(2, 7))
                .map();

        Cannon cannon = new Cannon(Team.CHO);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateNotSameTeam(board.get(to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,3",
            "8,8",
    })
    void 도착지의_기물이_포라면_에러를_반환한다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(1,7), Coordination.of(2,7))
                .moveIgnoringValidation(Coordination.of(3,7), Coordination.of(3,8))
                .map();


        Cannon cannon = new Cannon(Team.CHO);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validateNotSameTeam(board.get(to)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,8"
    })
    void 궁성_대각선_건너뛰기로_이동할_수_있다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 8), Coordination.of(4, 10))
                .map();

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> {
            cannon.validateRule(from, to);
            cannon.validatePath(piecesOnPath(cannon, from, to, board));
        })
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,8"
    })
    void 궁성_대각선_중앙에_기물이_없다면_이동할_수_없다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 8), Coordination.of(4, 10))
                .moveIgnoringValidation(Coordination.of(5, 9), Coordination.of(5, 8))
                .map();

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validatePath(piecesOnPath(cannon, from, to, board)))
                .isInstanceOf(PieceException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "6,8"
    })
    void 궁성_대각선_중앙의_기물이_포라면_이동할_수_없다(int column, int row) {
        Map<Coordination, Piece> board = BoardFixtureFactory.create("1", "1")
                .moveIgnoringValidation(Coordination.of(2, 8), Coordination.of(4, 10))
                .moveIgnoringValidation(Coordination.of(8, 8), Coordination.of(5, 9))
                .map();

        Cannon cannon = new Cannon(Team.CHO);
        Coordination from = Coordination.of(4, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> cannon.validatePath(piecesOnPath(cannon, from, to, board)))
                .isInstanceOf(PieceException.class);
    }
}
