package janggi.controller;

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
    }

    public ArrangementStrategy askStrategy(Side side) {
        int decisionNumber = view.requestArrangementStrategyDecision(side, strategies);
        return findStrategyWithCorrespondingDecisionNumber(decisionNumber);
    }

    private ArrangementStrategy findStrategyWithCorrespondingDecisionNumber(int decisionNumber) {
        return strategies.stream()
                .filter(strategy -> strategy.isDecisionNumberMatching(decisionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 전략 번호가 없습니다: " + decisionNumber));
    }
}
