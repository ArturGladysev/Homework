package MyStream;

public class Person implements Comparable<Person>{
private String name;
private Integer age;
private String gender;

    public Person(String name , int age , String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() { return getAge()+"`" + getGender()+ "`" +getName();}

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
            return true;
        if(object == null)
            return false;
        if(object instanceof Person)
        {
            Person person = (Person)object;
            if(this.age == person.age && this.name.equals(person.name) == true && this.gender.equals(person.gender) == true)
                return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        return name.hashCode() + age.hashCode() + gender.hashCode();
    }

    @Override
    public int compareTo(Person person)
    {
        if(this.age == person.age)
            return 1;
        if(this.age > person.age)
            return 1;
        else
            return -1;
    }
}
