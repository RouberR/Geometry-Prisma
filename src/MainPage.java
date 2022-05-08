import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPage {
    int WIDTH_PAGE = 530, HEIGHT_PAGE = 400;
    static Frame startFrame = new Frame("Геометрический калькулятор прризмы");

    JLabel titleLabel = new JLabel("Добро пожаловать", JLabel.CENTER);
    Font titleFont = new Font("Arial", Font.BOLD, 24);


    JLabel subTitleLabel = new JLabel("Это геометрический калькулятор призмы");
    Font subTitleFont = new Font("Arial", Font.BOLD, 16);
    String projectDirectory = System.getProperty("user.dir");
    JLabel image;

    JButton button = new JButton("Войти в калькулятор :)");


    MainPage() {
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLUE);


        subTitleLabel.setFont(subTitleFont);
        subTitleLabel.setForeground(Color.CYAN);
        startFrame.setBackground(new Color(174, 213, 194));
        startFrame.add(titleLabel);
        startFrame.add(subTitleLabel);

        startFrame.setLayout(new FlowLayout());
        startFrame.setVisible(true);
        startFrame.setResizable(false);

        addImage();
        button.setFont(new java.awt.Font("Calibri", 3, 20 ));
        button.setForeground(new java.awt.Color(190, 126, 229));
        startFrame.add(button);

        ActionListener enterButtonListener = new EnterListener();
        button.addActionListener(enterButtonListener);

        startFrame.setSize(WIDTH_PAGE, HEIGHT_PAGE);

        startFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    private void addImage() {
        try {
            File fileImage = new File(projectDirectory + "\\src\\assets\\defaultPrisma.png");
            BufferedImage frustumPicture = ImageIO.read(fileImage);
            image = new JLabel(new ImageIcon(frustumPicture));
            startFrame.add(image);
        } catch(IOException error) {
            System.out.println(error.getMessage());
        }
    }

    private static class EnterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            startFrame.setVisible(false);
            new Calculator();
        }
    }

    public static void main(String[] args) {
        MainPage mainPage = new MainPage();
    }
}
