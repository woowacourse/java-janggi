package janggi.domain.board;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board = new HashMap<>();

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public void movePiece(Position movePiecePosition, Position destination) {
        Piece piece = board.get(movePiecePosition);
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 빈 칸은 이동시킬 수 없습니다.");
        }
        board.remove(movePiecePosition);
        board.put(destination, piece);
    }

    public Piece pieceAt(Position position) {
        return board.get(position);
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public List<Position> findAvailablePositions(Position position) {
        Piece piece = pieceAt(position);

        return piece.findMovablePositions(this, position);
    }

    public void validateMovePiecePosition(Position movePiecePosition) {
        if (!hasPiece(movePiecePosition)) {
            throw new IllegalArgumentException("[ERROR] 빈 칸을 선택하셨습니다.");
        }
    }

    public void validateDestination(Position movePiecePosition, Position destination) {
        List<Position> availablePositions = findAvailablePositions(movePiecePosition);
        if (!availablePositions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 이동 가능한 좌표 중에서 선택하세요.");
        }
    }

    public void validateAvailablePositions(List<Position> positions) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 있는 좌표가 없습니다.");
        }
    }

    public Map<Team, Double> calculateScore() {
        Map<Team, Double> teamScores = new HashMap<>();
        for (Team team : Team.values()) {
            double totalScore = calculateScoreByTeam(team);
            teamScores.put(team, totalScore);
        }
        return teamScores;
    }

    private double calculateScoreByTeam(Team team) {
        double totalScore = team.selectStartScoreByTeam();
        for (Piece piece : board.values()) {
            totalScore = piece.addScore(team, totalScore);
        }
        return totalScore;
    }
}
