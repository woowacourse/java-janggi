package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.position.Position;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final int GAME_RUNNING_KING_COUNT = 2;
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void movePiece(Position from, Position to, TeamType team) {
        Piece foundPiece = findPieceByPosition(from);

        validateOwnPiece(team, foundPiece);
        foundPiece.validateMove(from, to, this);

        changePosition(from, to, foundPiece);
    }

    public TeamType findWinTeam() {
        if (isInProgress()) {
            throw new IllegalArgumentException("게임이 종료되지 않아 우승을 판별할 수 없습니다.");
        }
        Piece king = pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임에 왕이 존재하지 않아 우승을 판별할 수 없습니다."));

        return findWinTeamByAliveKing(king);
    }

    public boolean isInProgress() {
        int kingCount = (int) pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .count();

        return kingCount == GAME_RUNNING_KING_COUNT;
    }

    public Piece findPieceByPosition(Position from) {
        if (isEmptyPosition(from)) {
            throw new IllegalArgumentException("해당 좌표에는 말이 존재하지 않습니다.");
        }
        return pieces.get(from);
    }

    public boolean isEmptyPosition(Position position) {
        return !pieces.containsKey(position);
    }

    private void changePosition(Position from, Position to, Piece foundPiece) {
        pieces.remove(from);
        pieces.put(to, foundPiece);
    }

    private TeamType findWinTeamByAliveKing(Piece king) {
        return Arrays.stream(TeamType.values())
                .filter(king::isSameTeam)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("왕의 팀이 존재하지 않습니다."));
    }

    private void validateOwnPiece(TeamType team, Piece piece) {
        if (isNotSameTeam(team, piece)) {
            throw new IllegalArgumentException("본인 말만 움직일 수 있습니다.");
        }
    }

    private boolean isNotSameTeam(TeamType team, Piece piece) {
        return !piece.isSameTeam(team);
    }

    public Map<Position, Piece> getAlivePieces() {
        return new HashMap<>(pieces);
    }
}
