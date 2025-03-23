package domain.board;

import domain.Coordinate;
import domain.piece.Piece;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board implements PieceFinder {

    private final Map<Coordinate, Piece> pieces;

    public Board(Map<Coordinate, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(Coordinate departure, Coordinate arrival) {
        checkDeparturePieceExisting(departure);
        final Piece selectedPiece = pieces.get(departure);

        checkArrivalIsMovable(departure, arrival, selectedPiece);
        checkArrivalIsNotSameTeam(arrival, selectedPiece);

        doMovePiece(departure, arrival, selectedPiece);
    }

    private void doMovePiece(Coordinate departure, Coordinate arrival, Piece piece) {
        pieces.remove(departure);
        pieces.merge(arrival, piece, (existing, selected) -> selected);
    }

    private void checkDeparturePieceExisting(final Coordinate departure) {
        if (!pieces.containsKey(departure)) {
            throw new IllegalArgumentException("해당 좌표에는 기물이 없습니다.");
        }
    }

    private void checkArrivalIsNotSameTeam(final Coordinate arrival, final Piece selectedPiece) {
        boolean isArrivalSameTeam = findAt(arrival)
            .map(p -> p.isSameTeam(selectedPiece))
            .orElse(false);

        if (isArrivalSameTeam) {
            throw new IllegalArgumentException("도착 좌표에 같은 팀 말이 있습니다.");
        }
    }

    private void checkArrivalIsMovable(
        final Coordinate departure,
        final Coordinate arrival,
        final Piece selectedPiece
    ) {
        final boolean cannotMoveToArrival = !selectedPiece.canMove(this, departure, arrival);
        if (cannotMoveToArrival) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 좌표입니다.");
        }
    }

    @Override
    public boolean nonePiecesIn(final List<Coordinate> coordinates) {
        return coordinates.stream().noneMatch(pieces::containsKey);
    }

    @Override
    public List<Piece> findPiecesIn(final List<Coordinate> coordinates) {
        return coordinates.stream()
            .filter(pieces::containsKey)
            .map(pieces::get)
            .toList();
    }

    public Optional<Piece> findAt(Coordinate coordinate) {
        return Optional.ofNullable(pieces.get(coordinate));
    }

    public Map<Coordinate, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
