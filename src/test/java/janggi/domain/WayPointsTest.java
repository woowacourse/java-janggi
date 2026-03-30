package janggi.domain;

import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class WayPointsTest {

    @Test
    void 경로의_모든_포지션과_하나의_포지션이라도_겹친다면_true를_반환한다(){
        Position position = Position.of(3, 2);

        WayPoints wayPoints = new WayPoints(List.of(Position.of(3, 2), Position.of(2, 2)));
        boolean blocked = wayPoints.isBlocked(position);

        Assertions.assertThat(blocked).isTrue();
    }

    @Test
    void 경로의_모든_포지션과_하나의_포지션이라도_겹치지_않는다면_false를_반환한다(){
        Position position = Position.of(3, 3);

        WayPoints wayPoints = new WayPoints(List.of(Position.of(3, 2), Position.of(2, 2)));
        boolean blocked = wayPoints.isBlocked(position);

        Assertions.assertThat(blocked).isFalse();
    }
}