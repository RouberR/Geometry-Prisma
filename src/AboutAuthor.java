import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class AboutAuthor extends JDialog {
    JTextArea text = new JTextArea(1, 10);
    Button buttonBack = new Button("Вернуться назад");


    AboutAuthor() {
        setTitle("Об авторе");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        text.setEditable(false);

        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setText("Группа ИТС-10\n"
                + "Вариант 25\n"
                + "Федосенко Евгений Викторович");

        setLayout(new BorderLayout());
        buttonBack.setForeground(new java.awt.Color(190, 126, 229));
        buttonBack.setBackground(new Color(56, 225, 196));
        add(buttonBack, BorderLayout.SOUTH);
        add(text, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        setModal(true);
    }
}

