package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MoveRuleTest {

    @Test
    @DisplayName("초나라 졸은 위양옆으로 갈 수 있다")
    void 초나라_졸_이동규칙() {
        //given
        MoveRule moveRule = new MoveRule();
        Piece zol = new Piece(Team.CHO, PieceType.ZOL);
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.LEFT));
        Route route3 = new Route(List.of(Direction.RIGHT));

        List<Route> routes = List.of(route1,route2,route3);

        //when
        List<Route> zolPaths = moveRule.findRoute(zol);

        //then
        assertThat(zolPaths).isEqualTo(routes);
    }

    @Test
    @DisplayName("한나라 졸은 아래양옆으로 갈 수 있다")
    void 한나라_졸_이동규칙() {
        //given
        MoveRule moveRule = new MoveRule();
        Piece zol = new Piece(Team.HAN, PieceType.ZOL);
        Route route1 = new Route(List.of(Direction.DOWN));
        Route route2 = new Route(List.of(Direction.LEFT));
        Route route3 = new Route(List.of(Direction.RIGHT));

        List<Route> routes = List.of(route1,route2,route3);

        //when
        List<Route> zolPaths = moveRule.findRoute(zol);

        //then
        assertThat(zolPaths).isEqualTo(routes);
    }

    @Test
    @DisplayName("마는 직선으로 한 번 대각선으로 한 번 갈 수 있다")
    void 마_이동규칙() {
        //given
        MoveRule moveRule = new MoveRule();
        Piece ma = new Piece(Team.CHO, PieceType.MA);
        Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT));
        Route route2 = new Route( List.of(Direction.UP, Direction.UP_RIGHT));
        Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT));
        Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT));
        Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT));
        Route route6 = new Route( List.of(Direction.DOWN, Direction.DOWN_LEFT));
        Route route7 = new Route( List.of(Direction.LEFT, Direction.DOWN_LEFT));
        Route route8 = new Route( List.of(Direction.LEFT, Direction.UP_LEFT));

        List<Route> routes = List.of(route1,route2,route3,route4,route5,route6,route7,route8);
        //when
        List<Route> maRoutes = moveRule.findRoute(ma);

        //then
        assertThat(maRoutes).isEqualTo(routes);
    }

    @Test
    @DisplayName("상은 직선으로 한 번 대각선으로 두 번 갈 수 있다")
    void 상_이동규칙() {
        //given
        MoveRule moveRule = new MoveRule();
        Piece sang = new Piece(Team.CHO, PieceType.SANG);
        Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT));
        Route route2 = new Route( List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route6 = new Route( List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route7 = new Route( List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route8 = new Route( List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT));
        List<Route> routes = List.of(route1,route2,route3,route4,route5,route6,route7,route8);

        //when
        List<Route> sangRoutes = moveRule.findRoute(sang);

        //then
        assertThat(sangRoutes).isEqualTo(routes);
    }

    @Test
    @DisplayName("왕은 직선과 대각선으로 갈 수 있다.")
    void 왕의_이동규칙(){
        //given
        MoveRule moveRule = new MoveRule();
        Piece king = new Piece(Team.CHO, PieceType.KING);
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        Route route5 = new Route(List.of(Direction.UP_LEFT));
        Route route6 = new Route(List.of(Direction.UP_RIGHT));
        Route route7 = new Route(List.of(Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> kingRoutes = moveRule.findRoute(king);

        //then
        assertThat(kingRoutes).isEqualTo(routes);
    }

    @Test
    @DisplayName("사는 직선과 대각선으로 갈 수 있다")
    void 사의_이동규칙(){
        //given
        MoveRule moveRule = new MoveRule();
        Piece sa = new Piece(Team.CHO, PieceType.SA);
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        Route route5 = new Route(List.of(Direction.UP_LEFT));
        Route route6 = new Route(List.of(Direction.UP_RIGHT));
        Route route7 = new Route(List.of(Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
        List<Route> routes = List.of(route1, route2, route3, route4, route5, route6, route7, route8);

        //when
        List<Route> saRoutes = moveRule.findRoute(sa);

        //then
        assertThat(saRoutes).isEqualTo(routes);
    }

    @Test
    @DisplayName("차는 직선으로 갈 수 있다")
    void 차의_이동규칙(){
        //given
        MoveRule moveRule = new MoveRule();
        Piece cha = new Piece(Team.CHO, PieceType.CHA);
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        List<Route> routes = List.of(route1, route2, route3, route4);

        //when
        List<Route> chaRoutes = moveRule.findRoute(cha);

        //then
        assertThat(chaRoutes).isEqualTo(routes);
    }

    @Test
    @DisplayName("차는 직선으로 갈 수 있다")
    void 포의_이동규칙(){
        //given
        MoveRule moveRule = new MoveRule();
        Piece po = new Piece(Team.CHO, PieceType.PO);
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        List<Route> routes = List.of(route1, route2, route3, route4);

        //when
        List<Route> poRoutes = moveRule.findRoute(po);

        //then
        assertThat(poRoutes).isEqualTo(routes);
    }

}
