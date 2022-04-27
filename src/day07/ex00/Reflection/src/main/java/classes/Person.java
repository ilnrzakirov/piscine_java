package classes;

public class Person {

    private String name;
    private Integer age;
    private Double weight;
    private boolean alive;
    private Long id;

    public void speak(String name, Long is){
        System.out.println(name + " " + is);
    }

    public Person() {
    }

    public Person(String name, Integer age, Double weight, boolean alive, Long id) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.alive = alive;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", alive=" + alive +
                ", id=" + id +
                '}';
    }
}
