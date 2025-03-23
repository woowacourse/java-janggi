package domain;

import domain.piece.Piece;
import java.util.List;

public class Board {

    private final TeamBoard teamBoard;

    public Board(TeamBoard teamBoard) {
        this.teamBoard = teamBoard;
    }

    public void movePiece(Team team, BoardLocation current, BoardLocation destination) {
        Piece piece = teamBoard.findByLocation(current);
        validateTeam(piece, team);

        if (!piece.isMovable(current, destination)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }

        List<BoardLocation> allPath = piece.createAllPath(current, destination);
        List<Piece> pathPiece = teamBoard.extractPathPiece(allPath);
        if (!piece.canArrive(pathPiece)){
            throw new IllegalArgumentException("[ERROR] 해당 기물은 이동경로가 막혀있어 이동할 수 없습니다");
        }
        Piece destinationPiece = teamBoard.findByLocation(destination);
        if (!piece.canDestination(destinationPiece)){
            throw new IllegalArgumentException("[ERROR] 목적지에 이동할 수 없습니다.");
        }

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
