package reflection.section_2.dependency_injection;

public class Person {
    private String firstName;
    private String lastName;

    private Address address;

    public Person(Address address) {
        this.address = address;
        this.firstName = "anonymous";
        this.lastName = "anonymous";
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                '}';
    }
}
