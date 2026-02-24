// javac -cp "sqlite-jdbc-3.51.2.0.jar" -d target *.java
// java -cp "sqlite-jdbc-3.51.2.0.jar:target" Program

public class Program {
    private static final String connectionUrl = "jdbc:sqlite:db";

    public static void main(String[] args) {
        DataBaseInitializer.initializeCustomerDataBase(connectionUrl);

        CustomerService customerService = new CustomerService(new CustomerDAO(), connectionUrl);

        Customer luffy = new Customer(
            "Monkey D. Luffy",
            "luffy@strawhat.pirate",
            "GomuGomuNoMi123"
        );

        Customer zoro = new Customer(
            "Roronoa Zoro",
            "zoro@santoryu.sword",
            "ThreeSwordStyle!"
        );

        Customer nami = new Customer(
            "Nami",
            "nami@navigator.thousand",
            "CatBurglar$$"
        );

        System.out.println("-> customer objects created");

        System.out.println(luffy);
        System.out.println(zoro);
        System.out.println(nami);

        customerService.insert(luffy);
        customerService.insert(zoro);
        customerService.insert(nami);

        System.out.println("-> customer objects inserted");

        System.out.println(
            customerService.findById(luffy.getId()).orElseThrow(
                () -> new RuntimeException("Customer not found.")
            )
        );

        System.out.println(
            customerService.findById(zoro.getId()).orElseThrow(
                () -> new RuntimeException("Customer not found.")
            )
        );

        System.out.println(
            customerService.findById(nami.getId()).orElseThrow(
                () -> new RuntimeException("Customer not found.")
            )
        );

        System.out.println("-> find all customers from database");

        System.out.println(customerService.findAll());

        customerService.deleteById(nami.getId());

        System.out.println("-> delete customer from database");

        System.out.println(customerService.findAll());

        luffy.setEmail("luffy@strawhat.onepiece");
        zoro.setEmail("zoro@santoryu.onepiece");

        customerService.update(luffy);
        customerService.update(zoro);

        System.out.println("-> update customers");

        System.out.println(customerService.findAll());
    }
}