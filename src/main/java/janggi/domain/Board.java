package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board initialize(final List<Piece> pieces) {
        HashMap<Position, Piece> positionToPiece = new HashMap<>();

        pieces.forEach(piece -> positionToPiece.put(piece.getPosition(), piece));

        return new Board(positionToPiece);
    }

    public boolean isExists(final Position position) {
        return positionToPiece.containsKey(position);
    }

    public void movePiece(final Player player, final Position departure, final Position destination) {
        Piece allyPiece = getPiece(departure);
        checkAllyPiece(player, allyPiece);
        Piece movedPiece = allyPiece.move(this, destination);
        positionToPiece.remove(departure);
        updateBoard(destination, movedPiece);
    }

    private void checkAllyPiece(final Player player, final Piece allyPiece) {
        if (allyPiece.isEnemy(player.getTeam())) {
            throw new IllegalArgumentException("적의 기물을 선택할 수 없습니다.");
        }
    }

    private void updateBoard(final Position destination, final Piece movedPiece) {
        positionToPiece.put(destination, movedPiece);
    }

    public boolean isAlly(final Position position, final Team team) {
        return getPiece(position).isAlly(team);
    }

    public boolean isEnemy(final Position position, final Team team) {
        return !isAlly(position, team);
    }

    public GameStatus checkGeneralDied() {
        List<Piece> general = positionToPiece.keySet().stream()
                .filter(position -> positionToPiece.get(position).isSameType(PieceType.General))
                .map(positionToPiece::get)
                .toList();
        long red = findGeneral(general, Team.RED);
        long green = findGeneral(general, Team.GREEN);
        if (red == 1 && green == 0) {
            return GameStatus.RED_WIN;
        }
        if (red == 0 && green == 1) {
            return GameStatus.GREEN_WIN;
        }
        return GameStatus.CONTINUE;
    }

    public GameStatus checkRemainOnlyGeneral() {
        long countWithoutGeneral = positionToPiece.keySet().stream()
                .filter(position -> !positionToPiece.get(position).isSameType(PieceType.General))
                .count();
        if (countWithoutGeneral == 0) {
            return GameStatus.DRAW;
        }
        return GameStatus.CONTINUE;
    }

    private static long findGeneral(final List<Piece> general, final Team team) {
        return general.stream()
                .filter(piece -> piece.getTeam() == team)
                .count();
    }

    public Piece getPiece(final Position position) {
        if (isExists(position)) {
            return positionToPiece.get(position);
        }
        throw new IllegalArgumentException("장기말이 존재하지 않는 지점입니다.");
    }

    public Map<Position, Piece> getPositionToPiece() {
        return Collections.unmodifiableMap(positionToPiece);
    }
}
