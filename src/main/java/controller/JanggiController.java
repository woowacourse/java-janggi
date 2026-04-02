package controller;

import controller.dto.CurrentBoardStatus;
import controller.dto.MovedPieceRequest;
import domain.GameManager;
import domain.HorseElephantFormation;
import domain.Team;
import exception.GameExceptionHandler;
import exception.custom.GameException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.InitializeStrategy;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final GameExceptionHandler gameExceptionHandler;
    private final InputView inputView;
    private final OutputView outputView;
    private GameManager gameManager;

    public JanggiController(GameExceptionHandler gameExceptionHandler, InputView inputView, OutputView outputView) {
        this.gameExceptionHandler = gameExceptionHandler;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        Map<Team, String> horseElephantFormations = readHorseElephantFormation();
        initializeJanggiGame(horseElephantFormations);
        Team winnerTeam = playJanggiGame();
        printGameWinner(winnerTeam);
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, readEachHorseElephantFormation(Team.CHO));
        horseElephantInputs.put(Team.HAN, readEachHorseElephantFormation(Team.HAN));

        return horseElephantInputs;
    }

    /**
     * 1. 장기 게임 초기화
     */
    private void initializeJanggiGame(Map<Team, String> horseElephantFormations) {
        this.gameManager = new GameManager(toInitializeStrategies(horseElephantFormations));
        printCurrentBoardStatus();
    }

    private Map<Team, InitializeStrategy> toInitializeStrategies(Map<Team, String> horseElephantFormations) {
        Map<Team, InitializeStrategy> initializeStrategies = new HashMap<>();
        horseElephantFormations.forEach(
                (team, formation) -> initializeStrategies.put(team, getBoardInitializeStrategy(formation))
        );
        return initializeStrategies;
    }

    private InitializeStrategy getBoardInitializeStrategy(String formationInput) {
        return HorseElephantFormation.getStrategy(formationInput);
    }

    private void printCurrentBoardStatus() {
        List<CurrentBoardStatus> statuses = gameManager.getCurrentBoardStatus();
        outputView.printCurrentBoard(statuses);
    }

    private Team playJanggiGame() {
        Team currentTeam = Team.CHO;

        while (!gameManager.isGameFinished(currentTeam)) {
            playTurn(currentTeam);
            currentTeam = switchTeam(currentTeam);
        }

        return switchTeam(currentTeam);
    }

    private void playTurn(Team team) {
        while (true) {
            try {
                movePiece(team);
                return;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private Team switchTeam(Team team) {
        if (team == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    private void printGameWinner(Team team) {
        outputView.printGameWinner(team.getKoreanName());
    }

    private void movePiece(Team team) {
        MovedPieceRequest movedPieceRequest = readMovedPiece();
        gameManager.movePiece(movedPieceRequest, team);
        printCurrentBoardStatus();
    }

    private MovedPieceRequest readMovedPiece() {
        String sourcePositionAndPieceType = readSourcePositionAndPieceType();
        String targetPosition = readTargetPosition();
        return MovedPieceRequest.of(sourcePositionAndPieceType, targetPosition);
    }

    private String readEachHorseElephantFormation(Team team) {
        while (true) {
            try {
                return inputView.readHorseElephantFormation(team.getKoreanName());
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private String readSourcePositionAndPieceType() {
        while (true) {
            try {
                String input = inputView.readSourcePositionAndPieceType();
                return input;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private String readTargetPosition() {
        while (true) {
            try {
                String input = inputView.readTargetPosition();
                return input;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }
}
