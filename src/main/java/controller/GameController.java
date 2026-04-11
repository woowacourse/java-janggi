package controller;

import domain.Janggi;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.HorseElephantFormation;
import domain.place.piece.Side;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import parser.PlayerNameParser;
import parser.PositionParser;
import view.InputView;
import view.OutputView;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(Janggi janggi) {
        while (true) {
            Player player = janggi.getCurrentPlayer();
            outputView.printBoard(janggi.getBoardFormat());

            playTurn(player, janggi);
            outputView.printScore(janggi.getGameTotalScore());
            if (janggi.isGameOver()) {
                outputView.printWinner(janggi.getWinner());
                break;
            }
        }
    }

    public Players getPlayer() {
        while (true) {
            try {
                outputView.printInputPlayerNames();
                String input = inputView.readLine();
                List<String> names = PlayerNameParser.splitNames(input);
                return Players.from(names);
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

    public Board getBoard() {
        HorseElephantFormation cho = getHorseElephantFormation(Side.CHO);
        HorseElephantFormation han = getHorseElephantFormation(Side.HAN);

        return BoardFactory.create(cho, han);
    }

    private HorseElephantFormation getHorseElephantFormation(Side side) {
        while (true) {
            try {
                outputView.printHorseElephantFormation(side);
                String input = inputView.readLine();
                return HorseElephantFormation.from(input);
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

    private void playTurn(Player player, Janggi janggi) {
        while (true) {
            try {
                Position from = getFrom(player);
                Position to = getTo(player);
                janggi.playOneTurn(from, to);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

    private Position getFrom(Player player) {
        while (true) {
            try {
                outputView.printPieceMove(player.getName(), player.getSide());
                return PositionParser.parsePosition(inputView.readLine());
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }

    private Position getTo(Player player) {
        while (true) {
            try {
                outputView.printPositionMove(player.getName(), player.getSide());
                return PositionParser.parsePosition(inputView.readLine());
            } catch (IllegalArgumentException e) {
                outputView.printMessage(e.getMessage());
            }
        }
    }
}
