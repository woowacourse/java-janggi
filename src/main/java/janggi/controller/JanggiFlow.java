package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.BoardAssembler;
import janggi.view.ApplicationView;
import java.util.List;
import java.util.Map;

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
        try {
            return askStrategy(side);
        } catch (IllegalArgumentException e) {
            view.showErrorMessage(e.getMessage());
            return repeatAskStrategyUntilSuccess(side);
        }
    }

    private ArrangementStrategy askStrategy(Side side) {
        Map<Integer, String> strategyInfos = ArrangementOption.getStrategyOptions();
        int decisionNumber = view.promptForArrangementStrategyDecision(side.getName(), strategyInfos);
        return ArrangementOption.createStrategyOf(side, decisionNumber);
    }

    private Location repeatAskLocationOfPieceUntilSuccess(Side turnSide, Board board) {
        try {
            return askLocationOfPiece(turnSide, board);
        } catch (IllegalArgumentException e) {
            view.showErrorMessage(e.getMessage());
            return repeatAskLocationOfPieceUntilSuccess(turnSide, board);
        }
    }

    private Location askLocationOfPiece(Side current, Board board) {
        List<Integer> locationOfPiece = view.promptForLocationOfPiece();
        Location verifiedLocation = Location.from(locationOfPiece);
        board.validateLocationOfPiece(current, verifiedLocation);
        return verifiedLocation;
    }

    private Location repeatAskLocationToMoveUntilSuccess(Location from, Board board) {
        try {
            return askLocationToMove(from, board);
        } catch (IllegalArgumentException e) {
            view.showErrorMessage(e.getMessage());
            return repeatAskLocationToMoveUntilSuccess(from, board);
        }
    }

    private Location askLocationToMove(Location startingLocation, Board board) {
        List<Integer> locationToMove = view.promptForLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocationToMove(startingLocation, verifiedLocation);
        return verifiedLocation;
    }

    private void retryUntilPieceIsSuccessfullyMoved(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            view.showErrorMessage(e.getMessage());
            retryUntilPieceIsSuccessfullyMoved(runnable);
        }
    }
}
