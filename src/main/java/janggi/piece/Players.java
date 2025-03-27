package janggi.piece;

import janggi.position.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// 팀마다의 기물들을 관리한다
public class Players {

    private static final int TOTAL_KING_COUNT = 2;

    private final Map<Team, Pieces> players;

    public Players(final Map<Team, Pieces> players) {
        this.players = new HashMap<>(players);
    }

    // 가운데 경로에는 기물이 없어야함
    // 도착 위치에는 다른 팀의 기물이어야함
    // 도착 위치에 자신의 팀 기물일 경우 움직일 수 없음
    public final void move(final Position currentPosition, final Position arrivalPosition,
                           final Team currentTeam) {
        validateSamePosition(currentPosition, arrivalPosition);

        final Pieces currrentTeamPieces = players.get(currentTeam);
        final Pieces opponentPieces = players.get(currentTeam.getOppositeTeam());

        currrentTeamPieces.validatePath(currentPosition, arrivalPosition, getTotalPieces());
        catchPiece(arrivalPosition, currrentTeamPieces, opponentPieces);
        currrentTeamPieces.updatePiece(currentPosition, arrivalPosition);
    }

    public boolean canContinue() {
        return calculateExistKing() == TOTAL_KING_COUNT;
    }

    public Team findWinningTeam() {
        // TODO : 점수 계산하는 로직으로 수정
        if (calculateExistKing() != 1) {
            throw new IllegalStateException("[ERROR] 왕이 하나가 아니라면 접근할 수 없습니다.");
        }

        return players.entrySet().stream()
                .filter(entry -> entry.getValue().hasKing())
                .map(Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 왕이 존재하지 않을 수 없습니다."));
    }

    public Set<Piece> getTotalPieces() {
        final Set<Piece> totalPieces = new HashSet<>();
        for (final Pieces pieces : players.values()) {
            totalPieces.addAll(pieces.getPieces());
        }
        return totalPieces;
    }

    private void catchPiece(final Position arrivalPosition,
                            final Pieces currentTeamPieces,
                            final Pieces oppositePieces) {
        // 도착 위치에 자신의 팀 기물일 경우 움직일 수 없음
        validateNotCatchingCurrentTeamPiece(currentTeamPieces, arrivalPosition);
        // 도착 위치에는 다른 팀의 기물이어야함
        if (oppositePieces.hasPiece(arrivalPosition)) {
            oppositePieces.removePiece(arrivalPosition);
        }
    }

    private void validateNotCatchingCurrentTeamPiece(final Pieces currentTeamPieces, final Position arrivalPosition) {
        if (hasSameTeamPiece(currentTeamPieces, arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀 기물을 잡을 수 없습니다.");
        }
    }

    private boolean hasSameTeamPiece(final Pieces currentTeamPieces, final Position arrivalPosition) {
        return currentTeamPieces.getPieces().stream()
                .anyMatch(piece -> piece.isSamePosition(arrivalPosition));
    }

    private void validateSamePosition(final Position currentPosition, final Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private int calculateExistKing() {
        return (int) players.values().stream()
                .filter(Pieces::hasKing)
                .count();
    }

    public Pieces getHanPieces() {
        return players.get(Team.HAN);
    }

    public Pieces getChoPieces() {
        return players.get(Team.CHO);
    }
}
