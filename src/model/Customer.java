package model;

import interfaces.Validatable;

public class Customer extends BaseEntity implements Validatable {
    private String email;

    public Customer() {
    }

    public Customer(int id, String name, String email) {
        super(id, name);
        this.email = email;
    }

    @Override
    public String getEntityType() {
        return "Customer";
    }

    @Override
    public String shortInfo() {
        return "Customer{id=" + getId() + ", name=" + getName() + ", email=" + email + "}";
    }

    @Override
    public void validate() {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new exception.InvalidInputException("Customer name must not be empty");
        }
        // email optional, но если есть — простая проверка
        if (email != null && !email.trim().isEmpty()) {
            if (!email.contains("@")) {
                throw new exception.InvalidInputException("Customer email is not valid");
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
