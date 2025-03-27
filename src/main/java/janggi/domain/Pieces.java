package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public Set<Route> getPossibleRoutes(final Piece piece) {
        final List<Piece> otherPieces = pieces.stream()
                .filter(p -> !p.equals(piece))
                .toList();

        return piece.getPossibleRoutes(otherPieces);
    }

    public Piece findPieceByPositionAndTeam(final Position position, final Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .filter(piece -> piece.isSameTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 우리팀 기물이 없습니다."));
    }

    public void move(final Position position, final Piece piece) {
        if (hasPieceByPosition(position.x(), position.y())) {
            kill(position.x(), position.y());
        }
        piece.move(position);
    }

    private void kill(final int x, final int y) {
        pieces.remove(findPieceByPosition(x, y));
    }

    private boolean hasPieceByPosition(final int x, final int y) {
        final Position position = new Position(x, y);
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    private Piece findPieceByPosition(final int x, final int y) {
        final Position position = new Position(x, y);
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    public boolean isGeneralDead(final Team team) {
        return pieces.stream()
                .noneMatch(piece -> piece.isGeneral() && piece.isSameTeam(team));
    }

    public boolean isAliveOnlyGeneralEachTeam() {
        return pieces.size() == 2 &&
                pieces.stream().allMatch(Piece::isGeneral);
    }

    public double calculatePiecesScoreByTeam(final Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
