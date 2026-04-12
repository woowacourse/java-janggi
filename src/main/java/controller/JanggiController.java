package controller;

import controller.response.BoardView;
import controller.response.Turn;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Team;
import view.InputView;
import view.OutputView;

import java.util.List;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final JanggiGame game = initializeGame();

        while (game.isPlaying()) {
            playTurn(game);
        }
    }

    private JanggiGame initializeGame() {
        final Player choPlayer = generatePlayer(Team.CHO);
        final Player hanPlayer = generatePlayer(Team.HAN);

        final Board board = initializeBoard();
        return new JanggiGame(board, choPlayer, hanPlayer);
    }


    private Player generatePlayer(final Team team) {
        while (true) {
            try {
                outputView.printEnterPlayerNamePrompt(team);
                final String playerName = inputView.readPlayerName();
                return Player.of(playerName, team);
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

        outputView.printBoard(BoardView.from(game.getBoard()));
        outputView.printCurrentTurn(Turn.from(player));

        final Position from = selectPiecePosition(game.getBoard(), player);
        final Position to = selectDestination(game.getBoard(), from);

        game.movePiece(from, to);
    }

    private Position selectPiecePosition(final Board board, final Player player) {
        final List<Position> positions = board.findPositionsByTeam(player.getTeam());

        while (true) {
            try {
                outputView.printSelectablePieces(positions, BoardView.from(board));

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
            outputView.printMovablePositions(movablePositions);

            try {
                final int selectedDestinationNumber = inputView.readNumber(movablePositions.size());

                return movablePositions.get(selectedDestinationNumber - 1);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
