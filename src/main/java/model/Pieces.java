package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.piece.Piece;
import model.position.Position;

public class Pieces {

    public final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public boolean isGeneralAlive() {
        return pieces.values().stream()
            .filter(Piece::isGeneral)
            .count() == 2;
    }

    public Optional<Piece> findPieceOfNullable(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Piece findPieceBy(Position position) {
        return pieces.computeIfAbsent(position, key -> {
            throw new IllegalArgumentException("해당 위치에 기물 없음");
        });
    }

    public void move(Position departure, Position arrival) {
        List<Position> directionToArrive = findDirectionOfPiece(departure, arrival);
        validateMiddleDirection(departure, arrival, directionToArrive);
        validateAnotherPieceOfArrival(departure, arrival);
        moveDepartureToArrival(departure, arrival);
    }

    private void validateMiddleDirection(Position departure, Position arrival, List<Position> directionToArrive) {
        Piece piece = pieces.get(departure);
        if (piece.isCannon()) {
            validateAnotherPieceOfMiddleDirectionForCannon(arrival, directionToArrive);
            return;
        }
        validateAnotherPieceOfMiddleDirection(arrival, directionToArrive);
    }

    private List<Position> findDirectionOfPiece(Position departure, Position arrival) {
        Piece piece = findPieceBy(departure);
        return piece.calculateAllDirection(departure, arrival);
    }

    private void validateAnotherPieceOfMiddleDirection(Position arrival, List<Position> positions) {
        boolean isAlreadyExist = positions.stream()
                .filter(this::isAlreadyExist)
                .anyMatch(position -> !position.equals(arrival));
        if (isAlreadyExist) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

    private boolean isAlreadyExist(Position position) {
        return pieces.get(position) != null;
    }

    private void validateAnotherPieceOfArrival(Position departure, Position arrival) {
        if (pieces.get(arrival) == null) {
            return;
        }
        validateSameTeamExistOfArrival(departure, arrival);
        pieces.remove(arrival);
    }

    private void validateSameTeamExistOfArrival(Position departure, Position arrival) {
        Piece departurePositionPiece = pieces.get(departure);
        Piece currentArrivalPositionPiece = pieces.get(arrival);
        if (departurePositionPiece.isSameTeam(currentArrivalPositionPiece)) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

    private void validateAnotherPieceOfMiddleDirectionForCannon(Position arrival, List<Position> positions) {
        long pieceCount = positions.stream()
            .filter(this::isAlreadyExist)
            .filter(position -> !position.equals(arrival))
            .count();

        boolean hasCannon = positions.stream()
            .anyMatch(this::hasCannon);

        if (pieceCount == 1 && !hasCannon) {
            return;
        }
        throw new IllegalArgumentException("같은 포 끼리는 넘을 수 없습니다.");
    }

    private boolean hasCannon(Position position) {
        Piece findPiece = pieces.get(position);
        if (findPiece == null) {
            return false;
        }
        return findPiece.isCannon();
    }

    private void moveDepartureToArrival(Position departure, Position arrival) {
        Piece piece = pieces.get(departure);
        pieces.put(arrival, piece);
        pieces.remove(departure);
    }
}
