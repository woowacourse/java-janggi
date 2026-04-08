package domain.manager;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.BasicPiece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import domain.rule.BigJangDrawRule;
import domain.rule.DrawGameWinnerRule;
import domain.rule.NormalGameWinnerRule;
import domain.rule.DrawRuleEngine;
import domain.rule.WinnerDeterminationRuleEngine;
import java.util.List;

public class JanggiGameManager {

    private final Board board;
    private final DrawRuleEngine drawRuleEngine;
    private final WinnerDeterminationRuleEngine winnerDeterminationRuleEngine;
    private Player currentPlayer;
    private Player standbyPlayer;
    private boolean isGameRunning;
    private boolean isDraw;

    public JanggiGameManager(Player choPlayer, Player hanPlayer, Formation choFormation, Formation hanFormation) {
        this.currentPlayer = choPlayer;
        this.standbyPlayer = hanPlayer;
        this.board = BoardFactory.createWithFormation(choFormation, hanFormation);
        this.drawRuleEngine = new DrawRuleEngine(List.of(new BigJangDrawRule()));
        this.winnerDeterminationRuleEngine = new WinnerDeterminationRuleEngine(List.of(new NormalGameWinnerRule(), new DrawGameWinnerRule()));
        this.isGameRunning = true;
        this.isDraw = false;
    }

    private JanggiGameManager(Player choPlayer, Player hanPlayer, Board loadedBoard, Team currentTeam) {
        this.board = loadedBoard;
        this.drawRuleEngine = new DrawRuleEngine(List.of(new BigJangDrawRule()));
        this.winnerDeterminationRuleEngine = new WinnerDeterminationRuleEngine(List.of(new NormalGameWinnerRule(), new DrawGameWinnerRule()));
        this.isGameRunning = true;
        this.isDraw = false;

        if (currentTeam == Team.CHO) {
            this.currentPlayer = choPlayer;
            this.standbyPlayer = hanPlayer;
        } else {
            this.currentPlayer = hanPlayer;
            this.standbyPlayer = choPlayer;
        }
    }

    public static JanggiGameManager fromLoadedState(Player choPlayer, Player hanPlayer, Board loadedBoard, Team currentTeam) {
        return new JanggiGameManager(choPlayer, hanPlayer, loadedBoard, currentTeam);
    }

    public void move(Position source, Position destination) {
        BasicPiece caughtPiece = board.move(source, destination, currentPlayer);

        if (caughtPiece.isType(PieceType.JANG)) {
            isGameRunning = false;
            return;
        }

        if (drawRuleEngine.isDraw(board)) {
            isDraw = true;
            isGameRunning = false;
            return;
        }

        switchTurn();
    }

    public void validateSource(Position source) {
        board.validateSource(source, currentPlayer);
    }

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = standbyPlayer;
        standbyPlayer = temp;
    }

    public void endGame() {
        isGameRunning = false;
    }

    public PlayerProfile calculateFinalScore() {
        return winnerDeterminationRuleEngine.calculateFinalScore(board, currentPlayer, standbyPlayer, isDraw);
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}


