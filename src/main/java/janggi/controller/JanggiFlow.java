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
import java.util.function.Supplier;

public class JanggiFlow {

    private final ApplicationView view;
    private final List<ArrangementStrategy> strategies;

    public JanggiFlow(ApplicationView view) {
        this.view = view;
        this.strategies = List.of(
                new MaSangMaSang(),
                new MaSangSangMa(),
                new SangMaMaSang(),
                new SangMaSangMa()
        );
    }

    public void process() {
        ArrangementStrategy hanStrategy = askStrategy(Side.HAN);
        ArrangementStrategy choStrategy = askStrategy(Side.CHO);
        Board board = Board.create(BoardAssembler.of(hanStrategy, choStrategy));

        view.responseBoardArray(board.to2DArray());

        Side current = Side.HAN;
        for (int i = 0; i < 2; i++) {
            view.responseCurrentSide(current);
            Location locationOfPiece = retry(() -> askLocationOfPiece(board));
            Location locationToMove = retry(() -> askLocationToMove(board));

            current = current.switchTurn();
        }
    }

    private Location askLocationOfPiece(Board board) {
        List<Integer> locationOfPiece = view.requestLocationOfPiece();
        Location verifiedLocation = Location.from(locationOfPiece);
        board.validateLocation(verifiedLocation);
        board.validatePieceExist(verifiedLocation);
        return verifiedLocation;
    }

    private Location askLocationToMove(Board board) {
        List<Integer> locationToMove = view.requestLocationToMove();
        Location verifiedLocation = Location.from(locationToMove);
        board.validateLocation(verifiedLocation);
        return verifiedLocation;
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

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                view.responseErrorMessage(e);
            }
        }
    }


}
