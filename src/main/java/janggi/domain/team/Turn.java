package janggi.domain.team;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Turn {
    private static final List<TeamType> INITIAL_ORDERS = List.of(TeamType.CHO, TeamType.HAN);
    private final Deque<TeamType> orders;

    public Turn() {
        this.orders = new ArrayDeque<>(INITIAL_ORDERS);
    }

    public Turn(List<TeamType> orders) {
        this.orders = new ArrayDeque<>(orders);
    }

    public TeamType getCurrentTeam() {
        return orders.peek();
    }

    public void turnOver() {
        final TeamType currentTeamType = orders.poll();
        orders.offer(currentTeamType);
    }

    public boolean isLastOrder(TeamType teamType) {
        return teamType == INITIAL_ORDERS.getLast();
    }

    public Deque<TeamType> getOrders() {
        return orders;
    }
}
