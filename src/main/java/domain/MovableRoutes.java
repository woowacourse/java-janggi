package domain;

import java.util.List;

public record MovableRoutes(List<Route> routes, boolean hasKingDestination) {}
