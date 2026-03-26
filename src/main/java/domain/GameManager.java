package domain;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import view.InputView;
import view.OutputView;

public class GameManager {
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    public void run() {
        Board board = initialize();
        outputView.printBoard(board.createDTO().board());
    }

    private Board initialize() {
        String choName = inputView.askChoPlayerName();
        Player choPlayer = createPlayer(choName, CHO);

        String hanName = inputView.askHanPlayerName();
        Player hanPlayer = createPlayer(hanName, HAN);

        int choPositionInput = inputView.askChoPositionInput();
        Formation choFormation = createFormation(choPositionInput);

        int hanPositionInput = inputView.askHanPositionInput();
        Formation hanFormation = createFormation(hanPositionInput);

        return BoardFactory.createWithFormation(choFormation, hanFormation);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
