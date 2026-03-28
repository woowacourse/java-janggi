package janggi.controller;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.BoardAssembler;
import janggi.view.ApplicationView;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JanggiFlow {

    private final List<ArrangementStrategy> strategies;
    private final ApplicationView view;

    public JanggiFlow(List<ArrangementStrategy> strategies, ApplicationView view) {
        this.strategies = strategies;
        this.view = view;
    }

    public void process() {
        ArrangementStrategy hanStrategy = repeatAskStrategyUntilSuccess(Side.HAN);
        ArrangementStrategy choStrategy = repeatAskStrategyUntilSuccess(Side.CHO);
        Board board = Board.create(BoardAssembler.of(hanStrategy, choStrategy));

        Side current = Side.HAN;
        while (board.isNotEmpty()) {
            view.responseBoardArray(convertBoardStatus(board));
            view.responseCurrentSide(current.getName());

            final Side turnSide = current;
            retryUntilPieceIsSuccessfullyMoved(() -> {
                Location from = repeatAskLocationOfPieceUntilSuccess(turnSide, board);
                Location to = repeatAskLocationToMoveUntilSuccess(from, turnSide, board);
                board.move(from, to);
            });
            current = current.switchSide();
        }
    }

    private List<List<String>> convertBoardStatus(Board board) {
        List<List<Piece>> boradIn2D = board.to2DArray();
        return boradIn2D.stream()
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
            view.responseErrorMessage(e);
            return repeatAskStrategyUntilSuccess(side);
        }
    }

    private ArrangementStrategy askStrategy(Side side) {
        Map<Integer, String> strategyInfos = convertStrategyInfo();
        int decisionNumber = view.requestArrangementStrategyDecision(side.getName(), strategyInfos);
        return findStrategyWithCorrespondingDecisionNumber(decisionNumber);
    }

    private Map<Integer, String> convertStrategyInfo() {
        Map<Integer, String> strategyInfos = new LinkedHashMap<>();
        for (ArrangementStrategy strategy : strategies) {
            strategyInfos.put(strategy.decisionNumber(), strategy.name());
        }
        return strategyInfos;
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
            return askLocationToMove(from, board);
        } catch (IllegalArgumentException e) {
            view.responseErrorMessage(e);
            return repeatAskLocationToMoveUntilSuccess(from, turnSide, board);
        }
    }

    private Location askLocationToMove(Location startingLocation, Board board) {
        List<Integer> locationToMove = view.requestLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocationToMove(startingLocation, verifiedLocation);
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
