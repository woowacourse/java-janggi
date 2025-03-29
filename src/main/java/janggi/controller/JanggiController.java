package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.piece.Piece;
import janggi.dto.CommandType;
import janggi.dto.GameStartType;
import janggi.dto.MoveDto;
import janggi.infra.repository.piece_repository.PieceRepository;
import janggi.infra.repository.turn_repository.TurnRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceRepository pieceRepository;
    private final TurnRepository turnRepository;

    public JanggiController(final InputView inputView, final OutputView outputView, final PieceRepository pieceRepository, final TurnRepository turnRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pieceRepository = pieceRepository;
        this.turnRepository = turnRepository;
    }

    public void run() {
        final Board board = initializeBoard();

        while (!board.isEnd()) {
            outputView.outputBoard(board.getBoard());
            final CommandType type = inputView.inputCommand(board.getCurrentCountry());
            switch (type) {
                case MOVE -> {
                    final MoveDto dto = inputView.inputMove();
                    board.move(dto.startPosition(), dto.endPosition());
                }
                case SAVE -> {
                    saveGame(board);
                    return;
                }
            }
        }

        final Country winner = board.getWinner();
        final int winnerScore = board.getWinnerScore();
        final int looserScore = board.getLooserScore();
        outputView.outputWinner(winner, winnerScore, looserScore);
    }

    private Board initializeBoard() {
        final GameStartType type = inputView.inputStartType();
        return switch (type) {
            case NEW_GAME -> {
                final StartingPosition choStartingPosition = inputView.inputStartPositionOf(Country.CHO);
                final StartingPosition hanStartingPosition = inputView.inputStartPositionOf(Country.HAN);
                yield Board.start(choStartingPosition, hanStartingPosition);
            }
            case CONTINUE -> {
                final int number = inputView.inputStartFileNumber();
                final Map<Country, List<Piece>> allPieces = pieceRepository.findAllPieces(number);
                final Country turn = turnRepository.findNextTurn(number);
                yield Board.continueWith(allPieces, turn);
            }
        };
    }

    private void saveGame(final Board board) {
        final int number = inputView.inputSaveNumber();
        final Map<Country, List<Piece>> result = board.getBoard();
        pieceRepository.saveAllPieces(number, Country.CHO, result.get(Country.CHO));
        pieceRepository.saveAllPieces(number, Country.HAN, result.get(Country.HAN));
        turnRepository.saveTurn(number, board.getCurrentCountry());
    }
}
