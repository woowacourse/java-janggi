package domain.board;

import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void movePiece(PieceType pieceType, Position source, Position destination) {
        validateMove(source, destination);
        Piece piece = getPieceByPosition(source);
        validateIsMyPieceType(piece, pieceType);

        board.remove(source);
        board.put(destination, piece);
    }

    public Piece getPieceByPosition(Position position) {
        return board.getOrDefault(position, Empty.getInstance());
    }

    public List<Piece> getPieceByColor(PieceColor color) {
        return board.values().stream().
                filter(piece -> piece.getColor() == color).
                toList();
    }

    public boolean isGeneralKilledByColor(PieceColor color) {
        return board.values().stream()
                .noneMatch(piece -> piece.getColor() == color);
    }

    private void validateMove(Position source, Position destination) {
        Piece sourcePiece = getPieceByPosition(source);
        Piece destinationPiece = getPieceByPosition(destination);
        MovePath movePath = new MovePath(source, destination);
        boolean isValidDestination = sourcePiece.isValidMovement(movePath);

        List<Position> route = sourcePiece.findAllRoute(movePath);
        List<Piece> piecesOnRoute = getPiecesOnRoute(route);
        boolean canMove = sourcePiece.canMove(destinationPiece, piecesOnRoute);

        if (!isValidDestination || !canMove) {
            throw new IllegalArgumentException("해당 위치로는 이동할 수 없습니다.");
        }
    }

    private void validateIsMyPieceType(Piece piece, PieceType pieceType) {
        if (!piece.isSamePieceType(pieceType)) {
            throw new IllegalArgumentException("움직이려는 기물이 일치하지 않습니다.");
        }
    }

    private List<Piece> getPiecesOnRoute(List<Position> positions) {
        return positions.stream()
                .map(this::getPieceByPosition)
                .toList();
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
