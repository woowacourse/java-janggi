package controller;

import controller.dto.CurrentBoardStatus;
import controller.dto.CurrentScore;
import controller.dto.MoveStatus;
import controller.dto.MovedPieceRequest;
import domain.Game;
import domain.HorseElephantFormation;
import domain.Team;
import exception.GameExceptionHandler;
import exception.custom.GameException;

import infra.repository.GameRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.InputView;
import view.OutputView;

public class JanggiController {
    private final GameExceptionHandler gameExceptionHandler;
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;
    private Game game;

    public JanggiController(GameExceptionHandler gameExceptionHandler, InputView inputView, OutputView outputView,
                            GameRepository gameRepository) {
        this.gameExceptionHandler = gameExceptionHandler;
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void start() {
        if (shouldContinueGame()) {
            loadJanggiGame();
        } else {
            initializeJanggiGame();
        }

        playJanggiGame();
        printGameWinner(game.getWinner());
        boardRepository.deleteAll();
    }

    private void initializeJanggiGame() {
        Map<Team, String> horseElephantFormations = readHorseElephantFormation();
        String gameName = readGameName();
        this.game = new Game(gameName, parseToFormations(horseElephantFormations));
        gameRepository.save(this.game);
        printCurrentBoardStatus();
    }

    private void playJanggiGame() {
        while (!game.isGameFinished()) {
            playTurn();
        }
    }

    private void playTurn() {
        while (true) {
            try {
                movePiece();
                /*boardRepository.save(game.getCurrentBoardStatus());*/
                printCurrentScore();
                printCurrentBoardStatus();
                return;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private void movePiece() {
        MovedPieceRequest movedPieceRequest = readMovedPiece();
        game.movePiece(movedPieceRequest);
        printMoveStatus(movedPieceRequest);
    }

    /**
     * 헬퍼 메서드
     */
    private boolean shouldContinueGame() {
//        return !boardRepository.readBoard().isEmpty();
        return false;
    }

    private Map<Team, HorseElephantFormation> parseToFormations(Map<Team, String> horseElephantFormations) {
        Map<Team, HorseElephantFormation> initializeStrategies = new HashMap<>();
        horseElephantFormations.forEach(
                (team, formation) -> initializeStrategies.put(team, getBoardInitializeStrategy(formation))
        );
        return initializeStrategies;
    }

    private HorseElephantFormation getBoardInitializeStrategy(String formationInput) {
        return HorseElephantFormation.getFormationFrom(formationInput);
    }

    /**
     * 출력 단계 조율 메서드
     */
    private void printCurrentBoardStatus() {
        List<CurrentBoardStatus> statuses = game.getCurrentBoardStatus();
        outputView.printCurrentBoard(statuses);
    }

    private void printGameWinner(Team team) {
        outputView.printGameWinner(team.getKoreanName());
    }

    private void printCurrentScore() {
        List<CurrentScore> results = new ArrayList<>();

        Map<Team, Integer> currentScore = game.calculateCurrentScore(List.of(Team.CHO, Team.HAN));
        currentScore.forEach(((team, score) ->
                results.add(CurrentScore.of(team, score))));

        outputView.printCurrentScore(results);
    }

    private void printMoveStatus(MovedPieceRequest movedPieceRequest) {
        MoveStatus moveStatus = game.getMoveStatus(movedPieceRequest);
        outputView.printMoveStatus(moveStatus);
    }

    private void printCurrentTurnTeam(Team team) {
        outputView.printCurrentTurnTeam(team.getKoreanName());
    }

    /**
     * 입력 단계 조율 메서드
     */
    private void loadJanggiGame() {
        this.game = new Game(boardRepository.readBoard(), boardRepository.readTurn());
        printCurrentBoardStatus();
    }

    private String readGameName() {
        while (true) {
            try {
                return inputView.readSourcePosition();
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, readEachHorseElephantFormation(Team.CHO));
        horseElephantInputs.put(Team.HAN, readEachHorseElephantFormation(Team.HAN));

        return horseElephantInputs;
    }

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
