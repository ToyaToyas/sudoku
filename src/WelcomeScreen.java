import sudoku.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

public class WelcomeScreen extends JFrame {

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Sudoku");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(800,700));

        int buttonHeight = 100;
        int buttonWidth = 200;
        int exitButtonHeight = 50;
        int exitButtonWidth = 50;

        BufferedImage bgImage = ImageIO.read(new File("Assets/download1.png"));
        Image scaledBgImage = bgImage.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon backgroundImage = new ImageIcon(scaledBgImage);
        JLabel background = new JLabel(backgroundImage);
        background.setIcon(backgroundImage);
        background.setHorizontalAlignment(JLabel.CENTER);

        BufferedImage sudokuIcon = ImageIO.read(new File("Assets/sudokuButton.png"));
        Image sudokuImage = sudokuIcon.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledSudokuIcon = new ImageIcon(sudokuImage);

        JButton exitButton = new JButton("X");


        JLabel sudokuButton = new JLabel(scaledSudokuIcon);

        exitButton.setBackground(new Color(220,53,69));
        exitButton.setFont(new Font("Arial",Font.PLAIN,20));
        exitButton.setBorderPainted(false);
        exitButton.setForeground(Color.WHITE);
        sudokuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        sudoku.Main.main(new String[]{}); // Memanggil metode main di kelas Main
                        frame.dispose(); // Menutup WelcomeScreen
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });


        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                Dimension size = frame.getSize();
                int width = (int) size.getWidth();
                int height = (int) size.getHeight();

                background.setBounds(0, 0, width, height);
                sudokuButton.setBounds((width / 2) - (buttonWidth / 2), (height / 2) - (buttonHeight / 2), buttonWidth, buttonHeight);
                exitButton.setBounds(width / 8 - (exitButtonWidth /2 ), (height / 8) - (exitButtonHeight /2 ), exitButtonWidth,exitButtonHeight);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        LoopSound loopSound = new LoopSound("Assets/y2mate.com-Ost-Harvest-Moon-BTN-Town-1-Hour-Music-_v240P.wav");
        loopSound.play();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight()) / 2);

//        frame.add(textCopyright);
        frame.add(exitButton);
        frame.add(sudokuButton);
        frame.add(background);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}