package controller;

import controller.dto.CurrentBoardStatus;
import controller.dto.MovedPieceRequest;
import domain.GameManager;
import domain.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private GameManager gameManager;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        this.gameManager = new GameManager(readHorseElephantFormation());
        printCurrentBoardStatus();
        movePiece();
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, inputView.readChoHorseElephantFormation());
        horseElephantInputs.put(Team.HAN, inputView.readHanHorseElephantFormation());

        return horseElephantInputs;
    }

    private void printCurrentBoardStatus() {
        List<CurrentBoardStatus> statuses = gameManager.getCurrentBoardStatus();
        outputView.printCurrentBoard(statuses);
    }

    public void movePiece() {
        MovedPieceRequest movedPieceRequest = readMovedPiece();
        gameManager.movePiece(movedPieceRequest);
        printCurrentBoardStatus();
    }

    private MovedPieceRequest readMovedPiece(){
        return inputView.readMovedPieceInput();
    }
}
