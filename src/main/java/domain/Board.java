package domain;

import domain.piece.Piece;
import java.util.List;

public class Board {

    private final TeamBoard teamBoard;

    public Board(TeamBoard teamBoard) {
        this.teamBoard = teamBoard;
    }

    public void moveChoPiece(Team team, BoardLocation current, BoardLocation destination) {
        Piece piece = teamBoard.findByLocation(current);
        validateTeam(piece, team);

        if (!piece.isMovable(current, destination)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }

        List<BoardLocation> allPath = piece.createAllPath(current, destination);
        if (piece.isCannon()) {
            teamBoard.validateCannon(allPath, destination);
        }
        teamBoard.validatePaths(allPath);
        teamBoard.validateDestinationAlly(piece, destination);
        teamBoard.removeIfHas(destination);
        teamBoard.move(current, destination);
    }

    private void validateTeam(Piece piece, Team team) {
        if (piece.isEqualTeam(team)) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다");
    }
}
