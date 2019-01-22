package entities;

public class Bill extends Entity {
    private User user;
    private Service service;
    private Long bill;
    private Long paid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Long getBill() {
        return bill;
    }

    public void setBill(Long bill) {
        this.bill = bill;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }
}
