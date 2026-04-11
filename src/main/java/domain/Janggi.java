package domain;

import domain.board.Board;
import domain.place.piece.Piece;
import domain.place.piece.Side;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import java.util.Optional;

public class Janggi {

    private final Players players;
    private final Board board;
    private Long id;
    private Player currentPlayer;
    private boolean gameOver;
    private double choScore = 72;
    private double hanScore = 73.5;

    public Janggi(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.currentPlayer = players.getPlayerBySide(Side.CHO);
    }

    public Janggi(Long id, Players players, Board board, Player currentPlayer, boolean gameOver, double choScore,
                  double hanScore) {
        this.id = id;
        this.players = players;
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<List<String>> getBoardFormat() {
        return board.getFormatBoard();
    }

    public void playOneTurn(Position from, Position to) {
        validateFromPiece(from, to);

        Optional<Piece> capturedPiece = board.findPiece(to);
        board.move(from, to);
        calculateScore(capturedPiece);

        if (endGameIfGeneralCaptured(capturedPiece)) {
            return;
        }

        changeTurn();
    }

    public List<Double> getGameTotalScore() {
        return List.of(choScore, hanScore);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return currentPlayer;
    }

    public Long id() {
        return id;
    }

    public Players players() {
        return players;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 id가 존재합니다.");
        }
        this.id = id;
    }

    public Board board() {
        return board;
    }

    private void validateFromPiece(Position from, Position to) {
        Piece piece = getRequiredPiece(from);
        validateOwnPiece(piece);
        validateDestinationNotOccupiedBySameSide(piece, to);
    }

    private Piece getRequiredPiece(Position position) {
        return board.findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 현재 위치에 기물이 없습니다."));
    }

    private void validateOwnPiece(Piece piece) {
        if (!piece.isSameSide(currentPlayer.getSide())) {
            throw new IllegalArgumentException("[ERROR] 선택하신 피스가 같은 편 피스가 아닙니다.");
        }
    }

    private void validateDestinationNotOccupiedBySameSide(Piece piece, Position to) {
        Optional<Piece> target = board.findPiece(to);

        if (target.isPresent() && piece.isSameSide(target.get())) {
            throw new IllegalArgumentException("[ERROR] 이동하실 위치에 같은 편 기물이 존재합니다.");
        }
    }

    private void changeTurn() {
        if (currentPlayer.getSide() == Side.CHO) {
            currentPlayer = players.getPlayerBySide(Side.HAN);
            return;
        }
        currentPlayer = players.getPlayerBySide(Side.CHO);
    }

    private void subtractOpponentScore(double score) {
        if (currentPlayer.getSide() == Side.CHO) {
            hanScore -= score;
            return;
        }
        choScore -= score;
    }

    private void calculateScore(Optional<Piece> toPiece) {
        double score = toPiece.map(Piece::getScore)
                .orElse(0.0);
        subtractOpponentScore(score);
    }

    private boolean endGameIfGeneralCaptured(Optional<Piece> toPiece) {
        if (toPiece.filter(Piece::isGeneral).isPresent()) {
            this.gameOver = true;
            return true;
        }
        return false;
    }
}
