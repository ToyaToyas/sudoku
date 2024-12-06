import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomeScreen extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sudoku");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800, 700));

        int buttonHeight = 100;
        int buttonWidth = 200;
        int exitButtonHeight = 50;
        int exitButtonWidth = 50;

        // Load background image
        BufferedImage bgImage = null;
        try {
            bgImage = ImageIO.read(new File("src/Assets/download1.png"));
        } catch (IOException e) {
            System.err.println("Background image not found: " + e.getMessage());
            e.printStackTrace();
        }

        // Scale and set background image
        Image scaledBgImage = bgImage.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledBgImage);
        JLabel background = new JLabel(backgroundImage);
        background.setIcon(backgroundImage);
        background.setHorizontalAlignment(JLabel.CENTER);

        // Load Sudoku button image
        BufferedImage sudokuIcon = null;
        try {
            sudokuIcon = ImageIO.read(new File("src/Assets/sudokuButton.png"));
        } catch (IOException e) {
            System.err.println("Sudoku button image not found: " + e.getMessage());
            e.printStackTrace();
        }

        // Scale and set Sudoku button
        Image sudokuImage = sudokuIcon.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledSudokuIcon = new ImageIcon(sudokuImage);
        JLabel sudokuButton = new JLabel(scaledSudokuIcon);

        // Configure exit button
        JButton exitButton = new JButton("X");
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setBorderPainted(false);
        exitButton.setForeground(Color.WHITE);

        // Add listener for Sudoku button
        sudokuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        sudoku.Main.main(new String[]{}); // Call main in sudoku.Main
                        frame.dispose(); // Close WelcomeScreen
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });

        // Add listener for resizing components
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                Dimension size = frame.getSize();
                int width = (int) size.getWidth();
                int height = (int) size.getHeight();

                background.setBounds(0, 0, width, height);
                sudokuButton.setBounds((width / 2) - (buttonWidth / 2), (height / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
                exitButton.setBounds(width / 8 - (exitButtonWidth / 2), height / 8 - (exitButtonHeight / 2), exitButtonWidth, exitButtonHeight);
            }
        });

        // Add listener for exit button
        exitButton.addActionListener(e -> System.exit(0));

        // Play background music
        LoopSound loopSound = new LoopSound("src/Assets/y2mate.com-Ost-Harvest-Moon-BTN-Town-1-Hour-Music-_v240P.wav");
        loopSound.play();

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2
        );

        frame.add(exitButton);
        frame.add(sudokuButton);
        frame.add(background);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
