package controller;

import domain.Game;
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
import service.GameService;
import view.InputView;
import view.OutputView;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService gameService;

    public GameController(InputView inputView, OutputView outputView, GameService gameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameService = gameService;
    }

    public void run(Long gameId) {
        while (true) {
            Game game = gameService.loadGame(gameId);
            Player player = game.getCurrentPlayer();
            outputView.printBoard(game.getBoardFormat());

            Game playedGame = playTurn(player, gameId);

            outputView.printScore(playedGame.getGameTotalScore());
            if (playedGame.isGameOver()) {
                outputView.printWinner(playedGame.getWinner());
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

    private Game playTurn(Player player, Long gameId) {
        while (true) {
            try {
                Position from = getFrom(player);
                Position to = getTo(player);
                return gameService.playTurn(gameId, from, to);
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
