package domain.game;

import domain.board.Board;
import domain.game.state.ChoTurn;
import domain.game.state.GameState;
import domain.game.state.HanTurn;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import domain.position.Position;
import domain.score.Score;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static Game restore(Players players, Board board, List<Piece> caughtPieces, Team currentTeam) {
        Game game = new Game(players, board);
        game.caughtPieces = new ArrayList<>(caughtPieces);
        if (currentTeam.isHan()) {
            game.gameState = new HanTurn(game);
        }
        return game;
    }

    public Set<Position> select(Position source) {
        return gameState.selectPiece(source);
    }

    public void move(Position source, Position destination) {
        gameState.move(source, destination);
    }

    public void changeState(GameState gameState) {
        this.gameState = gameState;
    }

    public void movePiece(Position source, Position destination) {
        catchPieceBeforeMove(destination);
        board.move(source, destination);
    }

    public void catchPieceBeforeMove(Position destination) {
        Piece destinationPiece = board.findPiece(destination);
        if (!destinationPiece.isNone()) {
            caughtPieces.add(destinationPiece);
        }
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
        return player.name().value();
    }

    public Team getCurrentTeam() {
        return gameState.getCurrentTeam();
    }

    public Team getWinner() {
        return gameState.getWinner();
    }

    public Set<Position> findMovablePositions(Position source) {
        return board.findMovablePositions(source);
    }

    public boolean isJangCaught() {
        return caughtPieces.stream()
                .anyMatch(Piece::isJang);
    }

    public List<Piece> getCaughtPieces() {
        return List.copyOf(caughtPieces);
    }

    public String getChoPlayerName() {
        return players.getChoPlayerName();
    }

    public String getHanPlayerName() {
        return players.getHanPlayerName();
    }

    public Score getScore() {
        return Score.from(board.getRemainPieces());
    }
}
