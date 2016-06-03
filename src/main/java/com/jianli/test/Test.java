package com.jianli.test;

public class Test {
    public static void main(String[] args) {
        int a = 0;
        Test.addOne(a);
        Person person = new Person("Jianli", 18, "male");
        Test.changePerson(person);
        System.out.println(person.getAge());
        System.out.println(person.getSex());
        System.out.println(a);

        StringBuilder s = new StringBuilder("hello");
        Test.append(s);
        System.out.println(s);

    }

    public static void addOne(int a) {
        a++;
    }

    public static void changePerson(Person p) {
        p.setSex("female");
        p.setAge(p.getAge() + 1);
    }

    public static void append(StringBuilder s) {
        s.append("jianli");
    }
}
