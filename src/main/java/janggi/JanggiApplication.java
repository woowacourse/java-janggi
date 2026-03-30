package janggi;

public class JanggiApplication {
    public static void main(String[] args) {
        AppConfig app = new AppConfig();
        JanggiGame janggi = app.janggi();
        janggi.run();
    }
}
