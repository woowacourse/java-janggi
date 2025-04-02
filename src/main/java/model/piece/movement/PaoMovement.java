package model.piece.movement;

import java.util.List;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.Piece;
import model.piece.PieceType;

public class PaoMovement implements Movement {

    private final PalaceMovement palaceMovement = new PalaceMovement(true, true);

    @Override
    public Piece.Route findMovableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team,
        Position position, Position difference) {
        Position target = position.move(difference);
        if (isTargetPao(boardSearcher, target)) {
            throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
        }
        return movableRoute(boardSearcher, routes, team, position, target);
    }

    private Piece.Route movableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team,
        Position position, Position target) {
        Team teamOfPalace = PalaceMovement.getTeamOfPalace(position);
        if (teamOfPalace != null && palaceMovement.isOnVertexOfPalace(teamOfPalace, position)) {
            if (position.betweenOnPalaceDiagonal(teamOfPalace, target)
                && hasPieceOnCenterOfPalace(boardSearcher, teamOfPalace)) {
                return new Piece.Route(List.of(target.difference(position).toDirection()));
            }
        }
        return routes.stream()
            .filter(route -> isOverOtherPiece(boardSearcher, position, target, route))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다."));
    }

    private boolean hasPieceOnCenterOfPalace(BoardSearcher boardSearcher, Team teamOfPalace) {
        Position center = PalaceMovement.initialPositionForTeam(teamOfPalace);
        if (boardSearcher.hasPieceOn(center)) {
            Piece targetPiece = boardSearcher.get(center);
            return targetPiece.type() != PieceType.PAO;
        }
        return false;
    }

    private boolean isOverOtherPiece(BoardSearcher boardSearcher, Position position, Position target,
        Piece.Route route) {
        boolean isOvered = false;
        Position nextPos = nextPositionOnRoute(position, route);
        while (boardSearcher.isInBoard(nextPos)) {
            if (nextPos.equals(target) && isOvered) {
                return true;
            }
            if (overPiece(boardSearcher, nextPos)) {
                isOvered = true;
            }
            nextPos = nextPositionOnRoute(nextPos, route);
        }
        return false;
    }

    private boolean isTargetPao(BoardSearcher boardSearcher, Position target) {
        return boardSearcher.hasPieceOn(target) && boardSearcher.get(target).type() == PieceType.PAO;
    }

    private boolean overPiece(BoardSearcher boardSearcher, Position nextPos) {
        return boardSearcher.hasPieceOn(nextPos);
    }

    @Override
    public void validateRoute(BoardSearcher boardSearcher, Piece.Route route, Position position, Position difference) {
        boolean isOvered = false;
        Position targetPosition = position.move(difference);
        Position validatePosition = nextPositionOnRoute(position, route);
        while (!validatePosition.equals(targetPosition)) {
            if (boardSearcher.hasPieceOn(validatePosition)) {
                validateOverPao(boardSearcher, validatePosition);
                validateIsOvered(isOvered);
                isOvered = true;
            }
            validatePosition = nextPositionOnRoute(validatePosition, route);
        }
    }

    private void validateOverPao(BoardSearcher boardSearcher, Position validatePosition) {
        if (boardSearcher.get(validatePosition).type() == PieceType.PAO) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
        }
    }

    private void validateIsOvered(boolean isOvered) {
        if (isOvered) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 1개만 넘을 수 있습니다.");
        }
    }

    private Position nextPositionOnRoute(Position position, Piece.Route route) {
        return position.move(route.positions().getFirst());
    }
}
