public class Customer {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Customer() {}

    public Customer (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
            "%s [id=%d, name=%s, email=%s]",
            this.getClass().getSimpleName(),
            this.id,
            this.name,
            this.email
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Customer customer)) {
            return false;
        }

        return this.id != null && this.id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}