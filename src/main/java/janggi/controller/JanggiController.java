package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece_initiaizer.StaticPieceInitializer;
import janggi.infra.PieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceRepository pieceRepository;

    public JanggiController(final InputView inputView, final OutputView outputView, final PieceRepository pieceRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pieceRepository = pieceRepository;
    }

    public void run() {
        final Board board = initializeBoard();

        while (!board.isEnd()) {
            outputView.printBoard(board.getBoard());
            CommandType type = inputView.inputCommand(board.getCurrentCountry());
            if (type == CommandType.MOVE) {
                final MoveDto dto = inputView.inputMove();
                board.move(dto.startPosition(), dto.endPosition());
            }
            if (type == CommandType.SAVE) {
                final int number = inputView.getSaveNumber();
                final Map<Country, List<Piece>> result = board.getBoard();
                pieceRepository.saveAllPieces(number, Country.CHO, result.get(Country.CHO));
                pieceRepository.saveAllPieces(number, Country.HAN, result.get(Country.HAN));
            }
        }

        final Country winner = board.getWinner();
        outputView.outputWinner(winner);
    }

    public Board initializeBoard() {
        final CommandType type = inputView.getStartType();
        if (type == CommandType.CONTINUE) {
            final int number = inputView.getStartFileNumber();
            final Map<Country, List<Piece>> allPieces = pieceRepository.findAllPieces(number);
            return new Board(
                    new Team(allPieces.get(Country.CHO), Country.CHO),
                    new Team(allPieces.get(Country.HAN), Country.HAN)
            );
        }
        if (type == CommandType.NEW_GAME) {
            final StartingPosition choStartingPosition = inputView.getStartPositionOf(Country.CHO);
            final StartingPosition hanStartingPosition = inputView.getStartPositionOf(Country.HAN);
            return new Board(
                    new Team(choStartingPosition, new StaticPieceInitializer(), Country.CHO),
                    new Team(hanStartingPosition, new StaticPieceInitializer(), Country.HAN)
            );
        }
        throw new IllegalStateException();
    }
}
