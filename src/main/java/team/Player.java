package team;

import direction.Point;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class Player {

    private final Pieces pieces;
    private final Team team;

    public Player(Pieces pieces, Team team) {
        this.pieces = pieces;
        this.team = team;
    }

    public boolean isContainPiece(Point point) {
        return pieces.isContainPiece(point);
    }

    public Piece getPieceByPoint(Point point) {
        return pieces.getByPoint(point);
    }

    public boolean isTeam(Team targetTeam) {
        return team.equals(targetTeam);
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }

    public void play(Pieces allPieces, Point start, Point end) {
        Piece piece = getPieceByPoint(start);
        piece.validateDestination(end);
        piece.checkPaths(allPieces, end);
        piece.move(end);
    }

    public void validateAlreadyPlayerPieceInPosition(Point end) {
        if (isAlreadyPlayerPieceInPosition(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }

    private boolean isAlreadyPlayerPieceInPosition(Point point) {
        return pieces.isAlreadyPieceInPosition(point);
    }
}
