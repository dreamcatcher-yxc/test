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
public class Test03 {

    @Autowired
    private JPAQueryFactory factory;

    /**
     * 查询返回自动以对象
     */
    @Test
    public void testBackCustomObject() {
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
