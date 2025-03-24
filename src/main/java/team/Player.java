package team;

import direction.Point;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class Player {

    private final Pieces pieces;
    private final Team team;

    public Player(List<Piece> pieces, Team team) {
        this.pieces = new Pieces(pieces);
        this.team = team;
    }

    public void move(Pieces allPieces, Point start, Point end) {
        validateExistMyPieceOnDestination(end);

        Piece piece = pieces.findByPoint(start);
        piece.move(allPieces, end);
    }

    private void validateExistMyPieceOnDestination(Point end) {
        if (pieces.isExistPieceIn(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }

    public boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public List<Piece> getPieces() {
        return pieces.getPieces();
    }
}
