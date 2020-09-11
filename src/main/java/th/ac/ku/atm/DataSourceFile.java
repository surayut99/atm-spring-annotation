package th.ac.ku.atm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class DataSourceFile implements DataSource {

    private String filename;

    /**
     * @param filename the name of the customer file
     */

    public DataSourceFile(@Value("${filePath}") String filename) {
        this.filename = filename;
    }

    /**
     * Reads the customer numbers and pins
     * and initializes the bank accounts.
     */
    public Map<Integer,Customer> readCustomers() {

        Map<Integer,Customer> customers = new HashMap<>();
        Scanner in;

        try {
            // using ResourceUtils to get File

            in = new Scanner(new FileReader(ResourceUtils.getFile(this.filename)));
        } catch (FileNotFoundException e) {
            System.err.println("Problem reading a customer file: "+filename);
            return customers;
        }

        while (in.hasNext()) {
            int number = in.nextInt();
            String name = in.next();
            int pin = in.nextInt();
            double currentBalance = in.nextDouble();
            Customer c = new Customer(number, name, pin, currentBalance);
            customers.put(c.getId(), c);
        }
        in.close();
        return customers;
    }
}
