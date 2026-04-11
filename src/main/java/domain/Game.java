package domain;

import controller.dto.CurrentBoardStatus;
import controller.dto.MoveStatus;
import controller.dto.MovedPieceRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import strategy.InitializeStrategy;

public class Game {
    private final String name;
    private final Board board;
    private final Map<Team, HorseElephantFormation> initializeFormations;
    private Team currentTurn;

    /**
     * 초기화용 생성자
     */
    public Game(String name, Map<Team, HorseElephantFormation> initializeFormations) {
        this.name = name;
        this.board = new Board(initializeFormations);
        this.initializeFormations = initializeFormations;
        this.currentTurn = Team.CHO;
    }

    /**
     * 재구성용 생성자
     */
    public Game(String name,
                List<CurrentBoardStatus> statuses,
                Map<Team, HorseElephantFormation> initializeFormations,
                Team currentTurn) {
        this.name = name;
        this.board = new Board(statuses);
        this.initializeFormations = initializeFormations;
        this.currentTurn = currentTurn;
    }

    public List<CurrentBoardStatus> getCurrentBoardStatus() {
        return board.getCurrentStatus();
    }

    /**
     * command
     */
    public void movePiece(MovedPieceRequest request) {
        board.move(Position.from(request.currentRow(), request.currentColumn()),
                Position.from(request.nextRow(), request.nextColumn()), this.currentTurn);

        this.currentTurn = currentTurn.changeTurn();
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

    public boolean isGameFinished() {
        return !board.isExistPiece(PieceType.KING, this.currentTurn);
    }

    public String getHorseElephantFormation(Team team){
        return this.initializeFormations.get(team).getPattern();
    }

    public String getName() {
        return this.name;
    }

    public String getCurrentTeamName(){
        return this.currentTurn.getKoreanName();
    }

    /**
     * 헬퍼 메서드
     */
    private int calculateCurrentScoreOf(Team team) {
        return board.getCurrentScoreOfTeam(team);
    }
}
