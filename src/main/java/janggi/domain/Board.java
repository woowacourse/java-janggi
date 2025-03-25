package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> positionToPiece;

    public Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = new HashMap<>(positionToPiece);
    }

    public boolean exists(final Position position) {
        return positionToPiece.containsKey(position);
    }

    public void movePiece(final Player player, final Position departure, final Position destination) {
        validateSamePosition(departure, destination);
        Piece targetPiece = getPiece(departure);
        validateDepartureIsAlly(player, targetPiece);
        validateDestinationIsEnemy(destination, targetPiece.getTeam());
        Placement placement = new Placement(this, departure, destination);
        targetPiece.checkCanMove(placement, departure, destination);
        positionToPiece.remove(departure);
        updateBoard(destination, targetPiece);
    }

    private void validateSamePosition(final Position departure, final Position destination) {
        if (departure.equals(destination)) {
            throw new IllegalArgumentException("현재 위치와 이동할 위치와 같은 위치입니다");
        }
    }

    private void validateDepartureIsAlly(final Player player, final Piece allyPiece) {
        if (allyPiece.isEnemy(player.getTeam())) {
            throw new IllegalArgumentException("적의 기물을 선택할 수 없습니다.");
        }
    }

    private void validateDestinationIsEnemy(final Position destination, final Team team) {
        if ((positionToPiece.containsKey(destination) && getPiece(destination).isAlly(team))) {
            throw new IllegalArgumentException("목적지에 아군이 존재합니다.");
        }
    }

    private void updateBoard(final Position destination, final Piece movedPiece) {
        positionToPiece.put(destination, movedPiece);
    }

    public GameStatus checkGeneralDied() {
        List<Piece> general = positionToPiece.keySet().stream()
                .filter(position -> positionToPiece.get(position).isSameType(PieceType.GENERAL))
                .map(positionToPiece::get)
                .toList();
        boolean redGeneralAlive = isAliveGeneral(general, Team.RED);
        boolean greenGeneralAlive = isAliveGeneral(general, Team.GREEN);
        if (redGeneralAlive && !greenGeneralAlive) {
            return GameStatus.RED_WIN;
        }
        if (!redGeneralAlive && greenGeneralAlive) {
            return GameStatus.GREEN_WIN;
        }
        return GameStatus.CONTINUE;
    }

    public GameStatus checkRemainOnlyGeneral() {
        long countWithoutGeneral = positionToPiece.keySet().stream()
                .filter(position -> !positionToPiece.get(position).isSameType(PieceType.GENERAL))
                .count();
        if (countWithoutGeneral == 0) {
            return GameStatus.DRAW;
        }
        return GameStatus.CONTINUE;
    }

    private static boolean isAliveGeneral(final List<Piece> general, final Team team) {
        return general.stream()
                .anyMatch(piece -> piece.isSameType(PieceType.GENERAL) && piece.getTeam() == team);
    }

    public Piece getPiece(final Position position) {
        if (exists(position)) {
            return positionToPiece.get(position);
        }
        throw new IllegalArgumentException("장기말이 존재하지 않는 지점입니다.");
    }

    public Map<Position, Piece> getPositionToPiece() {
        return Collections.unmodifiableMap(positionToPiece);
    }

    public boolean isSameType(final Position position, final PieceType pieceType) {
        return getPiece(position).isSameType(pieceType);
    }
}
