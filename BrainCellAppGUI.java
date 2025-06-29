import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a single quiz question
class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrect(int userAnswerIndex) {
        return userAnswerIndex == correctOptionIndex;
    }
}

// Manages the quiz logic and UI
class QuizGUI extends JFrame implements ActionListener {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    private JLabel questionLabel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private JButton nextButton;
    private JPanel quizPanel;

    public QuizGUI() {
        // JFrame setup
        setTitle("🧠 Brain Cell - IQ Quiz Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        questions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;
        loadQuestions();
        initComponents();
        displayQuestion();
    }

    private void loadQuestions() {
        questions.add(new Question("What comes next in the series: 2, 4, 8, 16, ?", Arrays.asList("18", "24", "32", "34"), 2));
        questions.add(new Question("Which shape has the most sides?", Arrays.asList("Hexagon", "Octagon", "Pentagon", "Heptagon"), 1));
        questions.add(new Question("What is 15% of 200?", Arrays.asList("25", "30", "35", "20"), 1));
        questions.add(new Question("Which number is the odd one out? 3, 5, 7, 9, 11", Arrays.asList("3", "5", "7", "9"), 3));
        questions.add(new Question("If A=1, B=2... What is the value of the word 'ACE'?", Arrays.asList("9", "10", "11", "12"), 0));
    }

    private void initComponents() {
        quizPanel = new JPanel();
        quizPanel.setLayout(new BorderLayout(10, 10));
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        quizPanel.setBackground(new Color(255, 240, 245)); // Light Pink Background

        // Question Label
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setForeground(new Color(139, 0, 0)); // Dark Red Text
        quizPanel.add(questionLabel, BorderLayout.NORTH);

        // Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBackground(new Color(255, 240, 245)); // Light Pink Background
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].setBackground(new Color(255, 240, 245)); // Light Pink Background
            optionButtons[i].setForeground(new Color(139, 0, 0)); // Dark Red Text
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        quizPanel.add(optionsPanel, BorderLayout.CENTER);

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBackground(new Color(255, 182, 193)); // Pink Background
        nextButton.setForeground(Color.WHITE); // White Text
        nextButton.addActionListener(this);
        quizPanel.add(nextButton, BorderLayout.SOUTH);

        add(quizPanel);
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestionText());

            List<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setSelected(false); // Clear previous selection
            }
            optionsGroup.clearSelection(); // Ensure no radio button is selected by default
        } else {
            showResults();
        }
    }

    private void showResults() {
        quizPanel.removeAll();
        quizPanel.setLayout(new BorderLayout());
        quizPanel.setBackground(new Color(255, 240, 245)); // Light Pink Background

        JLabel resultLabel = new JLabel("🎉 Quiz Completed! Your final score: " + score + "/" + (questions.size() * 10));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(139, 0, 0)); // Dark Red Text
        quizPanel.add(resultLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(new Color(255, 182, 193)); // Pink Background
        exitButton.setForeground(Color.WHITE); // White Text
        exitButton.addActionListener(e -> System.exit(0)); // Exit on button click
        JPanel buttonPanel = new JPanel();
         buttonPanel.setBackground(new Color(255, 240, 245)); // Light Pink Background
        buttonPanel.add(exitButton);
        quizPanel.add(buttonPanel, BorderLayout.SOUTH);

        quizPanel.revalidate();
        quizPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            int selectedOptionIndex = -1;
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].isSelected()) {
                    selectedOptionIndex = i;
                    break;
                }
            }

            if (selectedOptionIndex != -1) {
                if (questions.get(currentQuestionIndex).isCorrect(selectedOptionIndex)) {
                    score += 10;
                }
                currentQuestionIndex++;
                displayQuestion();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an answer!", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}

public class BrainCellAppGUI {
    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            new QuizGUI().setVisible(true);
});
}
}