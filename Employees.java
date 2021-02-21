public class Employees{
    private String fname, lname, address;
    private int id, salary;

    public Employees(int id, String fname, String lname, int salary, String address) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.id = id;
        this.salary = salary;
    }


    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return getFname() + " " + getLname();
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    public int getAnnualSalary() {
        return this.salary*12;
    }

    public int raiseSalary(int percent) {
        //e.g. if salary 100$ and percent is 20, it will increase to 120$
        setSalary(salary+(salary*percent/100));
        return getSalary();
    }

    public String toString() {
        return "Employee[id=" + getId() + ", name=" + getName() + ", salary=" + getSalary() + ", address=" + getAddress() + "]";
    }
}