import checker.Availability;
import checker.AvailabilityChecker;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Driver class to run application.
 */
public class Main {
    private static final List<AvailabilityChecker> checkers = new ArrayList<>();
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Main.class);

    private static void setupProperties() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
        System.setProperty("webdriver.chrome.logfile", System.getProperty("user.dir") + "/drivers/chromedriver.log");
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    private static void readLinkInput() throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BufferedReader bufferedReader =  new BufferedReader(new FileReader(System.getProperty("user.dir") + "/input/websitesToCheck.txt"));
        String line = bufferedReader.readLine();

        while(line != null) {
            if(!line.isEmpty() && !line.trim().startsWith("http")) {
                String className = AvailabilityChecker.class.getPackage().getName() + "." + line.trim();
                Constructor<?> constructor = Class.forName(className).getConstructors()[0];
                List<String> sites = new ArrayList<>();
                line = bufferedReader.readLine();

                while(line != null && line.trim().startsWith("http")) {
                    sites.add(line);
                    line = bufferedReader.readLine();
                }
                checkers.add((AvailabilityChecker) constructor.newInstance((Object) sites.toArray(new String[0])));
            }
            else {
                line = bufferedReader.readLine();
            }
        }
        bufferedReader.close();
    }

    private static void processAllLinks() throws IOException, InterruptedException {
        for(AvailabilityChecker checker : checkers) {
            checker.isAvailable();
        }
    }

    private static void prettyPrintMap(Map<String, Availability> availabilityMap) throws IOException {
        for(String productURL : availabilityMap.keySet()) {
            System.out.println(availabilityMap.get(productURL).getVal() + " : " + productURL);
        }
    }

    public static void main(String[] args) {
        setupProperties();
        while(true) {
            try {
                readLinkInput();
                processAllLinks();
                Thread.sleep(1000);
            } catch (Exception exception) {
                exception.printStackTrace();
                break;
            }
        }
    }
}
