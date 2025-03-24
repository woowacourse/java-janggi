package team;

import direction.Point;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class Player {

    private final List<Piece> pieces;
    private final Team team;

    public Player(List<Piece> pieces, Team team) {
        this.pieces = pieces;
        this.team = team;
    }

    public boolean isContainPiece(Point position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isEqualPositionWith(position));
    }

    public Piece getPieceByPoint(Point point) {
        return pieces.stream()
                .filter(piece -> piece.isEqualPositionWith(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean isTeam(Team targetTeam) {
        return team.equals(targetTeam);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void play(Pieces allPieces, Point start, Point end) {
        Piece piece = getPieceByPoint(start);
        piece.move(allPieces, end);
    }

    public void validateAlreadyPlayerPieceInPosition(Point end) {
        if (isAlreadyPlayerPieceInPosition(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }

    private boolean isAlreadyPlayerPieceInPosition(Point point) {
        return pieces.stream()
                .anyMatch(piece -> piece.isEqualPositionWith(point));
    }
}
