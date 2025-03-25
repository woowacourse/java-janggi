package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.state.PieceState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<JanggiPosition, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = JanggiBoardFactory.createJanggiBoard();
    }

    public Map<JanggiPosition, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        Piece targetPiece = getPieceFrom(afterPosition);

        validateEmptyPiece(piece);
        validateDestinationPiece(piece, targetPiece);
        validateMovePattern(piece, beforePosition, afterPosition);

        changeState(piece, targetPiece);
        changePosition(beforePosition, afterPosition);
    }

    private void validateEmptyPiece(Piece piece) {
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("기물이 없어서 이동할 수 없습니다.");
        }
    }

    private void validateDestinationPiece(Piece piece, Piece targetPiece) {
        if (piece.isSameSide(targetPiece.getSide())) {
            throw new IllegalArgumentException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void validateMovePattern(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<JanggiPosition> positions = piece.getPositionsFromPatterns(beforePosition, afterPosition);
        List<Piece> hurdlePieces = getPiecesFrom(positions);
        piece.validateMove(hurdlePieces);
    }

    private List<Piece> getPiecesFrom(List<JanggiPosition> positions) {
        List<Piece> pieces = new ArrayList<>();
        for (JanggiPosition position : positions) {
            Piece piece = getPieceFrom(position);
            if (!piece.isEmpty()) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public Piece getPieceFrom(JanggiPosition position) {
        Piece piece = janggiBoard.get(position);
        if (piece == null) {
            return new Empty();
        }
        return piece;
    }

    private void changeState(Piece piece, Piece targetPiece) {
        piece.updateState();

        if (!targetPiece.isEmpty()) {
            targetPiece.captureIfNotMySide(piece);
        }
    }

    private void changePosition(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);
        afterPosition.validateBound();

        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    public boolean isGeneralDead(PieceState targetPiece) {
        if (targetPiece == null) {
            return false;
        }
        return targetPiece.isGeneral();
    }
}
