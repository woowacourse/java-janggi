package team;

import direction.Point;
import java.util.List;
import java.util.Optional;
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
                .anyMatch(piece -> piece.getPosition().equals(position));
    }

    public Optional<Piece> findPieceBy(Point point) {
        return pieces.stream()
                .filter(piece -> piece.getPosition().equals(point))
                .findFirst();
    }

    public boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void move(Pieces allPieces, Point start, Point end) {
        validateExistMyPieceOnDestination(end);

        Piece piece = findPieceBy(start).get();
        piece.move(allPieces, end);
    }

    private void validateExistMyPieceOnDestination(Point end) {
        if (findPieceBy(end).isPresent()) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }
}
