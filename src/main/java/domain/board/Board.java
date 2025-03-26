package domain.board;

import static java.util.function.Function.identity;

import domain.Coordinate;
import domain.Piece;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Board implements PieceSearcher {

    private final Set<Piece> pieces;

    public Board(Set<Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(Coordinate departure, Coordinate arrival) {
        final Piece selectedPiece = findAt(departure)
            .orElseThrow(() -> new IllegalArgumentException("해당 좌표에는 기물이 없습니다."));

        checkPieceCanMoveToArrival(selectedPiece, arrival);
        checkArrivalIsNotSameTeam(arrival, selectedPiece);

        doMovePiece(selectedPiece, arrival);
    }

    private void doMovePiece(Piece selectedPiece, Coordinate arrival) {
        pieces.remove(selectedPiece);
        findAt(arrival).ifPresent(pieces::remove);

        pieces.add(selectedPiece.moveTo(arrival));
    }

    private void checkArrivalIsNotSameTeam(final Coordinate arrival, final Piece selectedPiece) {
        boolean isArrivalSameTeam = findAt(arrival)
            .map(p -> p.isSameTeam(selectedPiece))
            .orElse(false);

        if (isArrivalSameTeam) {
            throw new IllegalArgumentException("도착 좌표에 같은 팀 말이 있습니다.");
        }
    }

    private void checkPieceCanMoveToArrival(
        final Piece selectedPiece,
        final Coordinate arrival
    ) {
        final boolean cannotMoveToArrival = !selectedPiece.canMove(arrival, this);
        if (cannotMoveToArrival) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 좌표입니다.");
        }
    }

    @Override
    public boolean nonePiecesIn(final List<Coordinate> coordinates) {
        return coordinates.stream().noneMatch(this::existsAt);
    }

    @Override
    public List<Piece> findPiecesIn(final List<Coordinate> coordinates) {
        return coordinates.stream()
            .map(this::findAt)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    public Optional<Piece> findAt(Coordinate coordinate) {
        return pieces.stream()
            .filter(piece -> piece.isAt(coordinate))
            .findAny();
    }

    private boolean existsAt(final Coordinate coordinate) {
        return findAt(coordinate).isPresent();
    }

    public Map<Coordinate, Piece> getPieces() {
        final var coordinatePieceMap = pieces.stream()
            .collect(Collectors.toMap(Piece::getCoordinate, identity()));
        return Collections.unmodifiableMap(coordinatePieceMap);
    }
}
