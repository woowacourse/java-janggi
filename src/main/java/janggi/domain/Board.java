package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> positionToPiece;

    private Board(final Map<Position, Piece> positionToPiece) {
        this.positionToPiece = positionToPiece;
    }

    public static Board initialize(final List<Piece> pieces) {
        Map<Position, Piece> positionToPiece = pieces.stream()
                .collect(Collectors.toMap(Piece::getPosition, piece -> piece));
        return new Board(positionToPiece);
    }

    public boolean exists(final Position position) {
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
}
