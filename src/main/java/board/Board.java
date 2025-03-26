package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Turn;
import piece.Cannon;
import piece.Piece;
import piece.Team;

public class Board {

    private final List<Piece> pieces;

    public Board() {
        this.pieces = new ArrayList<>();
    }

    public Board(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void putPieces(final List<Piece> pieces) {
        this.pieces.addAll(pieces);
    }

    public void isValidTurn(final Position startPosition, final Turn turn) {
        Piece pieceByPosition = findPieceByPosition(startPosition);
        Team currentTurnTeam = turn.getCurrnetTeam();
        if (!pieceByPosition.isSameTeam(currentTurnTeam)) {
            throw new IllegalArgumentException(
                    String.format("올바른 기물의 위치를 입력해주세요(현재 턴: %s).", currentTurnTeam.name())
            );
        }
    }

    public void move(final Position start, final Position destination) {
        Piece movingPiece = findPieceByPosition(start);
        movingPiece.move(destination, this);
    }

    public boolean isExists(final Position position) {
        return pieces.stream().anyMatch(piece -> piece.isSamePosition(position));
    }

    public boolean isSameTeamPosition(final Team team, final Position position) {
        return pieces.stream()
                .anyMatch(p -> p.isSamePosition(position) && p.isSameTeam(team));
    }

    public void remove(final Position position) {
        pieces.removeIf(piece -> piece.isSamePosition(position));
    }

    public boolean isCannonPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .anyMatch(Cannon.class::isInstance);
    }

    public Piece findPieceByPosition(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 기물의 위치를 입력해주세요."));
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

}
