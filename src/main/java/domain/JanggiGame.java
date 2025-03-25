package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final JanggiBoard janggiBoard;
    private final Players players;

    public JanggiGame(BoardGenerator boardGenerator, List<String> playerNames) {
        this.janggiBoard = new JanggiBoard(boardGenerator);
        this.players = Players.ofNames(playerNames.getFirst(), playerNames.getLast());
    }

    public void move(List<Integer> startRowAndColumn, List<Integer> targetRowAndColumn) {
        Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
        Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
        validateSelectedPiece(startPosition, targetPosition);
        janggiBoard.move(startPosition, targetPosition);
        players.nextTurn();
    }

    public boolean isEnd() {
        boolean isChoGungDead = !janggiBoard.existGung(Team.CHO);
        boolean isHanGungDead = !janggiBoard.existGung(Team.HAN);

        return isChoGungDead || isHanGungDead;
    }

    private void validateSelectedPiece(Position startPosition, Position targetPosition) {
        Piece selectedPiece = janggiBoard.findSelectedPiece(startPosition);
        if (!players.isSameTeamThisTurnPlayerAndPiece(selectedPiece)) {
            throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
        }
        if (startPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
    }

    public Player getThisTurnPlayer() {
        return players.getThisTurnPlayer();
    }

    public Map<Position, Piece> getBoardState() {
        return janggiBoard.getBoard();
    }
}
