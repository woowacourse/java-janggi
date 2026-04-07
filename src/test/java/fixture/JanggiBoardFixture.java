package fixture;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.piece.Team;

import java.util.ArrayList;
import java.util.List;

public class JanggiBoardFixture {

    private final List<Intersection> intersections = new ArrayList<>();

    private JanggiBoardFixture() {
    }

    public static JanggiBoardFixture builder() {
        return new JanggiBoardFixture();
    }

    public JanggiBoardFixture add(Intersection intersection) {
        this.intersections.add(intersection);
        return this;
    }

    public JanggiBoardFixture add(Intersection... intersections) {
        this.intersections.addAll(List.of(intersections));
        return this;
    }

    public JanggiBoard build() {
        return new JanggiBoard(new TestIntersectionGenerator(List.copyOf(intersections)));
    }

    public JanggiBoard build(Team currentTurn) {
        return new JanggiBoard(new TestIntersectionGenerator(List.copyOf(intersections)), currentTurn);
    }

    public static JanggiBoard generate(Intersection... intersections) {
        return JanggiBoardFixture.builder()
                .add(intersections)
                .build();
    }

    public static JanggiBoard generate(Team currentTurn, Intersection... intersections) {
        return JanggiBoardFixture.builder()
                .add(intersections)
                .build(currentTurn);
    }

}
