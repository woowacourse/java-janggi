package model.piece.goongsungpiece;

import java.util.Map;
import java.util.Map.Entry;
import model.Team;
import model.piece.Piece;
import model.piece.PieceInfo;

public class Pho extends GoongsungAdvantagePiece {

    public Pho(Team team) {
        super(team);
        pieceInfo = PieceInfo.PHO;
    }

    @Override
    public boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        if (piecesOnPathWithTargetOrNot.size() >= 3) {
            return false;
        }
        if (piecesOnPathWithTargetOrNot.size() == 2) {
            return noPhoInObstacles(piecesOnPathWithTargetOrNot)
                    && oneObstacleInTargetPoint(piecesOnPathWithTargetOrNot)
                    && isEnemy(piecesOnPathWithTargetOrNot);
        }
        if (piecesOnPathWithTargetOrNot.size() == 1) {
            return !isTargetPoint(piecesOnPathWithTargetOrNot) &&
                    noPhoInObstacles(piecesOnPathWithTargetOrNot);
        }
        return true;
    }

    private Boolean isTargetPoint(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.values()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[ERROR] 종점에 위치한 장애물이 존재하지 않습니다.\n"));
    }

    private boolean noPhoInObstacles(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot
                .keySet()
                .stream()
                .noneMatch(piece -> piece instanceof Pho);
    }

    private boolean oneObstacleInTargetPoint(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.values()
                .stream()
                .anyMatch(isTargetPoint -> isTargetPoint);
    }

    private boolean isEnemy(Map<Piece, Boolean> piecesOnPathWithTargetOrNot) {
        return piecesOnPathWithTargetOrNot.entrySet()
                .stream()
                .filter(Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("[ERROR] 종점에 위치한 장애물이 존재하지 않습니다.\n"))
                .getKey()
                .getTeam() != this.team;
    }
}
