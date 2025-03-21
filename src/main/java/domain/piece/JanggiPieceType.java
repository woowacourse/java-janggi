package domain.piece;

import domain.JanggiPosition;
import domain.pattern.Pattern;
import domain.route.JanggiPieceRoute;
import domain.route.limited_route.EmptyRoute;
import domain.route.limited_route.궁Route;
import domain.route.limited_route.마Route;
import domain.route.limited_route.병Route;
import domain.route.limited_route.사Route;
import domain.route.limited_route.상Route;
import domain.route.limited_route.졸Route;
import domain.route.linear_route.차Route;
import domain.route.linear_route.포Route;
import java.util.List;

public enum JanggiPieceType {
    궁(0, new 궁Route()),
    마(5, new 마Route()),
    사(3, new 사Route()),
    상(3, new 상Route()),
    졸(2, new 졸Route()),
    병(2, new 병Route()),
    차(13, new 차Route()),
    포(7, new 포Route()),
    EMPTY(0, new EmptyRoute());

    private final int score;
    private final JanggiPieceRoute routes;

    JanggiPieceType(int score, JanggiPieceRoute routes) {
        this.score = score;
        this.routes = routes;
    }

    public List<Pattern> getRoute(JanggiPosition origin, JanggiPosition destination) {
        return this.routes.getRoute(origin, destination);
    }
}
