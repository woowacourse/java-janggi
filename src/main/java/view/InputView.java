package view;

import java.util.Scanner;

public class InputView {
    static Scanner sc = new Scanner(System.in);

    public static String readArrangement() {
        System.out.println("1. 마상상마");
        System.out.println("2. 상마마상");
        System.out.println("3. 마상마상");
        System.out.println("4. 상마상마");
        return sc.nextLine();
    }
}
