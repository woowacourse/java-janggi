package manager;

import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.Team;
import util.ErrorHandler;
import view.Command;
import view.InputView;
import view.MoveCommand;
import view.OutputView;
import view.SangMaOrderCommand;

public class GameManager {

    private static final Team START_TEAM = Team.CHO;

    public final String roomName;
    private final JanggiGame game;

    public GameManager(String roomName) {
        this.roomName = roomName;
        this.game = new JanggiGame(createBoard(new BoardGenerator()), START_TEAM);
    }

    private Board createBoard(BoardGenerator boardGenerator) {
        SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN);
        SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO);

        return boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    public void startGame() {
        OutputView.printStart();
        ErrorHandler.retryUntilSuccess(this::play);
        OutputView.printMatchResult(game.findWinTeam());
    }

    private void play() {
        while (game.isPlaying()) {
            Command command = InputView.inputCommand();

            if (command.isEnd()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
                OutputView.printMatchResult(game.findWinTeam());
                break;
            }

            if (command.isMove()) {
                OutputView.printBoard(game.board());
                MoveCommand moveCommand = InputView.inputMoveCommand(game.currentTurn());
                game.move(moveCommand.source(), moveCommand.destination());
                OutputView.printBoard(game.board());
                continue;
            }

            if (command.isStatus()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
            }
        }
    }
}
