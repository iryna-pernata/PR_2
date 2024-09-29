import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

class TransactionCSVReaderTest {
    @Test
    public void testReadTransactions() {
        String filePath = "https://informer.com.ua/dut/java/pr2.csv";


        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);


        Assertions.assertTrue(transactions.size() > 0, "Не вдалося завантажити CSV.");
    }
}
