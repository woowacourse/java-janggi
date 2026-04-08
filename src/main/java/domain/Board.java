package domain;

import exception.JanggiBusinessException;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board of(Map<Position, Piece> board) {
        return new Board(board);
    }

    public void movePiece(Position piecePosition, Position targetPosition) {
        Piece piece = findPieceAt(piecePosition);
        validateMoveable(piece, targetPosition);

        board.replace(targetPosition, piece.moved(targetPosition));
        board.replace(piecePosition, Piece.None(piecePosition));
    }

    public void validateMoveable(Piece piece, Position targetPosition) {
        validatePieceMoveable(piece, targetPosition);
        validateDestinationHasOwnPiece(piece, targetPosition);
        validateCannon(piece, targetPosition);
        validatePath(piece, targetPosition);
    }

    private List<Position> findAllPiecePositions() {
        return board.values().stream()
                .filter(piece -> !piece.isNone())
                .map(Piece::position)
                .toList();
    }

    private List<Position> findNonCannonPositions() {
        return board.values().stream()
                .filter(piece -> !piece.isCannon() && !piece.isNone())
                .map(Piece::position)
                .toList();
    }

    public boolean isPieceGreenTeamAt(Position Position) {
        return findPieceAt(Position).isGreenTeam();
    }

    public boolean isPieceRedTeamAt(Position Position) {
        return findPieceAt(Position).isRedTeam();
    }

    public boolean isNone(Position Position) {
        return findPieceAt(Position).isNone();
    }

    Piece findPieceAt(Position targetPosition) {
        return board.get(targetPosition);
    }

    public boolean hasGreenTeamGeneral() {
        return greenPieces().stream()
                .anyMatch(Piece::isGeneral);
    }

    public boolean hasRedTeamGeneral() {
        return redPieces().stream()
                .anyMatch(Piece::isGeneral);
    }

    public List<Piece> allFactors() {
        return board.values().stream()
                .toList();
    }

    public List<Piece> allPieces() {
        return board.values().stream()
                .filter(piece -> !piece.isNone())
                .toList();
    }

    private boolean isSameTeam(Piece piece, Piece target) {
        if (piece.isRedTeam()) {
            return target.isRedTeam();
        }

        if (piece.isGreenTeam()) {
            return target.isGreenTeam();
        }

        return false;
    }

    private List<Piece> greenPieces() {
        return board.values().stream()
                .filter(Piece::isGreenTeam)
                .toList();
    }

    private List<Piece> redPieces() {
        return board.values().stream()
                .filter(Piece::isRedTeam)
                .toList();
    }

    private void validateCannon(Piece piece, Position targetPosition) {
        if (piece.isCannon()) {
            validateCannonPath(piece, targetPosition);
            validateCannonDestination(targetPosition);
        }
    }

    private void validateCannonPath(Piece cannon, Position targetPosition) {
        if (cannon.isPathRestricted(targetPosition, findNonCannonPositions())) {
            throw new JanggiBusinessException("[ERROR] 포가 이동하기 위해선 포와 목적지 사이에 포가 아닌 기물이 정확히 하나 존재해야 합니다.");
        }
    }

    private void validateCannonDestination(Position targetPosition) {
        if (findPieceAt(targetPosition).isCannon()) {
            throw new JanggiBusinessException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    private void validatePath(Piece piece, Position targetPosition) {
        if (piece.isPathRestricted(targetPosition, findAllPiecePositions())) {
            throw new JanggiBusinessException("[ERROR] 경로에 기물이 있습니다.");
        }
    }

    private void validatePieceMoveable(Piece piece, Position targetPosition) {
        if (!piece.isMoveable(targetPosition)) {
            throw new JanggiBusinessException(
                    String.format("[ERROR] %s는 해당 위치(%d, %d)로 이동할 수 없습니다.",
                            piece.name(), targetPosition.row(), targetPosition.col())
            );
        }
    }

    private void validateDestinationHasOwnPiece(Piece piece, Position targetPosition) {
        Piece target = findPieceAt(targetPosition);
        if (isSameTeam(piece, target)) {
            throw new JanggiBusinessException("[ERROR] 이동하려는 위치에 아군 기물이 있습니다.");
        }
    }
}
