import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;

public class PayrollCalculator {
    public static void main(String[] args) {
        String FilePath = "employees.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePath))){
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null){
                String[] tokens = line.split("\\|");

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens [1];
                double hoursworked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee employee = new Employee(employeeId, name, hoursworked, payRate);

                System.out.printf("ID: %d | Name: %s | Gross Pay: $%.2f%n" , employee.getEmployeeId(), employee.getName(), employee.getGrossPay());

            }
        }catch (IOException e){
            System.out.println("error reading:" + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("error number:" + e.getMessage());
        }
    }
}
