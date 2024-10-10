import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TransactionReportGenerator {

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }
    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printMaxAndMinExpensesReport(Transaction maxExpense, Transaction minExpense) {
        if (maxExpense != null) {
            System.out.println("Найбільша витрата: " + maxExpense.getDescription() + " - " + maxExpense.getAmount() + " грн");
        } else {
            System.out.println("Немає витрат за цей період.");
        }

        if (minExpense != null) {
            System.out.println("Найменша витрата: " + minExpense.getDescription() + " - " + minExpense.getAmount() + " грн");
        } else {
            System.out.println("Немає витрат за цей період.");
        }
    }



    public static void generateMonthlyExpenseReport(List<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        Map<String, Double> monthlyExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        t -> LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).format(formatter),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        System.out.println("Звіт по місяцях:");
        for (Map.Entry<String, Double> entry : monthlyExpenses.entrySet()) {
            String monthYear = entry.getKey();
            double totalExpense = Math.abs(entry.getValue());
            System.out.print(monthYear + ": ");
            int stars = (int) (totalExpense / 1000);
            for (int i = 0; i < stars; i++) {
                System.out.print("*");
            }
            System.out.println(" (" + totalExpense + " грн)");
        }
    }

    public static void generateCategoryExpenseReport(List<Transaction> transactions) {
        Map<String, Double> categoryExpenses = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.groupingBy(
                        Transaction::getDescription,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        System.out.println("Звіт по категоріях:");
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            String category = entry.getKey();
            double totalExpense = Math.abs(entry.getValue());
            System.out.println(category + ": " + totalExpense + " грн");
        }
    }

}
