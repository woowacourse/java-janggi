package controller;

import domain.GameManager;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import view.InputView;

public class JanggiController {
    private final InputView inputView;

    public JanggiController(InputView inputView) {
        this.inputView = inputView;
    }

    public void start(){
        GameManager gameManager = new GameManager(readHorseElephantFormation());
    }

    private Map<Team, String> readHorseElephantFormation() {
        Map<Team, String> horseElephantInputs = new HashMap<>();
        horseElephantInputs.put(Team.CHO, inputView.readChoHorseElephantFormation());
        horseElephantInputs.put(Team.HAN, inputView.readHanHorseElephantFormation());

        return horseElephantInputs;
    }
}
