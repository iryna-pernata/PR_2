import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionAnalyzer {

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0) // Фільтрація тільки витрат
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10) // Повертаємо 10 найбільших витрат
                .collect(Collectors.toList());
    }

    public static List<Transaction> findExpensesInPeriod(List<Transaction> transactions, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = LocalDate.parse(t.getDate(), formatter);
                    return transactionDate.isAfter(start.minusDays(1)) && transactionDate.isBefore(end.plusDays(1));
                })
                .filter(t -> t.getAmount() < 0)
                .collect(Collectors.toList());
    }

    public static Transaction findMaxExpenseInPeriod(List<Transaction> transactions, String startDate, String endDate) {
        List<Transaction> expensesInPeriod = findExpensesInPeriod(transactions, startDate, endDate);
        return expensesInPeriod.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static Transaction findMinExpenseInPeriod(List<Transaction> transactions, String startDate, String endDate) {
        List<Transaction> expensesInPeriod = findExpensesInPeriod(transactions, startDate, endDate);
        return expensesInPeriod.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public static int countTransactionsByMonth(String monthYear, List<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        return (int) transactions.stream()
                .filter(t -> LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        .format(formatter).equals(monthYear))
                .count();
    }
}
