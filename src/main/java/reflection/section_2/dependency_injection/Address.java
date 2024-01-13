package reflection.section_2.dependency_injection;

public class Address {
        private String street;
        private int number;

    public Address() {
    }

    public Address(String street, int number) {
            this.street = street;
            this.number = number;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", number=" + number +
                    '}';
        }
    }