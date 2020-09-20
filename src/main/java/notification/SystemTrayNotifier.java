package notification;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

public class SystemTrayNotifier {
    public static void notify(String message, String tooltip, String url) throws AWTException {
        if (SystemTray.isSupported()) {
            displayTray(message, tooltip, url);
        } else {
            System.err.println("System tray not supported!");
        }
    }

    private static void displayTray(String message, String tooltip, String url) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage(SystemTrayNotifier.class.getClassLoader().getResource("images/3080.jpg"));

        TrayIcon trayIcon = new TrayIcon(image, "Notification");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(tooltip);
        tray.add(trayIcon);

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI.create(url));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        trayIcon.displayMessage(tooltip, message, TrayIcon.MessageType.INFO);
    }
}
