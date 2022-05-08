import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AboutProgram extends JDialog {
    JTextArea textArea = new JTextArea();
    Button buttonBack = new Button("Вернуться назад");

    JLabel frustumPicture = new JLabel(new javax.swing.ImageIcon(getClass().getResource("./assets/defaultPrisma.png")));

    AboutProgram() {
        setTitle("О программе");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textArea.setEditable(false);

        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setText("Эта программа предназначена как курсовой проект  \"ВГТУ\" группы ИТС-10 \n" +
                "для расчета свойств призмы.\nПрограмма создана в бесплатной версии \n" +
                "IntelliJ IDEA Community Edition 2022.1.\n" +
                "И используется исключительно в ознокомительных целях, автор \n" +
                "не несет никакой отвественности за неверные полученные результаты расчета.\n" +
                "Программа не предназначена для коммерческого использования. \n \n " +
                "Для рассчета свойств были использованы формулы с сайта geleot \n " +

                "Можно произвести расчёт, удовлетворяя одному из следующих условий:\n\n"
                + "1) Зная: РЕБРА ТРЕУГОЛЬНОЙ ПРИЗМЫ.\n"
                + "2) Зная: РЕБРО И ВЫСОТУ ТРЕУГОЛЬНОЙ ПРИЗМЫ.\n"
                + "3) Зная: ДИАГОНАЛЬ И РЕБРО \"A\" ТРЕУГОЛЬНОЙ ПРИЗМЫ.\n"
                + "4) Зная: ДИАГОНАЛЬ И РЕБРО \"Б\" ТРЕУГОЛЬНОЙ ПРИЗМЫ.\n"
                + "5) Зная: РАДИУС ОПИСАННОЙ СФЕРЫ И РЕБРО \"A\" ТРЕУГОЛЬНОЙ ПРИЗМЫ"
);

        JPanel panelButtonBack = new JPanel(new GridLayout());
        buttonBack.setBackground(Color.cyan);
        panelButtonBack.add(buttonBack);

        String projectDirectory = System.getProperty("user.dir");

        try {
            File frustumFile = new File(projectDirectory + "\\src\\assets\\image.png");
            BufferedImage frustumPicture = ImageIO.read(frustumFile);
            JLabel picLabel = new JLabel(new ImageIcon(frustumPicture));
            textArea.add(picLabel);
        } catch(IOException error) {
            System.out.println(error.getMessage());
        }

        setLayout(new BorderLayout());
        add(textArea, BorderLayout.CENTER);
        add(panelButtonBack, BorderLayout.SOUTH);
        add(frustumPicture, BorderLayout.WEST);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setModal(true);
        setVisible(true);
    }
}

