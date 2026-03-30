package controller;

import controller.dto.CurrentBoardStatus;
import controller.dto.MovedPieceRequest;
import domain.GameManager;
import domain.Team;
import exception.GameExceptionHandler;
import exception.custom.GameException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        startJanggiGame(horseElephantFormations);
        playJanggiGame();
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, inputView.readHorseElephantFormation(Team.CHO.getKoreanName()));
        horseElephantInputs.put(Team.HAN, inputView.readHorseElephantFormation(Team.HAN.getKoreanName()));

        return horseElephantInputs;
    }

    private void startJanggiGame(Map<Team, String> horseElephantFormations) {
        this.gameManager = new GameManager(horseElephantFormations);
        printCurrentBoardStatus();
    }

    private void printCurrentBoardStatus() {
        List<CurrentBoardStatus> statuses = gameManager.getCurrentBoardStatus();
        outputView.printCurrentBoard(statuses);
    }

    private void playJanggiGame() {
        /**
         * TODO: 2차 사이클 - 게임 종료 조건 추가 예정
         */
        while (true) {
            playTurn(Team.CHO);
            playTurn(Team.HAN);
        }
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

    private void movePiece(Team team) {
        MovedPieceRequest movedPieceRequest = readMovedPiece();
        gameManager.movePiece(movedPieceRequest, team);
        printCurrentBoardStatus();
    }

    private MovedPieceRequest readMovedPiece(){
        String sourcePositionAndPieceType = readSourcePositionAndPieceType();
        String targetPosition = readTargetPosition();
        return MovedPieceRequest.of(sourcePositionAndPieceType, targetPosition);
    }

    private String readSourcePositionAndPieceType() {
        while(true){
            try {
                String input = inputView.readSourcePositionAndPieceType();
                return input;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }

    private String readTargetPosition() {
        while(true){
            try {
                String input = inputView.readTargetPosition();
                return input;
            } catch (GameException e) {
                gameExceptionHandler.handle(e);
            }
        }
    }
}
