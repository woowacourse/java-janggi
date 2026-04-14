package controller;

import controller.response.BoardViewResponse;
import controller.response.TurnResponse;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Team;
import service.JanggiGameService;
import view.InputView;
import view.OutputView;

import java.util.List;

import static controller.GameStartOption.NEW_GAME;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameService janggiGameService;

    public JanggiController(final InputView inputView, final OutputView outputView, final JanggiGameService janggiGameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGameService = janggiGameService;
    }

    public void run() {
        final JanggiGame game = initializeGame();

        while (game.isPlaying()) {
            playTurn(game);
        }

        printGameResult(game);
    }

    private JanggiGame initializeGame() {
        // TODO: 1. 새 게임   2. 불러오기
        final GameStartOption gameStartOption = selectGameStartOption();

        if (gameStartOption == NEW_GAME) {
            return startNewGame();
        }

        return janggiGameService.loadLatestGame();
    }

    private GameStartOption selectGameStartOption() {
        while (true) {
            try {
                outputView.printGameStartMenu();
                final int selectedNumber = inputView.readNumber(GameStartOption.values().length);
                return GameStartOption.of(selectedNumber);
            } catch (final IllegalArgumentException exception) {
                outputView.printErrorMessage(exception.getMessage());
            }
        }
    }

    private JanggiGame startNewGame() {
        final Player choPlayer = generatePlayer(Team.CHO);
        final Player hanPlayer = generatePlayer(Team.HAN);
        final Board board = initializeBoard();

        return janggiGameService.startNewGame(choPlayer, hanPlayer, board);
    }


    private Player generatePlayer(final Team team) {
        while (true) {
            try {
                outputView.printEnterPlayerNamePrompt(team);
                final String playerName = inputView.readPlayerName();
                return Player.newPlayer(playerName, team);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }


    private Board initializeBoard() {
        final ElephantSetup choElephantSetup = generateElephantSetup(Team.CHO);
        final ElephantSetup hanElephantSetup = generateElephantSetup(Team.HAN);

        return BoardInitializer.initialize(choElephantSetup, hanElephantSetup);
    }


    private ElephantSetup generateElephantSetup(final Team team) {
        while (true) {
            try {
                outputView.printChoiceElephantSetupPrompt(team);
                final int elephantSetupNumber = inputView.readNumber(ElephantSetup.values().length);
                return ElephantSetup.of(elephantSetupNumber);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }


    private void playTurn(final JanggiGame game) {
        final Player player = game.getCurrentPlayer();

        outputView.printBoard(BoardViewResponse.from(game.getBoard()));
        outputView.printCurrentTurn(TurnResponse.from(player));

        final Position from = selectPiecePosition(game.getBoard(), player);
        final Position to = selectDestination(game.getBoard(), from);

        janggiGameService.move(game, from, to);
    }

    private Position selectPiecePosition(final Board board, final Player player) {
        final List<Position> positions = board.findPositionsByTeam(player.getTeam());

        while (true) {
            try {
                outputView.printSelectablePieces(positions, BoardViewResponse.from(board));

                final int selectedPieceNumber = inputView.readNumber(positions.size());

                final Position selected = positions.get(selectedPieceNumber - 1);

                final Piece piece = board.getPiece(selected);
                validateMovablePiece(board, piece, selected);

                return selected;
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void validateMovablePiece(final Board board, final Piece piece, final Position selected) {
        piece.calculateMovablePositions(selected, board);
    }

    private Position selectDestination(final Board board, final Position from) {
        final Piece piece = board.getPiece(from);
        final List<Position> movablePositions = piece.calculateMovablePositions(from, board);

        while (true) {
            outputView.printMovablePositions(BoardViewResponse.from(board), movablePositions, piece.getTeam());

            try {
                final int selectedDestinationNumber = inputView.readNumber(movablePositions.size());

                return movablePositions.get(selectedDestinationNumber - 1);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void printGameResult(final JanggiGame game) {
        outputView.printWinner(game.getWinner());

    }
}
