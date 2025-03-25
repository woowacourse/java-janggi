package manager;

import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.Team;
import view.Command;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class GameManager {

    private static final Team START_TEAM = Team.CHO;

    private Board createBoard(BoardGenerator boardGenerator) {
        SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);

        return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    public void startGame() {
        OutputView.printStart();
        JanggiGame game = new JanggiGame(createBoard(new BoardGenerator()), START_TEAM);

        while (game.isPlaying()) {
            Command command = InputView.inputCommand();

            if (command.isEnd()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
                OutputView.printMatchResult(game.findWinTeam());
                break;
            }

            if (command.isMove()) {
                MoveCommand moveCommand = InputView.inputMoveCommand(game.currentTurn());
                game.move(moveCommand.source(), moveCommand.destination());
                continue;
            }

            if (command.isStatus()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
            }
        }

        OutputView.printMatchResult(game.findWinTeam());
    }
}
