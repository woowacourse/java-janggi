package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Pieces {

    public final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public Optional<Piece> findPieceOfNullable(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Piece findPieceBy(Position position) {
        return pieces.computeIfAbsent(position, key -> {
            throw new IllegalArgumentException("해당 위치에 기물 없음");
        });
    }
/*
    public void validateCanMove(Position departure, Position arrival) {
        List<Position> positions = findDirectionOfPiece(departure, arrival); // 목적지 찾기
        validateMiddleDirection(arrival, positions); // 중간 목적지
        validateDestination(departure, arrival); // 같은 팀이라면 에러, 다른 팀이라면 제거
        movePosition(departure, arrival);
    }

/*
    private List<Position> findDirectionOfPiece(Position departure, Position arrival) {
        Piece piece = findPieceBy(departure);
        List<Position> positions1 = piece.calculateAllDirection(departure, arrival);
        return findResults.stream()
                .filter(positions -> positions.contains(arrival))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }



 */
    private void validateMiddleDirection(Position arrival, List<Position> positions) {
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

    private void validateDestination(Position departure, Position arrival) {
        Optional<Piece> hasExistPiece = findPieceOptional(arrival);

        if (hasExistPiece.isEmpty()) {
            return;
        }

        Piece departurePositionPiece = pieces.get(departure);
        Piece currentArrivalPositionPiece = pieces.get(arrival);

        if (departurePositionPiece.getTeam() == currentArrivalPositionPiece.getTeam()) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
        pieces.remove(arrival);
    }

    /*
    public void validateCannonMove(Piece piece, Position destination) {
        List<Position> positions = findDirectionOfPiece(piece, destination); // 목적지 찾기
        validateMiddleDirectionOfCannon(destination, positions);
        validateDestinationOfCannon(piece, destination); // 같은 팀이라면 에러, 다른 팀이라면 제거
        movePosition(piece, destination);
    }


     */
    private void validateMiddleDirectionOfCannon(Position destination, List<Position> positions) {
        long pieceCount = positions.stream()
            .filter(this::isAlreadyExist)
            .filter(position -> !position.equals(destination))
            .count();

        boolean hasCannon = positions.stream()
            .anyMatch(this::hasCannon);

        if (pieceCount == 1 && !hasCannon) {
            return;
        }
        throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
    }

    private boolean hasCannon(Position position) {
        Piece findPiece = pieces.get(position);
        return findPiece.isCannon();
    }

    private void validateDestinationOfCannon(Piece myPiece, Position destination) {
        Optional<Piece> hasExistPiece = findPieceOptional(destination);
        if (hasExistPiece.isEmpty()) {
            return;
        }
        Piece existPiece = hasExistPiece.get();
        if (myPiece.getTeam() == existPiece.getTeam() || existPiece.isCannon()) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
        pieces.remove(existPiece);
    }

    private Optional<Piece> findPieceOptional(Position destination) {
        return Optional.ofNullable(pieces.get(destination));
    }

    private void movePosition(Position departure, Position arrival) {
        Piece piece = pieces.get(departure);
        pieces.put(arrival, piece);
        pieces.remove(departure);
    }
}
