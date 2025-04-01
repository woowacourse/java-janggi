package janggi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class ShellExecutor {

    public static ExecutionResult executeShellCommand(String command, int timeout)
        throws IOException, InterruptedException {
        String osType = getCommandForOS();
        String[] cmd = osType.equals("Windows") ? new String[]{"cmd.exe", "/c", command}
            : new String[]{"/bin/bash", "-c", command};

        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        String output = readStream(process.getInputStream());
        String error = readStream(process.getErrorStream());

        boolean finished = process.waitFor(timeout, TimeUnit.SECONDS);
        if (!finished) {
            process.destroy();
            throw new IOException("Timeout: Command execution exceeded " + timeout + " seconds.");
        }

        int exitCode = process.exitValue();
        return new ExecutionResult(output, error, exitCode);
    }

    private static String getCommandForOS() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? "Windows" : "Unix";
    }

    private static String readStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            reader.lines().forEach(line -> output.append(line).append("\n"));
        }
        return output.toString().trim();
    }

    public record ExecutionResult(
        String output,
        String error,
        int exitCode
    ) {

    }
}
