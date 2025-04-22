import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PayrollCalculator {
    private static Map<String, Object> entry;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the file to copy the data from:");
        String inputFile = scanner.nextLine();

        System.out.println("Enter the name of the file you want to creat:");
        String outputFile = scanner.nextLine();

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null){
                String[] tokens = line.split("\\|");

                int employeeId = Integer.parseInt(tokens[0]);
                String name = tokens [1];
                double hoursworked = Double.parseDouble(tokens[2]);
                double payRate = Double.parseDouble(tokens[3]);

                Employee emp = new Employee(employeeId, name, hoursworked, payRate);
                employees.add(emp);

            }
        }catch (IOException e){
            System.out.println("error reading:" + e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("error number:" + e.getMessage());
        }
        if (outputFile.endsWith(".json")) {
            writeJson(employees, outputFile);
        }else {
            writeCsv(employees, outputFile);
        }
        System.out.println("Payroll saved at:" + new File(outputFile).getAbsolutePath());
    }
    private static void writeCsv(List<Employee> employees, String outputFile){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
            writer.write("id|name|gross pay");
            writer.newLine();

            for (Employee emp : employees){
                String line = String.format("%d|%s|%.2f", emp.getEmployeeId(), emp.getName(), emp.getGrossPay());
                writer.write(line);
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println("error writing CSV:" + e.getMessage());
        }
    }
    private static void writeJson(List<Employee> employees, String outputFile){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){

            for (Employee emp : employees) {
                List<Map<String, Object>> payrollList = new ArrayList<>();
                entry.put("id", emp.getEmployeeId());
                entry.put("name", emp.getName());
                entry.put("grossPay", emp.getGrossPay());
                payrollList.add(entry);
            }

        }catch (IOException e){
            System.out.println("error writing Json:" + e.getMessage());
        }
    }
}
