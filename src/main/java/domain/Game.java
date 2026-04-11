package domain;

import controller.dto.CurrentBoardStatus;
import controller.dto.MoveStatus;
import controller.dto.MovedPieceRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {
    private final String name;
    private final Board board;
    private final Map<Team, HorseElephantFormation> initializeFormations;
    private Team currentTurn;
    private long moveSequence;

    /**
     * 어플리케이션 전략 선택시, 초기화용 생성자
     */
    public Game(String name, Map<Team, HorseElephantFormation> initializeFormations) {
        this.name = name;
        this.board = new Board(initializeFormations);
        this.initializeFormations = initializeFormations;
        this.currentTurn = Team.CHO;
        this.moveSequence = 0;
    }

    /**
     * DB에 저장된 초기 배치 전략 선택시, 초기화용 생성자
     */
    public Game(String name, List<CurrentBoardStatus> initialStatuses) {
        this.name = name;
        this.board = new Board(initialStatuses);
        this.initializeFormations = Collections.emptyMap();
        this.currentTurn = Team.CHO;
        this.moveSequence = 0;
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
        this.moveSequence++;
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

    public String getHorseElephantFormationName(Team team) {
        return this.initializeFormations.get(team).name();
    }

    public String getName() {
        return this.name;
    }

    public String getCurrentTeamName() {
        return this.currentTurn.getKoreanName();
    }

    public long getMoveSequence() {
        return this.moveSequence;
    }

    /**
     * 헬퍼 메서드
     */
    private int calculateCurrentScoreOf(Team team) {
        return board.getCurrentScoreOfTeam(team);
    }
}
