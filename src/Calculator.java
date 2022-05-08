import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Calculator {
    final int WIDTH_MAIN_PAGE = 900, HEIGHT_MAIN_PAGE = 700;

    final String TITLE_PAGE = "Геометрический калькулятор. Призма";
    final int DEFAULT_SIZE_TEXT_FIELD = 9;
    Frame mainFrame;

    Button aboutProgramButton, aboutAuthorButton, calculateButton, clearButton, exitButton;
    static Panel picturePanel, calculatePanel, buttonPanel;
    static JLabel resultCalculateFields = new JLabel("");

    String[] optionsToChoose = {
            "Не выбранно",
            "1) Дано: Ребра треугольной призмы",
            "2) Дано: Ребро и высоту треугольной призмы",
            "3) Дано: Диагональ и ребро \"А\" треугольной призмы",
            "4) Дано: Диагональ и ребро \"B\" треугольной призмы",
            "5) Дано: Радиус описанной сферы и ребро \"А\" треугольной призмы"
    };
    static JComboBox<String> operationsBox;
    String projectDirectory = System.getProperty("user.dir");
    Color BACKGROUND_COLOR = new java.awt.Color(248, 175, 152);
    Color BACKGROUND_PANEL_COLOR = new java.awt.Color(217, 191, 171);
    JLabel pictureLabel;
    static JTextField firstTextField, secondTextField;
    static JLabel calculateResult = new JLabel("");
    static int currentOperationIndex;
    Calculator(){
        mainFrame = new Frame(TITLE_PAGE);
        mainFrame.setResizable(true);
        mainFrame.setSize(WIDTH_MAIN_PAGE, HEIGHT_MAIN_PAGE);

        mainFrame.setLayout(new GridLayout(2,1));

        calculatePanel = new Panel();

        picturePanel = new Panel();
        buttonPanel = new Panel(new GridLayout(5,1));


        mainFrame.setBackground(BACKGROUND_COLOR);
        picturePanel.setBackground(BACKGROUND_PANEL_COLOR);

        addMainPropertiesToPicturePanel(picturePanel);
        addFrustumImageToPicturePanel("\\src\\assets\\defaultPrisma.png", picturePanel);

        createCalculate(calculatePanel);


        calculateButton = new Button("Вычислить");
        calculateButton.setBackground(new java.awt.Color(30, 241, 17));

        clearButton = new Button("Отчистить форму");
        clearButton.setBackground(new java.awt.Color(99, 110, 79));

        aboutAuthorButton = new Button("Об авторе");
        aboutAuthorButton.setBackground(new java.awt.Color(241, 161, 13));

        aboutProgramButton = new Button("О программе");
        aboutProgramButton.setBackground(new java.awt.Color(239, 236, 12));

        exitButton = new Button("Выйти");
        exitButton.setBackground(new java.awt.Color(246, 82, 82));

        Button[] buttons = {calculateButton, clearButton, aboutAuthorButton, aboutProgramButton, exitButton};

        ActionListener calculateButtonListener = new CalculateListener();
        ActionListener clearButtonListener = new ClearListener();
        ActionListener aboutAuthorButtonListener = new AboutAuthorListener();
        ActionListener aboutProgramButtonListener = new AboutProgramListener();
        ActionListener exitButtonListener = new ExitListener();
        ActionListener[] buttonListeners = {
                calculateButtonListener,
                clearButtonListener,
                aboutAuthorButtonListener,
                aboutProgramButtonListener,
                exitButtonListener
        };

        createButtonsForButtonPanel(buttons, buttonListeners, buttonPanel);

       Panel[] panels = {picturePanel, calculatePanel, buttonPanel };
       addPanelsToFrame(mainFrame, panels);


        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }

   private void addPanelsToFrame(Frame baseFrame, Panel[] panels) {
      for (Panel panel : panels) {
           baseFrame.add(panel, "span");
       }
   }

    private void addFrustumImageToPicturePanel(String localPathToFile, Panel currentPanel) {
        try {
            File frustumFile = new File(projectDirectory + localPathToFile);
            BufferedImage frustumPicture = ImageIO.read(frustumFile);
            pictureLabel = new JLabel(new ImageIcon(frustumPicture));
            currentPanel.add(pictureLabel);
        } catch(IOException error) {
            System.out.println(error.getMessage());
        }
    }

    private void changeImageFromPicturePanel(String localPathToFile, Panel currentPanel) {
        currentPanel.remove(pictureLabel);
        addFrustumImageToPicturePanel(localPathToFile, currentPanel);
        currentPanel.repaint();
    }

    private void addMainPropertiesToPicturePanel(Panel currentPanel) {
        String[] properties = {
                "Свойства:",
                "a, b - ребра",
                "d - диагональ",
                "h - высота",
                "P - периметр",
                "V - объем",
                "S - площадь",
                "r - радиус вписанной сферы",
                "R - радиус описанной сферы",
                "<br/>",
        };
        String propertiesWithSpace = String.join("<br/>", properties);
        JLabel propertiesLabel = new JLabel("<html>" + propertiesWithSpace + "</html>");
        currentPanel.add(propertiesLabel);
    }

    private void createButtonsForButtonPanel(Button[] buttons, ActionListener[] listeners, Panel currentPanel) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(listeners[i]);
            currentPanel.add(buttons[i]);
        }
    }

    private void createCalculate(Panel currentPanel) {
        JLabel titleLabel = new JLabel("Вычислить свойства призмы, зная:");
        currentPanel.add(titleLabel);

        operationsBox = new JComboBox<>(optionsToChoose);
        currentPanel.add(operationsBox);

        JLabel firstLabel = new JLabel();
        JLabel secondLabel = new JLabel();

        firstTextField = new JTextField(DEFAULT_SIZE_TEXT_FIELD);
        secondTextField = new JTextField(DEFAULT_SIZE_TEXT_FIELD);


        firstTextField.setVisible(false);
        secondTextField.setVisible(false);


        firstTextField.setBorder(BorderFactory.createLineBorder(Color.black));
        secondTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        currentPanel.add(firstLabel);
        currentPanel.add(firstTextField);
        currentPanel.add(secondLabel);
        currentPanel.add(secondTextField);

        currentPanel.add(calculateResult);

        operationsBox.addActionListener(event -> {
            currentOperationIndex = operationsBox.getSelectedIndex();

            JTextField[] textFields = {firstTextField, secondTextField};
            JLabel[] labels = {firstLabel, secondLabel};
            setVisibleLabelAndTextFieldDefault(textFields, labels, true);
            clearTextFields(textFields);
            calculateResult.setText("");
            resultCalculateFields.setText("");
            calculatePanel.repaint();

            switch (currentOperationIndex) {
                case 0 -> {
                    setVisibleLabelAndTextFieldDefault(textFields, labels, false);
                    changeImageFromPicturePanel(
                            "\\src\\assets\\defaultPrisma.png",
                            picturePanel
                    );
                }
                case 1 -> {
                    firstLabel.setText("Ребро треугольной призмы a");
                    secondLabel.setText("Ребро треугольной призмы b");
                    changeImageFromPicturePanel(
                            "\\src\\assets\\edgeHeightTiangularPrism.png",
                            picturePanel
                    );
                }
                case 2 -> {
                    firstLabel.setText("Ребро треугольной призмы b");
                    secondLabel.setText("Высота треугольной призмы h");
                    changeImageFromPicturePanel(
                            "\\src\\assets\\edgeRadiusHeightFrustum.png",
                            picturePanel
                    );
                }
                case 3 -> {
                    firstLabel.setText("Диагональ треугольной призмы d");
                    secondLabel.setText("Ребро треугольной призмы a");
                    changeImageFromPicturePanel(
                            "\\src\\assets\\diagonalAndEdgeTriangularPrismA.png",
                            picturePanel
                    );
                }
                case 4 -> {
                    firstLabel.setText("Диагональ треугольной призмы d");
                    secondLabel.setText("Ребро треугольной призмы b");
                    changeImageFromPicturePanel(
                            "\\src\\assets\\diagonalAndEdgeTriangularPrismB.png",
                            picturePanel
                    );
                }
                case 5 -> {
                    firstLabel.setText("Радиус описанной сферы R");
                    secondLabel.setText("Ребро треугольной призмы a");
                    changeImageFromPicturePanel(
                            "\\src\\assets\\radiusCircumscribedSphereAndEdgeTriangularPrismA.png",
                            picturePanel
                    );
                }
            }
        });
    }

    private void clearTextFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void setVisibleLabelAndTextFieldDefault(JTextField[] textFields, JLabel[] labels, boolean isVisible) {
        for (JTextField textField : textFields) {
            textField.setVisible(isVisible);
        }
        for (JLabel label : labels) {
            label.setVisible(isVisible);
        }
    }

    private static class AboutProgramListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new AboutProgram();
        }
    }

    private static class AboutAuthorListener implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            new AboutAuthor();
        }
    }

    private static class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            firstTextField.setText("");
            secondTextField.setText("");
            calculateResult.setText("");
            resultCalculateFields.setText("");
            operationsBox.setSelectedIndex(0);
        }
    }

    private static class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    private static class CalculateListener implements ActionListener {
        int firstValue, secondValue;

        CalculateListener() {
            JTextField[] fields = {firstTextField, secondTextField};
            parseTextFields(fields);

            boolean isIncorrectFields = checkToCorrectTextFields();
            if (!isIncorrectFields) return;

            calculateResult.setText("Результат вычислений:");

            inferCalculation();
        }

        private void inferCalculation() {
            switch (currentOperationIndex) {
                case 1 -> edgesTriangularPrism();
                case 2 -> edgeRadiusHeightFrustum();
                case 3 -> diagonalAndEdgeTriangularPrismA();
                case 4 -> diagonalAndEdgeTriangularPrismB();
                case 5 -> radiusCircumscribedSphereAndEdgeTriangularPrismA();
            }
        }
        private void edgesTriangularPrism() {
            double baseSideA = firstValue; // a
            double baseSideB = secondValue; // b
            double height = baseSideA / Math.sqrt(2); //высота
            double inscribedRadius = baseSideA / (2 * Math.sqrt(2)); //вписанный радиус
            double circumradius = baseSideA / Math.sqrt(3); // описанный радиус
            double diagonal = Math.sqrt(Math.pow(baseSideA, 2) + Math.pow(baseSideB, 2)); // диагональ
            double perimeter = 3 * (2*baseSideA + baseSideB); // периметр
            double triangleBaseArea = (Math.sqrt(3) * Math.pow(baseSideA, 2) / 4); //площадь основания треугольника
            double sideSurfaceArea = 3 * baseSideA * baseSideB; //площадь боковой поверхности
            double totalSurfaceArea = 3 * baseSideA * baseSideB +(Math.sqrt(3) * Math.pow(baseSideA, 2) / 2); //площадь общей поверхности
            double volume = triangleBaseArea * baseSideB; // объем
            double circumscribedSphereRadius = Math.sqrt(5) / Math.sqrt(6) * baseSideA; //радиус описанной сферы
            double inscribedSphereRadius = (height / 2) == inscribedRadius ? inscribedRadius : 0; //радиус вписанный сферы
            formattingCalculationResult(
                    baseSideA,
                    baseSideB,
                    height,
                    circumradius,
                    inscribedRadius,
                    diagonal,
                    perimeter,
                    triangleBaseArea,
                    sideSurfaceArea,
                    totalSurfaceArea,
                    volume,
                    circumscribedSphereRadius,
                    inscribedSphereRadius
            );
        }

        private void edgeRadiusHeightFrustum() {
            double height = secondValue; //h
            double baseSideB = firstValue; // ребро б
            double baseSideA = height * Math.sqrt(2); // ребро основания a
            double triangleBaseArea = (Math.sqrt(3) * Math.pow(height, 2) / 2); //площадь основания треугольника S_(осн.)
            double inscribedRadius = height / (Math.sqrt(6)); //вписанный радиус r
            double circumradius = (baseSideA * Math.sqrt(2))/Math.sqrt(3); // описанный радиус R
            double perimeter = 3 * (2*baseSideA + baseSideB); // периметр P
            double sideSurfaceArea = 3 * baseSideA * baseSideB; //площадь боковой поверхности S_(б.п.)
            double totalSurfaceArea = 3 * baseSideA * baseSideB +(Math.sqrt(3) * Math.pow(baseSideA, 2) / 2); //S_(п.п.) площадь общей поверхности
            double volume = triangleBaseArea * baseSideB; // объем V
            double diagonal = Math.sqrt(Math.pow(baseSideA, 2) + Math.pow(baseSideB, 2)); // диагональ d
            double circumscribedSphereRadius = Math.sqrt(5) / Math.sqrt(6) * height; //радиус описанной сферы R
            double inscribedSphereRadius = inscribedRadius;
            formattingCalculationResult(
                    baseSideA,
                    baseSideB,
                    height,
                    circumradius,
                    inscribedRadius,
                    diagonal,
                    perimeter,
                    triangleBaseArea,
                    sideSurfaceArea,
                    totalSurfaceArea,
                    volume,
                    circumscribedSphereRadius,
                    inscribedSphereRadius
            );
        }
        private void diagonalAndEdgeTriangularPrismA() {
            if (secondValue >= firstValue) {
                calculateResult.setText("Призма не существует");
                resultCalculateFields.setText("");
                return;
            }
            double baseSideA = secondValue; // ребро основания a
            double diagonal = firstValue; // диагональ d

            double height = baseSideA/Math.sqrt(2); //h
            double inscribedRadius = baseSideA / (2 * Math.sqrt(3)); //вписанный радиус r
            double circumradius = baseSideA * Math.sqrt(3); // описанный радиус R
            double triangleBaseArea = (Math.sqrt(3) * Math.pow(baseSideA, 2) / 4); //площадь основания треугольника S_(осн.)
            double baseSideB = Math.sqrt(Math.pow(diagonal, 2) - Math.pow(baseSideA, 2));
            double perimeter = 3 * (2*baseSideA + baseSideB); // периметр P
            double sideSurfaceArea = 3 * baseSideA * baseSideB; //площадь боковой поверхности S_(б.п.)
            double totalSurfaceArea = 3 * baseSideA * (Math.sqrt(Math.pow(diagonal, 2) - Math.pow(baseSideA, 2))) +
                   ( Math.sqrt(3) *  Math.pow(baseSideA, 2)) / 4; //S_(п.п.) площадь общей поверхности
            double volume = (Math.sqrt(3 * (Math.pow(diagonal, 2) - Math.pow(baseSideA, 2))) * Math.pow(baseSideA, 2)) / 4; // объем V
            double circumscribedSphereRadius = Math.sqrt(5/6) * baseSideA; //радиус описанной сферы R
            double inscribedSphereRadius = inscribedRadius;
            formattingCalculationResult(
                    baseSideA,
                    baseSideB,
                    height,
                    circumradius,
                    inscribedRadius,
                    diagonal,
                    perimeter,
                    triangleBaseArea,
                    sideSurfaceArea,
                    totalSurfaceArea,
                    volume,
                    circumscribedSphereRadius,
                    inscribedSphereRadius
            );
        }

        private void diagonalAndEdgeTriangularPrismB() {
            double baseSideB = secondValue;
            double diagonal = firstValue; // диагональ d
            double baseSideA = Math.sqrt(Math.pow(diagonal, 2) - Math.pow(baseSideB, 2));
            double height = baseSideA/Math.sqrt(2); //h
            double inscribedRadius = baseSideA / (2 * Math.sqrt(3)); //вписанный радиус r
            double circumradius = baseSideA * Math.sqrt(3); // описанный радиус R
            double triangleBaseArea = (Math.sqrt(3) * Math.pow(baseSideA, 2) / 4); //площадь основания треугольника S_(осн.)
            double perimeter = 3 * (2*baseSideA + baseSideB); // периметр P
            double sideSurfaceArea = 3 * baseSideA * baseSideB; //площадь боковой поверхности S_(б.п.)
            double totalSurfaceArea = 3 * baseSideA * (Math.sqrt(Math.pow(diagonal, 2) - Math.pow(baseSideA, 2))) +
                    ( Math.sqrt(3) *  Math.pow(baseSideA, 2)) / 4; //S_(п.п.) площадь общей поверхности
            double volume = (Math.sqrt(3 * (Math.pow(diagonal, 2) - Math.pow(baseSideA, 2))) * Math.pow(baseSideA, 2)) / 4; // объем V
            double circumscribedSphereRadius = Math.sqrt(5/6) * baseSideA; //радиус описанной сферы R
            double inscribedSphereRadius = inscribedRadius;
            formattingCalculationResult(
                    baseSideA,
                    baseSideB,
                    height,
                    circumradius,
                    inscribedRadius,
                    diagonal,
                    perimeter,
                    triangleBaseArea,
                    sideSurfaceArea,
                    totalSurfaceArea,
                    volume,
                    circumscribedSphereRadius,
                    inscribedSphereRadius
            );
        }

        private void radiusCircumscribedSphereAndEdgeTriangularPrismA() {
            double circumscribedSphereRadius = firstValue; //радиус описанной сферы R
            double baseSideA = secondValue; // ребро основания a
            double height = baseSideA * Math.sqrt(2); //h
            double inscribedRadius = baseSideA / (2 * Math.sqrt(6)); //вписанный радиус r
            double circumradius = baseSideA * Math.sqrt(3); // описанный радиус R
            double triangleBaseArea = (Math.sqrt(3) * Math.pow(baseSideA, 2) / 4); //площадь основания треугольника S_(осн.)
            double baseSideB = Math.sqrt(6/5) * circumscribedSphereRadius; // ребро б
            double diagonal = Math.sqrt(Math.pow(baseSideA, 2) + Math.pow(baseSideB, 2)); // диагональ d
            double perimeter = 3 * (2*baseSideA + baseSideB); // периметр P
            double sideSurfaceArea = 3 * baseSideA * baseSideB; //площадь боковой поверхности S_(б.п.)
            double totalSurfaceArea = 3 * baseSideB * (Math.sqrt(6/5)) * circumscribedSphereRadius +
                    (3 * Math.sqrt(3) * Math.pow(circumscribedSphereRadius, 2)  ) / 5 ; //S_(п.п.) площадь общей поверхности
            double volume = triangleBaseArea * baseSideB; // объем V
            double inscribedSphereRadius = 0;
            formattingCalculationResult(
                    baseSideA,
                    baseSideB,
                    height,
                    circumradius,
                    inscribedRadius,
                    diagonal,
                    perimeter,
                    triangleBaseArea,
                    sideSurfaceArea,
                    totalSurfaceArea,
                    volume,
                    circumscribedSphereRadius,
                    inscribedSphereRadius
            );
        }
        private void formattingCalculationResult(
                double baseSideA,
                double baseSideB,
                double height,
                double circumradius,
                double inscribedRadius,
                double diagonal,
                double perimeter,
                double triangleBaseArea,
                double sideSurfaceArea,
                double totalSurfaceArea,
                double volume,
                double circumscribedSphereRadius,
                double inscribedSphereRadius
        ) {
            DecimalFormat decimalFormat = new DecimalFormat("###.###");

            String[] resultFields = {
                    "Ребро призмы a: "+ decimalFormat.format(baseSideA),
                    "Ребро призмы b: "+ decimalFormat.format(baseSideB),
                    "Высота призмы h: " + decimalFormat.format(height),
                    "Описанный радиус R: " + decimalFormat.format(circumradius),
                    "Вписанный радиус r: " + decimalFormat.format(inscribedRadius),
                    "Диагональ d: " + decimalFormat.format(diagonal),
                    "Периметр P: " + decimalFormat.format(perimeter),
                    "Площадь основания треугольника S_(осн.): " + decimalFormat.format(triangleBaseArea),
                    "Площадь боковой поверхности S_(б.п.): " + decimalFormat.format(sideSurfaceArea),
                    "Площадь общей поверхности S_(п.п.): " + decimalFormat.format(totalSurfaceArea),
                    "Объем V: " + decimalFormat.format(volume),
                    "Радиус описанной сферы R_1: " + decimalFormat.format(circumscribedSphereRadius),
                    "Радиус вписанной сферы: r_1: " + decimalFormat.format(inscribedSphereRadius),
            };
            String resultFieldsWithSpace = String.join("<br/>", resultFields);
            resultCalculateFields.setText("<html>" + resultFieldsWithSpace + "</html>");
            calculatePanel.add(resultCalculateFields);
        }
        private boolean checkToCorrectTextFields() {
            return firstValue != 0 && secondValue != 0;
        }
        private void parseTextFields(JTextField[] fields) {
            try {
                firstValue = Integer.parseInt(fields[0].getText());
                secondValue = Integer.parseInt(fields[1].getText());
            } catch(NumberFormatException error) {
                calculateResult.setText("Введите корректные данные.");
                resultCalculateFields.setText("");
                System.out.println(error.getMessage());
            }
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            new CalculateListener();
        }
    }

}