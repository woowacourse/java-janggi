package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChaTest {

    @Test
    void 시작_좌표와_끝_좌표가_같은_선_상에_존재하지_않으면_에러가_발생한다() {
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(2,3);
        Position end = new Position(3,4);

        assertThatThrownBy(() -> cha.findRoute(start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 도착 지점이 아닙니다.");
    }

    @Test
    void 끝_좌표가_시작_좌표의_오른쪽으로_향할_때_올바른_경로를_생성한다(){
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3,3);
        Position end = new Position(3,5);

        List<Position> routes = cha.findRoute(start, end);

        assertThat(routes).containsExactly(
                start,
                new Position(3, 4),
                end
        );
    }

    @Test
    void 끝_좌표가_시작_좌표의_왼쪽으로_향할_때_올바른_경로를_생성한다(){
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3,3);
        Position end = new Position(3,1);

        List<Position> routes = cha.findRoute(start, end);

        assertThat(routes).containsExactly(
                start,
                new Position(3, 2),
                end
        );
    }

    @Test
    void 끝_좌표가_시작_좌표의_위로_향할_때_올바른_경로를_생성한다(){
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3,3);
        Position end = new Position(1,3);

        List<Position> routes = cha.findRoute(start, end);

        assertThat(routes).containsExactly(
                start,
                new Position(2, 3),
                end
        );
    }

    @Test
    void 끝_좌표가_시작_좌표의_아래로_향할_때_올바른_경로를_생성한다(){
        Cha cha = new Cha(Side.CHO);
        Position start = new Position(3,3);
        Position end = new Position(5,3);

        List<Position> routes = cha.findRoute(start, end);

        assertThat(routes).containsExactly(
                start,
                new Position(4, 3),
                end
        );
    }
}