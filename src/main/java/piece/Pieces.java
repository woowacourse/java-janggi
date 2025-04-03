package piece;

import direction.Point;
import java.util.List;
import team.Team;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void killableMove(Team currentTeam, Point start, Point end) {
        validateExistMyPieceOnDestination(end, currentTeam);

        Piece piece = findByPointWithTeam(start, currentTeam);
        piece.move(this, end);

        existOppositePieceKill(currentTeam, end);
    }

    private void existOppositePieceKill(Team currentTeam, Point end) {
        if (isExistPieceInPointWithTeam(end, currentTeam.oppsite())) {
            Piece enemyPiece = findByPointWithTeam(end, currentTeam.oppsite());
            pieces.remove(enemyPiece);
        }
    }

    private void validateExistMyPieceOnDestination(Point end, Team currentTeam) {
        if (isExistPieceInPointWithTeam(end, currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }

    public Piece findByPointWithTeam(Point point, Team currentTeam) {
        return pieces.stream()
                .filter(piece -> piece.isSameTeam(currentTeam))
                .filter(piece -> piece.isSamePoint(point))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다."));
    }

    public Piece findByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.isSamePoint(point))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean isExistPieceInPoint(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePoint(point));
    }

    public boolean isExistPieceInPointWithTeam(Point point, Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .anyMatch(piece -> piece.isSamePoint(point));
    }

    public boolean isKingDead(Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .noneMatch(piece -> PieceType.GENERAL.equals(piece.type()));
    }

    public double calculateScore(Team team) {
        double sum = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::score)
                .sum();

        if (team.equals(Team.HAN)) {
            return sum + 1.5;
        }

        return sum;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
