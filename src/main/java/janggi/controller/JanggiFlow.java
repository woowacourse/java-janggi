package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.BoardAssembler;
import janggi.strategy.MaSangMaSang;
import janggi.strategy.MaSangSangMa;
import janggi.strategy.SangMaMaSang;
import janggi.strategy.SangMaSangMa;
import janggi.view.ApplicationView;
import java.util.List;

public class JanggiFlow {

    private final ApplicationView view;
    private final List<ArrangementStrategy> strategies;

    public JanggiFlow(ApplicationView view) {
        this.view = view;
        this.strategies = List.of(
                MaSangMaSang.getInstance(),
                MaSangSangMa.getInstance(),
                SangMaMaSang.getInstance(),
                SangMaSangMa.getInstance()
        );
    }

    public void process() {
        ArrangementStrategy hanStrategy = repeatAskStrategyUntilSuccess(Side.HAN);
        ArrangementStrategy choStrategy = repeatAskStrategyUntilSuccess(Side.CHO);
        Board board = Board.create(BoardAssembler.of(hanStrategy, choStrategy));

        Side current = Side.HAN;
        while (board.isNotEmpty()) {
            view.responseBoardArray(board.to2DArray());
            view.responseCurrentSide(current);

            final Side turnSide = current;
            retryUntilPieceIsSuccessfullyMoved(() -> {
                Location from = repeatAskLocationOfPieceUntilSuccess(turnSide, board);
                Location to = repeatAskLocationToMoveUntilSuccess(from, turnSide, board);
                board.move(from, to);
            });
            current = current.switchSide();
        }
    }

    private ArrangementStrategy repeatAskStrategyUntilSuccess(Side side) {
        try {
            return askStrategy(side);
        } catch (IllegalArgumentException e) {
            view.responseErrorMessage(e);
            return repeatAskStrategyUntilSuccess(side);
        }
    }

    private ArrangementStrategy askStrategy(Side side) {
        int decisionNumber = view.requestArrangementStrategyDecision(side, strategies);
        return findStrategyWithCorrespondingDecisionNumber(decisionNumber);
    }

    private ArrangementStrategy findStrategyWithCorrespondingDecisionNumber(int decisionNumber) {
        return strategies.stream()
                .filter(strategy -> strategy.isDecisionNumberMatching(decisionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 전략 번호가 없습니다: " + decisionNumber));
    }

    private Location repeatAskLocationOfPieceUntilSuccess(Side turnSide, Board board) {
        try {
            return askLocationOfPiece(turnSide, board);
        } catch (IllegalArgumentException e) {
            view.responseErrorMessage(e);
            return repeatAskLocationOfPieceUntilSuccess(turnSide, board);
        }
    }

    private Location askLocationOfPiece(Side current, Board board) {
        List<Integer> locationOfPiece = view.requestLocationOfPiece();
        Location verifiedLocation = Location.from(locationOfPiece);
        board.validateLocationOfPiece(current, verifiedLocation);
        return verifiedLocation;
    }

    private Location repeatAskLocationToMoveUntilSuccess(Location from, Side turnSide, Board board) {
        try {
            return askLocationToMove(from, turnSide, board);
        } catch (IllegalArgumentException e) {
            view.responseErrorMessage(e);
            return repeatAskLocationToMoveUntilSuccess(from, turnSide, board);
        }
    }

    private Location askLocationToMove(Location startingLocation, Side current, Board board) {
        List<Integer> locationToMove = view.requestLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocationToMove(startingLocation, verifiedLocation, current);
        return verifiedLocation;
    }

    private void retryUntilPieceIsSuccessfullyMoved(Runnable runnable) {
        try {
            runnable.run();
        } catch (IllegalArgumentException e) {
            view.responseErrorMessage(e);
            retryUntilPieceIsSuccessfullyMoved(runnable);
        }
    }
}
