package ui;

import javax.swing.*;
import java.awt.*;

// Represents a splash screen that welcomes the user to interact with the store
public class SplashScreen extends JFrame {
    private static final String COVER = "./data/thriftCover.jpg";
    private JLabel welcomeLabel;

    // EFFECTS: creates a splash screen that displays the image of a thrift store
    public SplashScreen() {
        super("Wendy's Thrift Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(490, 350));
        setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon(COVER);
        welcomeLabel = new JLabel(imageIcon);
        add(welcomeLabel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
    }
}


