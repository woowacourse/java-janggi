package domain.game;

import domain.board.Board;
import domain.game.state.ChoTurn;
import domain.game.state.GameState;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private Players players;
    private Board board;
    private List<Piece> caughtPieces;
    private GameState gameState;

    public Game(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.caughtPieces = new ArrayList<>();
        this.gameState = new ChoTurn(this);
    }

    public void move(Position source, Position destination) {
        gameState.move(source, destination);
    }

    public void changeState(GameState gameState) {
        this.gameState = gameState;
    }

    public Piece movePiece(Position source, Position destination) {
        Piece caughtPiece = board.move(source, destination);
        if (!caughtPiece.isNone()) {
            caughtPieces.add(caughtPiece);
        }
        return caughtPiece;
    }

    public void catchPiece(Piece caughtPiece) {
        caughtPieces.add(caughtPiece);
    }

    public boolean isCho(Position position) {
        return board.isCho(position);
    }

    public boolean isHan(Position position) {
        return board.isHan(position);
    }

    public Map<Position, Piece> getBoardMap() {
        return board.getBoardMap();
    }

    public boolean isRunning() {
        return gameState.isRunning();
    }

    public String getCurrentPlayerName() {
        Team currentTeam = gameState.getCurrentTeam();
        Player player = players.getByTeam(currentTeam);
        return player.getName();
    }

    public Team getCurrentTeam() {
        return gameState.getCurrentTeam();
    }

    public Team getWinner() {
        return gameState.getWinner();
    }
}
