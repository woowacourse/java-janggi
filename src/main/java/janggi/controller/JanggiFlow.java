package janggi.controller;

import janggi.controller.mapper.ArrangementMapper;
import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.strategy.ArrangementOption;
import janggi.domain.board.strategy.ArrangementStrategy;
import janggi.domain.piece.AlivePieces;
import janggi.domain.piece.Piece;
import janggi.domain.result.GameResult;
import janggi.dto.FinalResultDto;
import janggi.dto.PieceDto;
import janggi.dto.GameDto;
import janggi.dto.ScoreResultDto;
import janggi.service.JanggiService;
import janggi.view.ApplicationView;
import janggi.view.label.ArrangementStrategyLabel;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiFlow {

    private final ApplicationView view;
    private final JanggiService service;

    public JanggiFlow(ApplicationView view, JanggiService service) {
        this.view = view;
        this.service = service;
    }

    public void process() {

        GameDto gameDto = loadOrStartNewGame();

        int gameId = gameDto.gameId();
        Board board = gameDto.board();
        Side currentTurn = gameDto.turn();

        do {
            view.showBoardArray(convertBoardStatus(board));
            view.showScoreResults(convertScoreResult(board));
            view.showCurrentSide(currentTurn.getNameFormat());

            Side turnSide = currentTurn;
            retryUntilPieceIsSuccessfullyMoved(() -> {
                Location from = repeatAskLocationOfPieceUntilSuccess(turnSide, board);
                Location to = repeatAskLocationToMoveUntilSuccess(from, board);
                board.move(from, to);
            });

            currentTurn = currentTurn.switchSide();

            service.saveGame(gameId, board, currentTurn);

        } while (canContinueJanggi(board));

        view.showBoardArray(convertBoardStatus(board));
        view.showFinalResult(convertFinalResult(board));

        service.finishGame(gameId);
    }

    private GameDto loadOrStartNewGame() {
        if (service.checkIfAnyOngoingGameExists()) {
            return service.loadOngoingGame();
        }

        ArrangementStrategy hanStrategy = repeatAskStrategyUntilSuccess(Side.HAN);
        ArrangementStrategy choStrategy = repeatAskStrategyUntilSuccess(Side.CHO);
        return service.startNewGame(hanStrategy, choStrategy);
    }

    private FinalResultDto convertFinalResult(Board board) {
        AlivePieces alivePieces = board.getAlivePieces();
        GameResult gameResult = GameResult.calculate(alivePieces);
        return FinalResultDto.of(gameResult);
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }

    private boolean canContinueJanggi(Board board) {
        AlivePieces alivePieces = board.getAlivePieces();
        return alivePieces.isEveryGungAlive();
    }

    private List<List<PieceDto>> convertBoardStatus(Board board) {
        List<List<Piece>> boardIn2D = board.to2DArray();
        return boardIn2D.stream()
                .map(
                        row -> row.stream()
                                .map(PieceDto::from)
                                .toList()
                ).toList();
    }

    private ScoreResultDto convertScoreResult(Board board) {
        AlivePieces alivePieces = board.getAlivePieces();
        GameResult gameResult = GameResult.calculate(alivePieces);
        return ScoreResultDto.from(gameResult);
    }

    private ArrangementStrategy repeatAskStrategyUntilSuccess(Side side) {
        return retry(() -> askStrategy(side));
    }

    private ArrangementStrategy askStrategy(Side side) {
        Map<Integer, String> strategyInfos = ArrangementStrategyLabel.getStrategyOptions();
        int decisionNumber = view.promptForArrangementStrategyDecision(side.getNameFormat(), strategyInfos);
        ArrangementMapper mapper = ArrangementMapper.getInstance();
        ArrangementStrategyLabel label = ArrangementStrategyLabel.createArrangementOption(decisionNumber);
        ArrangementOption option = mapper.findArrangementOption(label);
        return option.toStrategy(side);
    }

    private Location repeatAskLocationOfPieceUntilSuccess(Side turnSide, Board board) {
        return retry(() -> askLocationOfPiece(turnSide, board));
    }

    private Location askLocationOfPiece(Side current, Board board) {
        List<Integer> locationOfPiece = view.promptForLocationOfPiece();
        Location verifiedLocation = Location.from(locationOfPiece);
        board.validateLocationOfPiece(current, verifiedLocation);
        return verifiedLocation;
    }

    private Location repeatAskLocationToMoveUntilSuccess(Location from, Board board) {
        return retry(() -> askLocationToMove(from, board));
    }

    private Location askLocationToMove(Location startingLocation, Board board) {
        List<Integer> locationToMove = view.promptForLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocationToMove(startingLocation, verifiedLocation);
        return verifiedLocation;
    }

    private void retryUntilPieceIsSuccessfullyMoved(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }
}
