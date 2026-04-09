package domain;

import controller.dto.CurrentBoardStatus;
import controller.dto.MoveStatus;
import controller.dto.MovedPieceRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import strategy.InitializeStrategy;

public class GameManager {
    private final Board board;

    public GameManager(Map<Team, InitializeStrategy> initializeStrategies) {
        this.board = new Board(initializeStrategies);
    }

    public GameManager(List<CurrentBoardStatus> statuses) {
        this.board = new Board(statuses);
    }

    public List<CurrentBoardStatus> getCurrentBoardStatus() {
        return board.getCurrentStatus();
    }

    /**
     * command
     */
    public void movePiece(MovedPieceRequest request, Team team) {
        board.move(Position.from(request.currentRow(), request.currentColumn()),
                Position.from(request.nextRow(), request.nextColumn()), team);
    }

    /**
     * query
     */
    public MoveStatus getMoveStatus(MovedPieceRequest request) {
        Position destinationPosition = Position.from(request.nextRow(), request.nextColumn());

        String teamName = board.getPieceTeamOnPosition(destinationPosition);
        String pieceName = board.getPieceTypeOnPosition(destinationPosition);

        return MoveStatus.of(teamName, pieceName, request.nextRow(), request.nextColumn());
    }

    public Map<Team, Integer> calculateCurrentScore(List<Team> teams) {
        return teams.stream()
                .collect(Collectors.toMap(
                        team -> team,
                        this::calculateCurrentScoreOf
                ));
    }

    public boolean isGameFinished(Team currentTeam) {
        return !board.isExistPiece(PieceType.KING, currentTeam);
    }

    /**
     * 헬퍼 메서드
     */
    private int calculateCurrentScoreOf(Team team) {
        return board.getCurrentScoreOfTeam(team);
    }
}
