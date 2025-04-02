package janggi.board;

import janggi.coordinate.Position;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.player.Player;
import janggi.player.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionToPiece;
    private final Palaces palaces;

    private Board(final Map<Position, Piece> positionToPiece, final Palaces palaces) {
        this.positionToPiece = positionToPiece;
        this.palaces = palaces;
    }

    public static Board from(final Pieces pieces) {
        final HashMap<Position, Piece> positionToPiece = new HashMap<>();

        pieces.getPieces().forEach(piece ->
                positionToPiece.put(piece.getPosition(), piece));

        return new Board(positionToPiece, Palaces.create());
    }

    public boolean isExists(final Position position) {
        return positionToPiece.containsKey(position);
    }

    public boolean isAllyAt(final Position position, final Team team) {
        return isExists(position) && getPiece(position).isAlly(team);
    }

    public Piece getPiece(final Position position) {
        if (isExists(position)) {
            return positionToPiece.get(position);
        }
        throw new IllegalArgumentException("기물이 존재하지 않는 지점입니다.");
    }

    public void movePiece(final Player player, final Position departure, final Position destination) {
        final Piece selectedPiece = getPiece(departure);
        validatePieceOwner(selectedPiece, player);
        final Piece movedPiece = selectedPiece.move(this, destination);

        updateScore(player, destination);
        updateBoard(departure, destination, movedPiece);
    }

    public boolean isPalace(final Position position) {
        return palaces.isPalace(position);
    }

    public boolean isCenterOfPalace(final Position position) {
        return palaces.isCenter(position);
    }

    private void validatePieceOwner(final Piece piece, final Player currentTurnPlayer) {
        if (currentTurnPlayer.getTeam() == piece.getTeam()) {
            return;
        }
        throw new IllegalArgumentException("자신의 기물만을 움직일 수 있습니다.");
    }

    private void updateScore(final Player player, final Position destination) {
        if (isExists(destination)) {
            final Piece capturedPiece = positionToPiece.get(destination);
            player.addScore(capturedPiece.getScore());
        }
    }

    private void updateBoard(final Position departure, final Position destination, final Piece movedPiece) {
        positionToPiece.remove(departure);
        positionToPiece.put(destination, movedPiece);
    }

    public Map<Position, Piece> getPositionToPiece() {
        return Collections.unmodifiableMap(positionToPiece);
    }

    public Pieces getAlivePieces() {
        return Pieces.from(positionToPiece.values());
    }
}
