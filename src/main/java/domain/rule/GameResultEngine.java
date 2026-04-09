package domain.rule;

import common.exception.JanggiException;
import domain.board.Board;
import domain.player.Player;
import domain.player.PlayerProfile;
import java.util.List;

public class GameResultEngine {

    private final List<DrawRule> drawRules;
    private final List<WinnerDeterminationRule> winnerRules;

    public GameResultEngine(List<DrawRule> drawRules, List<WinnerDeterminationRule> winnerRules) {
        this.drawRules = drawRules;
        this.winnerRules = winnerRules;
    }

    public boolean isDraw(Board board) {
        return drawRules.stream().anyMatch(rule -> rule.isDraw(board));
    }

    public PlayerProfile calculateFinalScore(Board board, Player currentPlayer, Player standbyPlayer, boolean isDraw) {
        return winnerRules.stream()
            .filter(rule -> rule.canApply(isDraw))
            .findFirst()
            .map(rule -> rule.determineWinner(board, currentPlayer, standbyPlayer))
            .orElseThrow(() -> new JanggiException("점수 계산 규칙을 찾을 수 없습니다."));
    }
}
