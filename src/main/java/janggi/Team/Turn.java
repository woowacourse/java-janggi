package janggi.Team;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Turn {
    private final Deque<Team> orders;

    public Turn() {
        this.orders = new ArrayDeque<>(List.of(Team.CHO, Team.HAN));
    }

    public Team getCurrentTeam() {
        return orders.peek();
    }

    public void turnOver() {
        final Team currentTeam = orders.poll();
        orders.offer(currentTeam);
    }

    public Deque<Team> getOrders() {
        return orders;
    }
}
