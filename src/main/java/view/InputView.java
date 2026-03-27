package view;

import java.util.Scanner;
import model.board.Country;

public class InputView {

    static Scanner sc = new Scanner(System.in);

    public static String readArrangement(Country country) {
        System.out.println(country.color() + "1. 마상상마" + Country.RESET);
        System.out.println(country.color() + "2. 상마마상" + Country.RESET);
        System.out.println(country.color() + "3. 마상마상" + Country.RESET);
        System.out.println(country.color() + "4. 상마상마" + Country.RESET);
        return sc.nextLine();
    }
}
