package piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import piece.position.JanggiPosition;

public class Pieces {

    private static final String PIECE_DOESNT_EXIST = "규칙에 맞지않은 입력입니다";

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void killPieceFrom(Piece killerPiece, Pieces otherPieces) {
        otherPieces.pieces.removeIf(otherPiece ->
                killerPiece.isSamePosition(otherPiece) && !killerPiece.isSameTeam(otherPiece)
        );
    }

    public Pieces add(Pieces otherPieces) {
        List<Piece> resultPieces = new ArrayList<>(otherPieces.getPieces());
        resultPieces.addAll(pieces);
        return new Pieces(resultPieces);
    }

    public Piece move(JanggiPosition selectPiecePosition, JanggiPosition movePosition, Pieces boardAllPieces) {
        Piece piece = findPiece(selectPiecePosition);
        List<JanggiPosition> route = piece.calculateLegalRoute(selectPiecePosition, movePosition);
        Pieces piecesOnRoute = findPiecesOnRouteIncludeOtherTeam(route, piece, boardAllPieces);
        piece.move(piecesOnRoute, movePosition);
        return piece;
    }

    private Pieces findPiecesOnRouteIncludeOtherTeam(List<JanggiPosition> route, Piece movePiece,
                                                     Pieces boardAllPieces) {
        List<Piece> allPieces = boardAllPieces.getPieces();

        Map<JanggiPosition, Piece> positionPieces = allPieces.stream()
                .filter(piece -> !piece.equals(movePiece))
                .collect(Collectors.toMap(Piece::getPosition, piece -> piece));

        return findPiecesOnRouteIncludeOtherTeam(positionPieces, route);
    }

    private Pieces findPiecesOnRouteIncludeOtherTeam(Map<JanggiPosition, Piece> positionPieces,
                                                     List<JanggiPosition> onRoutePositions) {
        List<Piece> onRoutePieces = onRoutePositions.stream()
                .filter(positionPieces::containsKey)
                .map(positionPieces::get)
                .collect(Collectors.toList());

        return new Pieces(onRoutePieces);
    }

    private Piece findPiece(JanggiPosition selectPiecePosition) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(selectPiecePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PIECE_DOESNT_EXIST));
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public Piece getLastPiece() {
        return pieces.getLast();
    }

    public boolean isPieceExist(PieceType pieceType) {
        return pieces.stream()
                .anyMatch((piece) -> piece.isSameType(pieceType));
    }
}
