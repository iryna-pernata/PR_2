import java.util.List;

//package dut.pd3.practics.practic_2.FinanceAnalyzer;


public class Main {
    public static void main(String[] args) {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);

        System.out.println("Загальний баланс: " + totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(monthYear,transactions);
        System.out.println("Кількість транзакцій за " + monthYear + ": " + transactionsCount);

        TransactionReportGenerator.printBalanceReport(totalBalance);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear,transactionsCount);

        TransactionAnalyzer.findTopExpenses(transactions);
        Transaction maxExpense = TransactionAnalyzer.findMaxExpenseInPeriod(transactions, "01-01-2024", "30-01-2024");
        Transaction minExpense = TransactionAnalyzer.findMinExpenseInPeriod(transactions, "01-01-2024", "30-01-2024");
        TransactionReportGenerator.printMaxAndMinExpensesReport(maxExpense, minExpense);

        TransactionReportGenerator.generateMonthlyExpenseReport(transactions);

    }
}

//Задача: Аналіз витрат за допомогою текстових звітів
//1. Модульні тести:
//        - Напишіть тест для перевірки читання даних із CSV.
//        - Тест на визначення 10 найбільших витрат.
//        2. Функціональні розширення:
//        - Додайте функцію для визначення найбільших і найменших витрат за вказаний період.
//   - Створіть текстовий звіт, що показує сумарні витрати по категоріях та по місяцях. Використовуйте символи (наприклад, `*`), де кожен символ відповідає певній сумі грошей (наприклад, 1000 грн), для візуалізації сум витрат.
