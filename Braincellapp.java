import java.util.*;

class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public void displayQuestion() {
        System.out.println("\n" + questionText);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ") " + options.get(i));
        }
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer - 1 == correctOptionIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int score;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What comes next in the series: 2, 4, 8, 16, ?",
                Arrays.asList("18", "24", "32", "34"), 2));
        questions.add(new Question("Which shape has the most sides?",
                Arrays.asList("Hexagon", "Octagon", "Pentagon", "Heptagon"), 1));
        questions.add(new Question("What is 15% of 200?", Arrays.asList("25", "30", "35", "20"), 1));
        questions.add(
                new Question("Which number is the odd one out? 3, 5, 7, 9, 11", Arrays.asList("3", "5", "7", "9"), 3));
        questions.add(new Question("If A=1, B=2... What is the value of the word 'ACE'?",
                Arrays.asList("9", "10", "11", "12"), 0));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🧠 Welcome to Brain Cell - IQ Quiz Game!");

        for (Question q : questions) {
            q.displayQuestion();
            System.out.print("Enter your answer (1-4): ");
            int answer;
            try {
                answer = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Skipping question.");
                continue;
            }

            if (q.isCorrect(answer)) {
                System.out.println("✅ Correct!");
                score += 10;
            } else {
                System.out.println("❌ Wrong answer.");
            }
        }

        System.out.println("\n🎉 Quiz Completed! Your final score: " + score + "/" + (questions.size() * 10));
        scanner.close();
    }
}

public class Braincellapp {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.start();
    }
}
