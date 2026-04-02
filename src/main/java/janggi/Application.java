package janggi;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.util.SideDisplayNameMapper;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameManager gameManager = generateManeger(inputView, outputView);
        Runner runner = new Runner(inputView, outputView, gameManager);
        runner.run();
        inputView.close();
    }

    private static GameManager generateManeger(InputView inputView, OutputView outputView) {
        Board board = Board.initialize();
        Turn initiativeTurn = Turn.init();
        Players players = initialPlayers(inputView, outputView);
        return new GameManager(players, board, initiativeTurn);
    }

    private static Players initialPlayers(InputView inputView, OutputView outputView) {
        String choPlayerName = readPlayerName(inputView, outputView, Side.CHO);
        String hanPlayerName = readPlayerName(inputView, outputView, Side.HAN);
        return Players.from(choPlayerName, hanPlayerName);
    }

    private static String readPlayerName(InputView inputView, OutputView outputView, Side side) {
        String sideName = SideDisplayNameMapper.toDisplayName(side);
        outputView.printPlayerNameNotice(sideName);
        return inputView.readPlayerName();
    }
}
