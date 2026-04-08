package janggi.controller;

import janggi.controller.mapper.ArrangementMapper;
import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.ArrangementOption;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.BoardAssembler;
import janggi.view.ApplicationView;
import janggi.view.label.ArrangementStrategyLabel;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class JanggiFlow {

    private final ApplicationView view;

    public JanggiFlow(ApplicationView view) {
        this.view = view;
    }

    public void process() {
        ArrangementStrategy hanStrategy = repeatAskStrategyUntilSuccess(Side.HAN);
        ArrangementStrategy choStrategy = repeatAskStrategyUntilSuccess(Side.CHO);
        Board board = Board.create(BoardAssembler.from(List.of(hanStrategy, choStrategy)));

        Side current = Side.HAN;
        while (board.isNotEmpty()) {
            view.showBoardArray(convertBoardStatus(board));
            view.showCurrentSide(current.getName());

            final Side turnSide = current;
            retryUntilPieceIsSuccessfullyMoved(() -> {
                Location from = repeatAskLocationOfPieceUntilSuccess(turnSide, board);
                Location to = repeatAskLocationToMoveUntilSuccess(from, board);
                board.move(from, to);
            });
            current = current.switchSide();
        }
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

    private List<List<String>> convertBoardStatus(Board board) {
        List<List<Piece>> boardIn2D = board.to2DArray();
        return boardIn2D.stream()
                .map(
                        row -> row.stream()
                                .map(Piece::getPieceType)
                                .map(PieceType::getNameFormat)
                                .toList()
                ).toList();
    }

    private ArrangementStrategy repeatAskStrategyUntilSuccess(Side side) {
        return retry(() -> askStrategy(side));
    }

    private ArrangementStrategy askStrategy(Side side) {
        Map<Integer, String> strategyInfos = ArrangementStrategyLabel.getStrategyOptions();
        int decisionNumber = view.promptForArrangementStrategyDecision(side.getName(), strategyInfos);
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
