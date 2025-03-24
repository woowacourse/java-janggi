package domain;

import domain.piece.Piece;
import java.util.List;

public class Board {

    private final TeamBoard teamBoard;

    public Board(TeamBoard teamBoard) {
        this.teamBoard = teamBoard;
    }

    // TODO : movePiece 메소드 분리
    // 1. 현재 위치에 있는 기물 가져오기
    // 2.
    public void movePiece(Team team, BoardLocation current, BoardLocation destination) {
        Piece piece = teamBoard.findByLocation(current);
        validateTeam(piece, team);

        // TODO : validate 메소드로 분리하기
        if (!piece.isMovable(current, destination)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }

        List<BoardLocation> allPath = piece.createAllPath(current, destination);
        List<Piece> pathPiece = teamBoard.extractPathPiece(allPath);
        if (!piece.canArrive(pathPiece)){
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }

        if (teamBoard.contains(destination)) {
            Piece destinationPiece = teamBoard.findByLocation(destination);
            if (!piece.canDestination(destinationPiece)){
                throw new IllegalArgumentException("[ERROR] 해당 기물은 목적지로 이동할 수 없습니다.");
            }
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
