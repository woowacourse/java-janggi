package manager;

import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.character.Team;
import util.ErrorHandler;
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

        return boardGenerator.generateInitialBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
    }

    public void startGame(String gameRoomName) {
        // TODO: if(DB에 없는 방 이름): 방 새로 생성, else: DB에 저장된 방 로드
        JanggiGame game = new JanggiGame(createBoard(new BoardGenerator()), START_TEAM);

        OutputView.printStart(gameRoomName);
        ErrorHandler.retryUntilSuccess(() -> play(game));
        OutputView.printMatchResult(game.findWinTeam());
    }

    private void play(JanggiGame game) {
        while (game.isPlaying()) {
            Command command = InputView.inputCommand();

            if (command.isEnd()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
                OutputView.printMatchResult(game.findWinTeam());
                break;
            }

            if (command.isMove()) {
                OutputView.printBoard(game.pieces());
                MoveCommand moveCommand = InputView.inputMoveCommand(game.currentTurn());
                game.move(moveCommand.source(), moveCommand.destination());
                OutputView.printBoard(game.pieces());
                continue;
            }

            if (command.isStatus()) {
                OutputView.printStatus(game.calculateScore(Team.HAN), game.calculateScore(Team.CHO));
            }
        }
    }
}
