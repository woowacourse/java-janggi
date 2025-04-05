package janggi.domain.players;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.position.Position;
import janggi.dto.PieceMove;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Players {

    private static final int TOTAL_KING_COUNT = 2;

    private final Map<Team, Board> players;

    public Players(final Map<Team, Board> players) {
        this.players = new HashMap<>(players);
    }

    public final PieceMove move(final Position from, final Position to, final Team currentTeam) {
        validateSamePosition(from, to);

        final Board currrentTeamBoard = players.get(currentTeam);
        final Board opponentBoard = players.get(currentTeam.getOppositeTeam());

        final Board totalPieces = getTotalBoard();
        currrentTeamBoard.move(from, to, totalPieces);
        final Optional<Piece> caughtPieceOptional = catchPiece(to, currrentTeamBoard, opponentBoard);
        currrentTeamBoard.updatePiece(from, to);
        final Piece currentPiece = currrentTeamBoard.findPieceByPosition(to);

        if (caughtPieceOptional.isPresent()) {
            final Piece caughtPiece = caughtPieceOptional.get();
            return PieceMove.capture(currentTeam, currentPiece, from, to, caughtPiece);
        }
        return new PieceMove(currentTeam, currentPiece, from, to);
    }

    public boolean canContinue() {
        return calculateExistKing() == TOTAL_KING_COUNT;
    }

    public Team findWinningTeam() {
        if (calculateExistKing() == TOTAL_KING_COUNT) {
            return compareByScore();
        }
        return players.entrySet().stream()
                .filter(entry -> entry.getValue().hasKing())
                .map(Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 왕이 존재하지 않을 수 없습니다."));
    }

    public Board getTotalBoard() {
        final Board choBoard = players.get(Team.CHO);
        final Board hanBoard = players.get(Team.HAN);
        return choBoard.addAll(hanBoard);
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

    private Team compareByScore() {
        final Map<Team, Double> teamScore = calculateScore();
        final double hanScore = teamScore.get(Team.HAN);
        final double choScore = teamScore.get(Team.CHO);
        if (hanScore < choScore) {
            return Team.CHO;
        }
        if (hanScore == choScore) {
            return Team.NONE;
        }
        return Team.HAN;
    }

    private Optional<Piece> catchPiece(final Position arrivalPosition,
                                       final Board currentTeamBoard,
                                       final Board oppositeBoard) {
        validateNotCatchingCurrentTeamPiece(currentTeamBoard, arrivalPosition);
        if (oppositeBoard.hasPiece(arrivalPosition)) {
            final Piece targetPiece = oppositeBoard.findPieceByPosition(arrivalPosition);
            oppositeBoard.removePiece(arrivalPosition);
            return Optional.of(targetPiece);
        }
        return Optional.empty();
    }

    private void validateNotCatchingCurrentTeamPiece(final Board currentTeamBoard, final Position arrivalPosition) {
        if (hasSameTeamPiece(currentTeamBoard, arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀 기물을 잡을 수 없습니다.");
        }
    }

    private boolean hasSameTeamPiece(final Board currentTeamBoard, final Position arrivalPosition) {
        return currentTeamBoard.getPositions().stream()
                .anyMatch(position -> position.equals(arrivalPosition));
    }

    private void validateSamePosition(final Position from, final Position to) {
        if (from.equals(to)) {
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
