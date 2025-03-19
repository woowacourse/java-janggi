package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pieces {

    public final List<Piece> pieces;

    private Pieces() {
        List<Piece> generate = PieceInitializer.generate();
        this.pieces = new ArrayList<>(generate);
    }

    public static Pieces createAndInit() {
        return new Pieces();
    }

    public boolean isAlreadyExist(Position position) {
        return pieces.stream()
            .anyMatch(piece -> piece.getPosition().equals(position));
    }

    public Piece findPiece(Position position) {
        return pieces.stream()
            .filter(piece -> piece.getPosition().equals(position))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물 없음"));
    }

    public void validateCanMove(Piece piece, Position destination) {
        List<Position> positions = findDirectionOfPiece(piece, destination); // 목적지 찾기
        validateMiddleDirection(destination, positions); // 중간 목적지
        validateDestination(piece, destination); // 같은 팀이라면 에러, 다른 팀이라면 제거
        movePosition(piece, destination);
    }

    private void validateMiddleDirection(Position destination, List<Position> positions) {
        positions.stream()
            .filter(this::isAlreadyExist)
            .filter(position -> !position.equals(destination))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }


    private void validateDestination(Piece myPiece, Position destination) {
        Optional<Piece> hasExistPiece = this.pieces.stream()
            .filter(piece -> piece.getPosition().equals(destination))
            .findFirst();
        if (hasExistPiece.isEmpty()) {
            return;
        }
        Piece existPiece = hasExistPiece.get();
        if (myPiece.getTeam() == existPiece.getTeam()) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
        pieces.remove(existPiece);
    }

    private void movePosition(Piece myPiece, Position destination) {
        myPiece.changePosition(destination);
    }

    public List<Position> findDirectionOfPiece(Piece piece, Position destination) {
        List<List<Position>> findResults = piece.calculateAllDirection();
        return findResults.stream()
            .filter(positions -> positions.contains(destination))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물 없음"));
    }
}
