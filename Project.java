import java.util.ArrayList;
import java.util.List;

public class Project {
    private int proj_id, total_price = 0, deadline;
    private String proj_name;
    private List<Employees> employees = new ArrayList<>();

    public Project() {
    }

    public Project(int proj_id, String proj_name, List<Employees> employees, int deadline) {
        this.proj_id = proj_id;
        this.proj_name = proj_name;
        this.employees = employees;
        this.deadline = deadline;
    }

    public int getProj_id() {
        return this.proj_id;
    }

    public void setProj_id(int proj_id) {
        this.proj_id = proj_id;
    }

    public String getProj_name() {
        return this.proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public List<Employees> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public int getDeadline() {
        return this.deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getTotalPrice(){
        for (int i = 0; i < employees.size(); i++) {
            this.total_price += employees.get(i).getSalary();
        }
        this.total_price *= deadline;
        return this.total_price;
    }

    @Override
    public String toString() {
        return "{" +
            " proj_id='" + getProj_id() + "'" +
            ", proj_name='" + getProj_name() + "'" +
            ", employees='" + getEmployees() + "'" +
            "}";
    }

}
