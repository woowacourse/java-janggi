package domain.rule;

import common.exception.JanggiException;
import domain.board.Board;
import domain.player.Player;
import domain.player.PlayerProfile;
import java.util.List;

public class WinnerDeterminationRuleEngine {

    private final List<WinnerDeterminationRule> scoreRules;

    public WinnerDeterminationRuleEngine(List<WinnerDeterminationRule> scoreRules) {
        this.scoreRules = scoreRules;
    }

    public PlayerProfile calculateFinalScore(Board board, Player currentPlayer, Player standbyPlayer, boolean isDraw) {
        return scoreRules.stream()
            .filter(rule -> rule.canApply(isDraw))
            .findFirst()
            .map(rule -> rule.determineWinner(board, currentPlayer, standbyPlayer))
            .orElseThrow(() -> new JanggiException("점수 계산 규칙을 찾을 수 없습니다."));
    }
}

