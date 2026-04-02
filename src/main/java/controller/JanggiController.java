package controller;

import controller.response.BoardView;
import controller.response.Turn;
import domain.JanggiGame;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.ElephantSetup;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Team;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Player choPlayer = generateChoPlayer();
        final Player hanPlayer = generateHanPlayer();

        final Board board = initializeBoard();
        final JanggiGame game = new JanggiGame(board, choPlayer, hanPlayer);

        // TODO: 승패 조건 추가 후 수정
//        while (true) {
        for (int test = 0; test < 10; test++) {
            playTurn(game);
        }
    }


    private Player generateHanPlayer() {
        while (true) {
            try {
                outputView.printEnterHanPlayerNamePrompt();
                final String hanPlayerName = inputView.readPlayerName();
                return Player.of(hanPlayerName, Team.HAN);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Player generateChoPlayer() {
        while (true) {
            try {
                outputView.printEnterChoPlayerNamePrompt();
                final String choPlayerName = inputView.readPlayerName();
                return Player.of(choPlayerName, Team.CHO);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }


    private Board initializeBoard() {
        final ElephantSetup choElephantSetup = generateChoElephantSetup();
        final ElephantSetup hanElephantSetup = generateHanElephantSetup();

        return BoardInitializer.initialize(choElephantSetup, hanElephantSetup);
    }

    private ElephantSetup generateHanElephantSetup() {
        while (true) {
            try {
                outputView.printChoiceHanElephantSetupPrompt();
                final int hanElephantSetupNumber = inputView.readNumber(ElephantSetup.values().length);
                return ElephantSetup.of(hanElephantSetupNumber);
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private ElephantSetup generateChoElephantSetup() {
        while (true) {
            try {
                outputView.printChoiceChoElephantSetupPrompt();
                final int choElephantSetupNumber = inputView.readNumber(ElephantSetup.values().length);
                return ElephantSetup.of(choElephantSetupNumber);
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
                piece.calculateMovablePositions(selected, board);

                return selected;
            } catch (final IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
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
