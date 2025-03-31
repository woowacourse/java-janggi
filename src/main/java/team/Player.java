package team;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class Player {

    private Pieces pieces;
    private int score;
    private final Team team;

    public Player(Pieces pieces, int score, Team team) {
        this.pieces = pieces;
        this.score = score;
        this.team = team;
    }

    public Pieces move(Pieces oppositeTeamPieces, Point start, Point end) {
        validateExistMyPieceOnDestination(end);
        validateExistPieceOnSelectPoint(start);

        Piece piece = pieces.findByPoint(start);
        List<Piece> allPieces = new ArrayList<>(oppositeTeamPieces.getPieces());
        allPieces.addAll(pieces.getPieces());
        piece.move(new Pieces(allPieces), end);

        score += piece.killableToKill(oppositeTeamPieces);

        return oppositeTeamPieces;
    }

    private void validateExistPieceOnSelectPoint(Point start) {
        if (!pieces.isExistPieceIn(start)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateExistMyPieceOnDestination(Point end) {
        if (pieces.isExistPieceIn(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }

    public boolean isKingDead() {
        return pieces.isKingDead();
    }

    public boolean isContainPiece(Point point) {
        return pieces.isExistPieceIn(point);
    }

    public boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }

    public void updatePieceStatus(final Pieces oppositeTeamPieces) {
        pieces = oppositeTeamPieces;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }
}
