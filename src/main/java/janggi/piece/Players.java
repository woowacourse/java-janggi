package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

// 팀마다의 기물들을 관리한다
public class Players {

    private static final int TOTAL_KING_COUNT = 2;

    private final Map<Team, Board> players;

    public Players(final Map<Team, Board> players) {
        this.players = new HashMap<>(players);
    }

    // 가운데 경로에는 기물이 없어야함
    // 도착 위치에는 다른 팀의 기물이어야함
    // 도착 위치에 자신의 팀 기물일 경우 움직일 수 없음
    public final void move(final Position currentPosition, final Position arrivalPosition,
                           final Team currentTeam) {
        validateSamePosition(currentPosition, arrivalPosition);

        final Board currrentTeamBoard = players.get(currentTeam);
        final Board opponentBoard = players.get(currentTeam.getOppositeTeam());

        currrentTeamBoard.validatePath(currentPosition, arrivalPosition, getTotalPieces());
        catchPiece(arrivalPosition, currrentTeamBoard, opponentBoard);
        currrentTeamBoard.updatePiece(currentPosition, arrivalPosition);
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

    public Board getTotalPieces() {
        final Set<Piece> totalPieces = new HashSet<>();
        for (final Board board : players.values()) {
            totalPieces.addAll(board.getPieces());
        }
        return Board.from(totalPieces);
    }

    public Map<Team, Double> calculateScore() {
        return players.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> calculateScore(entry.getKey(), entry.getValue())));
    }

    private Double calculateScore(final Team team, final Board board) {
        double score = 0;
        if (team == Team.HAN) {
            score += 1.5;
        }
        return score + board.calculateScore();
    }

    private void catchPiece(final Position arrivalPosition,
                            final Board currentTeamBoard,
                            final Board oppositeBoard) {
        // 도착 위치에 자신의 팀 기물일 경우 움직일 수 없음
        validateNotCatchingCurrentTeamPiece(currentTeamBoard, arrivalPosition);
        // 도착 위치에는 다른 팀의 기물이어야함
        if (oppositeBoard.hasPiece(arrivalPosition)) {
            oppositeBoard.removePiece(arrivalPosition);
        }
    }

    private void validateNotCatchingCurrentTeamPiece(final Board currentTeamBoard, final Position arrivalPosition) {
        if (hasSameTeamPiece(currentTeamBoard, arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀 기물을 잡을 수 없습니다.");
        }
    }

    private boolean hasSameTeamPiece(final Board currentTeamBoard, final Position arrivalPosition) {
        return currentTeamBoard.getPieces().stream()
                .anyMatch(piece -> piece.isSamePosition(arrivalPosition));
    }

    private void validateSamePosition(final Position currentPosition, final Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    private int calculateExistKing() {
        return (int) players.values().stream()
                .filter(Board::hasKing)
                .count();
    }

    public Board getHanPieces() {
        return players.get(Team.HAN);
    }

    public Board getChoPieces() {
        return players.get(Team.CHO);
    }
}
