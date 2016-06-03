package com.jianli.Junit.mockito;

public interface PersonDao {
    public Person fetchPerson(Integer personID);

    public void update(Person person);
}