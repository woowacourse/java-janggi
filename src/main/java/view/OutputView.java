package view;

import java.util.Map;

import domain.Country;
import domain.Piece;
import domain.Position;
import service.dto.BoardDto;

public class OutputView {
    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printEndMessage() {
        System.out.println("게임을 종료합니다.");
    }

    public void printErrorMessage(String message){
        System.out.println(message);
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printBoard(BoardDto boardDto) {
        for (BoardDto.Row dto : boardDto.rows()){
            System.out.println(String.join(" ",dto.row()));
        }

    }


}
