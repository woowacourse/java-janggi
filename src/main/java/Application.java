import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import org.h2.tools.Server;

import controller.JanggiController;
import view.InputView;
import view.OutputView;
import view.OutputViewFormatter;

public class Application {
    public static void main(String[] args){
        try{
            Server.createWebServer("-web", "-webPort", "8082").start();
            Connection conn = DriverManager.getConnection("jdbc:h2:~/janggiGame", "sa", "");
            System.out.println("DB 연결 성공!");


            InputView inputView = new InputView(new Scanner(System.in));
            OutputView outputView = new OutputView(new OutputViewFormatter());

            try{
                JanggiController janggiController = new JanggiController (inputView, outputView);
                janggiController.run();
            } catch (IllegalStateException e){
            }

            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("DB 연결 실패");

        }


    }
}
