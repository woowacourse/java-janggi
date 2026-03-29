package controller;

import controller.dto.CurrentBoardStatus;
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

    public void start(){
        this.gameManager = new GameManager(readHorseElephantFormation());
        List<CurrentBoardStatus> currentBoardStatus = gameManager.getCurrentBoardStatus();
        outputView.printCurrentBoard(currentBoardStatus);
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, inputView.readChoHorseElephantFormation());
        horseElephantInputs.put(Team.HAN, inputView.readHanHorseElephantFormation());

        return horseElephantInputs;
    }

    private List<CurrentBoardStatus> getCurrentBoardStatus(){
        return gameManager.getCurrentBoardStatus();
    }
}
