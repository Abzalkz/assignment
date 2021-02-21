import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Connection con() {
        String url = "jdbc:mysql://localhost:3306/myCompany";
        String username = "root";
        String pass = "";

        try {
            Connection con = DriverManager.getConnection(url, username, pass);
            if (con == null) {
                System.out.println("No Connection!!!");
            }
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addEmp(Employees emp) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "INSERT INTO employees (first_name, last_name, address, salary, emp_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, emp.getFname());
            statement.setString(2, emp.getLname());
            statement.setString(3, emp.getAddress());
            statement.setInt(4, emp.getSalary());
            statement.setInt(5, emp.getId());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee " + emp.getName() + " with id: " + emp.getId() + " inserted!");
            }
        }
    }

    public static void selectEmp() throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "SELECT * FROM employees";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.isBeforeFirst()) {
                System.out.println("No Employees in Database!");
            } else {
                while (resultSet.next()) {
                    int emp_id = resultSet.getInt("emp_id");
                    String emp_name = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                    int salary = resultSet.getInt("salary");
                    System.out.println("ID:" + emp_id + " - " + emp_name + "; Salary: " + salary);
                }
            }
        }
    }

    public static Employees selectEmpById(int emp_id) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            Employees empl;
            String sql = "SELECT * FROM employees WHERE emp_id=?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, emp_id);
            ResultSet resultSet = pStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("Empty Database!");
                return null;
            } else {
                while (resultSet.next()) {
                    int e_id = resultSet.getInt("emp_id");
                    String e_first_name = resultSet.getString("first_name");
                    String e_last_name = resultSet.getString("last_name");
                    String e_address = resultSet.getString("address");
                    int e_salary = resultSet.getInt("salary");
                    empl = new Employees(e_id, e_first_name, e_last_name, e_salary, e_address);
                    return empl;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public static void createProject(Project proj) throws SQLException {
        Connection conn = con();
        if (conn != null) {
            String sql = "INSERT INTO projects (proj_id, proj_name, deadline, total_price) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, proj.getProj_id());
            statement.setString(2, proj.getProj_name());
            statement.setInt(3, proj.getDeadline());
            statement.setInt(4, proj.getTotalPrice());

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println(proj.getProj_name() + " added!");

                for (int i = 0; i < proj.getEmployees().size(); i++) {
                    String sql_inner = "INSERT INTO project_employees (proj_id, emp_id) VALUES (?, ?)";
                    PreparedStatement statement_inner = conn.prepareStatement(sql_inner);
                    statement_inner.setInt(1, proj.getProj_id());
                    statement_inner.setInt(2, proj.getEmployees().get(i).getId());

                    int rows_inner = statement_inner.executeUpdate();

                    if (rows_inner > 0) {
                        System.out.println(proj.getEmployees().get(i).getName() + " hired");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("1: Add Employee\n2: Make Project\n3 - Close");

            int choice = scan.nextInt();

            if (choice == 1) {
                System.out.println("Employees:");
                selectEmp();

                System.out.println();

                System.out.println("Input ID:");
                int id = scan.nextInt();
                System.out.println("Input First Name:");
                String fname = scan.next();
                System.out.println("Input Last Name:");
                String lname = scan.next();
                System.out.println("Input Address:");
                String address = scan.next();
                System.out.println("Input Salary:");
                int salary = scan.nextInt();
                Employees employee = new Employees(id, fname, lname, salary, address);
                addEmp(employee);
            } else if (choice == 2) {
                System.out.println("Input ID of project:");
                int pid = scan.nextInt();
                System.out.println("Input project name:");
                String pname = scan.next();
                int eid = 0;

                List<Employees> employees = new ArrayList<>();
                System.out.println("Select employees by ID or Input '0' to end:");
                selectEmp();
                while (true) {
                    eid = scan.nextInt();
                    if (eid == 0) {
                        System.out.println("End of selections");
                        break;
                    } else {
                        employees.add(selectEmpById(eid));
                    }
                }
                System.out.println("Input deadline");
                int deadline = scan.nextInt();
                Project project = new Project(pid, pname, employees, deadline);
                createProject(project);
            } else if (choice == 3) {
                break;
            }
        }

        scan.close();
    }
}