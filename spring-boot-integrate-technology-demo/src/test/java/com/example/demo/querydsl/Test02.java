package com.example.demo.querydsl;

import com.example.demo.entity.Department;
import com.example.demo.entity.Person;
import com.example.demo.entity.QDepartment;
import com.example.demo.entity.QPerson;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PersonRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(
        basePackages = "com.example.demo",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class, RestController.class})})
public class Test02 {

    @Autowired
    private JPAQueryFactory factory;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void init() {
        List<Department> departments = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Department department = new Department();
            department.setName("department-" + (i + 1));
            department.setQuantity((int)(Math.random() * 10 + 10));
            departments.add(department);
        }

        departmentRepository.saveAll(departments);

        List<Person> persons = new ArrayList<>();
        departments.forEach(department -> {
           int quantity = department.getQuantity();
           long departmentId = department.getId();
           String departmentName = department.getName();
           for(int i = 0; i < quantity; i++) {
               Person person = new Person();
               person.setDepartmentId(departmentId);
               person.setName(String.format("%s_%s-%d", departmentName, "person", i + 1));
               persons.add(person);
           }
        });

        personRepository.saveAll(persons);
    }

    /**
     * 实现多表的连接查询
     */
    @Test
    public void testJoinQuery() {
        QPerson qPerson = QPerson.person;
        QDepartment qDepartment = QDepartment.department;
        List<Person> persons = factory.select(qPerson)
                .from(qPerson)
                .leftJoin(qDepartment)
                .on(qDepartment.id.eq(qPerson.departmentId))
                .where(qDepartment.name.eq("department-1"))
                .fetch();
        persons.forEach(System.out::println);
    }
}
