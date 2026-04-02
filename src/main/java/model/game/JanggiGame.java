package model.game;

import model.board.Board;
import model.board.Country;
import model.move.Move;
import model.pieces.Piece;

public class JanggiGame {
    private final Board board;
    private Country turn;

    public JanggiGame(Board board) {
        this.board = board;
        this.turn = Country.CHO;
    }

    public void move(Move move){
        validateTurn(move);
        board.move(move);
        changeTurn();
    }

    private void validateTurn(Move move){
        Piece piece = board.findPiece(move.from());

        if(piece==null){
            throw new IllegalArgumentException("[ERROR] 기물이 없습니다.");
        }

        if(piece.country()!=turn){
            throw new IllegalArgumentException("[ERROR] 자기 나라의 기물만 이동할 수 있습니다.");
        }
    }

    private void changeTurn(){
        if(turn==Country.CHO){
            turn = Country.HAN;
            return;
        }
        turn = Country.CHO;
    }

    public Country turn(){
        return turn;
    }

    public Board board(){
        return board;
    }
}
