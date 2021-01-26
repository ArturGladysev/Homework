package StreAm;

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
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person p = (Person) o;
            if (this == p) {
                return true;
            }
            if (this.age == p.age && this.name.equals(p.name) == true && this.gender.equals(p.gender) == true) {
                return true;
            }
            return false;
        }
   return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + age.hashCode() + gender.hashCode();
    }


    @Override
    public int compareTo(Person o) {
      if ( this.age == o.age) return 1;
       if  ( this.age > o.age) return 1;
   else return -1;
    }
}
