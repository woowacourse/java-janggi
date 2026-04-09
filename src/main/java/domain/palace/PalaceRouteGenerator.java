package domain.palace;

import domain.board.Position;
import domain.board.Route;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PalaceRouteGenerator {
    public List<Route> createRoutes(Position position, PieceType pieceType, TeamColor teamColor) {
        if (isKingOrGuard(pieceType)) {
            return createKingAndGuardRoutes(position, teamColor);
        }
        if (pieceType == PieceType.ROOK) {
            return createRookRoutes(position);
        }
        if (pieceType == PieceType.CANNON) {
            return createCannonRoutes(position);
        }
        if (pieceType == PieceType.PAWN) {
            return createPawnRoutes(position, teamColor);
        }
        return List.of();
    }

    private List<Route> createKingAndGuardRoutes(Position position, TeamColor teamColor) {
        final Palace palace = Palace.of(teamColor);
        return palace.connectedPositions(position).stream()
                .map(connectedPosition -> new Route(position, connectedPosition, List.of()))
                .toList();
    }

    private List<Route> createRookRoutes(Position currentPosition) {
        return findCurrentPalace(currentPosition)
                .map(palace -> createPalaceRookRoutes(currentPosition, palace))
                .orElse(List.of());
    }

    private List<Route> createPalaceRookRoutes(Position currentPosition, Palace palace) {
        if (palace.isCenter(currentPosition)) {
            return createRoutesFromPalaceCenter(currentPosition, palace);
        }
        if (palace.isCorner(currentPosition)) {
            return createRoutesFromPalaceCorner(currentPosition, palace);
        }
        return List.of();
    }

    private List<Route> createRoutesFromPalaceCenter(Position currentPosition, Palace palace) {
        return palace.corners().stream()
                .map(destination -> new Route(currentPosition, destination, List.of()))
                .toList();
    }

    private List<Route> createRoutesFromPalaceCorner(Position currentPosition, Palace palace) {
        final List<Route> routes = new ArrayList<>();
        routes.add(new Route(currentPosition, palace.center(), List.of()));

        palace.oppositeCorner(currentPosition)
                .ifPresent(oppositeCorner ->
                        routes.add(new Route(currentPosition, oppositeCorner, List.of(palace.center())))
                );
        return routes;
    }

    private List<Route> createCannonRoutes(Position currentPosition) {
        return findCurrentPalace(currentPosition)
                .map(palace -> createPalaceCannonRoutes(currentPosition, palace))
                .orElse(List.of());
    }

    private List<Route> createPalaceCannonRoutes(Position currentPosition, Palace palace) {
        final List<Route> routes = new ArrayList<>();
        if(palace.isCorner(currentPosition)) {
            palace.oppositeCorner(currentPosition)
                    .ifPresent(oppositeCorner ->
                            routes.add(new Route(currentPosition, oppositeCorner, List.of(palace.center())))
                    );
        }
        return routes;
    }

    private List<Route> createPawnRoutes(Position currentPosition, TeamColor teamColor) {
        return findCurrentPalace(currentPosition)
                .map(palace -> createPalacePawnRoutes(currentPosition, palace, teamColor))
                .orElse(List.of());
    }

    private List<Route> createPalacePawnRoutes(Position currentPosition, Palace palace, TeamColor teamColor) {
        final List<Route> candidateRoutes = createPalacePawnCandidateRoutes(currentPosition, palace);
        return filterForwardRoutesForTeam(candidateRoutes, currentPosition, palace, teamColor);
    }

    private List<Route> createPalacePawnCandidateRoutes(Position currentPosition, Palace palace) {
        if (palace.isCenter(currentPosition)) {
            return createRoutesFromPalaceCenter(currentPosition, palace);
        }
        if (palace.isCorner(currentPosition)) {
            return createRouteFromPalaceCornerToCenter(currentPosition, palace);
        }
        return List.of();
    }

    private List<Route> filterForwardRoutesForTeam(List<Route> candidateRoutes, Position currentPosition,
                                                   Palace palace, TeamColor teamColor) {
        return candidateRoutes.stream()
                .filter(route -> palace.isForwardForTeam(currentPosition, route.endPos(), teamColor))
                .toList();
    }

    private List<Route> createRouteFromPalaceCornerToCenter(Position currentPosition, Palace palace) {
        return List.of(new Route(currentPosition, palace.center(), List.of()));
    }

    private Optional<Palace> findCurrentPalace(Position position) {
        final Palace choPalace = Palace.of(TeamColor.CHO);
        if (choPalace.contains(position)) {
            return Optional.of(choPalace);
        }

        final Palace hanPalace = Palace.of(TeamColor.HAN);
        if (hanPalace.contains(position)) {
            return Optional.of(hanPalace);
        }

        return Optional.empty();
    }

    private boolean isKingOrGuard(PieceType pieceType) {
        return pieceType == PieceType.KING || pieceType == PieceType.GUARD;
    }
}
