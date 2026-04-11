package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력을 하셨습니다.");
        }
    }
}
