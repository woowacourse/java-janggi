package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChaTest {

    @Test
    void 시작_좌표와_끝_좌표가_같은_선_상에_존재하지_않으면_에러가_발생한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(2, 3);
        Position end = new Position(5, 6);

        assertThatThrownBy(() -> cha.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @Test
    void 끝_좌표가_시작_좌표의_오른쪽으로_향할_때_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3, 3);
        Position end = new Position(3, 5);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(3, 4),
                end
        )));
    }

    @Test
    void 끝_좌표가_시작_좌표의_왼쪽으로_향할_때_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3, 3);
        Position end = new Position(3, 1);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(3, 2),
                end
        )));
    }

    @Test
    void 끝_좌표가_시작_좌표의_위로_향할_때_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3, 3);
        Position end = new Position(1, 3);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(2, 3),
                end
        )));
    }

    @Test
    void 끝_좌표가_시작_좌표의_아래로_향할_때_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3, 3);
        Position end = new Position(5, 3);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(4, 3),
                end
        )));
    }

    @Test
    void 궁성_위_오른쪽_대각선에_해당되는_시작과_끝좌표에_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(3, 4);
        Position end = new Position(1, 6);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(2, 5),
                end
        )));
    }

    @Test
    void 궁성_위_왼쪽_대각선에_해당되는_시작과_끝좌표에_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(3, 6);
        Position end = new Position(1, 4);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(2, 5),
                end
        )));
    }

    @Test
    void 궁성_아래_오른쪽_대각선에_해당되는_시작과_끝좌표에_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(1, 4);
        Position end = new Position(3, 6);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(2, 5),
                end
        )));
    }

    @Test
    void 궁성_아래_왼쪽_대각선에_해당되는_시작과_끝좌표에_올바른_경로를_생성한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(3, 4);
        Position end = new Position(1, 6);

        Route routes = cha.findRoute(start, end);

        assertThat(routes).isEqualTo(new Route(List.of(
                start,
                new Position(2, 5),
                end
        )));
    }

    @Test
    void 대각선_이동이_궁성_밖을_포함할_때_에러가_발생한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(1, 4);
        Position end = new Position(4, 7);

        assertThatThrownBy(() -> cha.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @Test
    void 궁성_내의_대각선_이동과_궁성_밖_직선_이동이_함께_있을_때_에러가_발생한다() {
        Cha cha = new Cha(Side.CHO);

        Position start = new Position(1, 4);
        Position end = new Position(4, 6);

        assertThatThrownBy(() -> cha.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 2, 4",
            "1, 5, 2, 6",
            "2, 4, 1, 5",
            "2, 6, 3, 5",
            "9, 4, 8, 5",
            "9, 6, 10, 5"
    })
    void 궁성_내_십자_위치에서의_대각선_이동이_있을_때_에러가_발생한다(int startX, int startY, int endX, int endY) {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(startX, startY);
        Position end = new Position(endX, endY);

        assertThatThrownBy(() -> cha.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }
}
