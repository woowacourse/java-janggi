package controller;

import controller.dto.CurrentBoardStatus;
import controller.dto.CurrentScore;
import controller.dto.MoveStatus;
import controller.dto.MovedPieceRequest;
import domain.GameManager;
import domain.HorseElephantFormation;
import domain.Team;
import exception.GameExceptionHandler;
import exception.custom.GameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.InitializeStrategy;
import infra.repository.BoardRepository;

import view.InputView;
import view.OutputView;

public class JanggiController {
    private final GameExceptionHandler gameExceptionHandler;
    private final InputView inputView;
    private final OutputView outputView;
    private final BoardRepository boardRepository;
    private GameManager gameManager;

    public JanggiController(GameExceptionHandler gameExceptionHandler, InputView inputView, OutputView outputView,
                            BoardRepository boardRepository) {
        this.gameExceptionHandler = gameExceptionHandler;
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
    }

    public void start() {
        Team currentTeam = Team.CHO;
        if (shouldContinueGame()) {
            currentTeam = loadJanggiGame();
        } else {
            Map<Team, String> horseElephantFormations = readHorseElephantFormation();
            initializeJanggiGame(horseElephantFormations);
        }
        Team winnerTeam = playJanggiGame(currentTeam);
        printGameWinner(winnerTeam);
        boardRepository.deleteAll();
    }

    private boolean shouldContinueGame() {
        return !boardRepository.readBoard().isEmpty();
    }

    private Team loadJanggiGame() {
        this.gameManager = new GameManager(boardRepository.readBoard());
        printCurrentBoardStatus();
        return boardRepository.readTurn();
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

    private Team playJanggiGame(Team startTeam) {
        Team currentTeam = startTeam;

        while (!gameManager.isGameFinished(currentTeam)) {
            playTurn(currentTeam);
            currentTeam = switchTeam(currentTeam);
        }

        return switchTeam(currentTeam);
    }

    private void playTurn(Team team) {
        printCurrentTurnTeam(team);
        while (true) {
            try {
                movePiece(team);
                boardRepository.save(gameManager.getCurrentBoardStatus(), switchTeam(team));
                printCurrentScore();
                printCurrentBoardStatus();
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
        printMoveStatus(movedPieceRequest);
    }

    private void printCurrentScore() {
        List<CurrentScore> results = new ArrayList<>();

        Map<Team, Integer> currentScore = gameManager.calculateCurrentScore(List.of(Team.CHO, Team.HAN));
        currentScore.forEach(((team, score) ->
                results.add(CurrentScore.of(team, score))));

        outputView.printCurrentScore(results);
    }

    private void printMoveStatus(MovedPieceRequest movedPieceRequest) {
        MoveStatus moveStatus = gameManager.getMoveStatus(movedPieceRequest);
        outputView.printMoveStatus(moveStatus);
    }

    private void printCurrentTurnTeam(Team team) {
        outputView.printCurrentTurnTeam(team.getKoreanName());
    }

    /**
     * 입력 단계 조율 메서드
     */
    private MovedPieceRequest readMovedPiece() {
        String sourcePosition = readSourcePosition();
        String targetPosition = readTargetPosition();
        return MovedPieceRequest.of(sourcePosition, targetPosition);
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

    private String readSourcePosition() {
        while (true) {
            try {
                return inputView.readSourcePosition();
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private String readTargetPosition() {
        while (true) {
            try {
                return inputView.readTargetPosition();
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }
}
