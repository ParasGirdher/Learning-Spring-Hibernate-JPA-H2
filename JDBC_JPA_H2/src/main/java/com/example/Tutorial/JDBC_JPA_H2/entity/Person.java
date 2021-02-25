package com.example.Tutorial.JDBC_JPA_H2.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
//@Table(name="person_details")
//No need for @Table entity if you want same table name
@NamedQueries(value = {@NamedQuery(name = "find_all_persons", query = "select p from Person p"),
    @NamedQuery(name = "find_person_byName", query = "select p from Person p where name like '%Girdher'")})
//@NamedQuery(name = "find_all_persons", query = "select p from Person p") //This is only for 1 query
//@Table(name = "person_table") //No need for this if you don't want a different name than class one
public class Person {

  @Id // Indicates that this is a primary key
  @GeneratedValue  //Hibernate will genrate its value automatically
  private Long id;
  @Column(name = "fullname", nullable = false)
  // (No need for this if you want the column name would be same)
  //nullable=false means this parameter cannot be null
  private String name;
  private String location;

  @OneToOne(fetch = FetchType.LAZY) //As all ***to One relationships are eager by default
  /*Eager fetch means for em.find query, we will not extract person only but password as well through the same query(it would be leftouter join)
  //but also lazy fetching, don't forget to use @transational in the repository or respective method to extend the hibernate session wherever you want to fetch passport from student */
  private Passport passport;
  private Date birthDate;
  @UpdateTimestamp
  private LocalDateTime lastUpdatedDate;
  @CreationTimestamp
  private LocalDateTime createdDate;

  @ManyToMany
  //In this relationship a join table gets created
  //Without any annotation of @JoinTable, table even also then gets genrated, below is to update names
  @JoinTable(name = "PersonCourse", joinColumns = @JoinColumn(name = "Student_Id"), inverseJoinColumns = @JoinColumn(name = "Course_Id"))
  private List<Course> courses = new ArrayList<>();


  private EntityHibernateCheck entityHibernateCheck;

  public EntityHibernateCheck getEntityHibernateCheck() {
    return entityHibernateCheck;
  }

  public void setEntityHibernateCheck(
      EntityHibernateCheck entityHibernateCheck) {
    this.entityHibernateCheck = entityHibernateCheck;
  }

  protected Person() {
    super();
  }

  public Person(String name, String location, Date birthDate) {
    super();
    this.name = name;
    this.location = location;
    this.birthDate = birthDate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public Passport getPassport() {
    return passport;
  }

  public void setPassport(Passport passport) {
    this.passport = passport;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void addCourses(Course course) {
    this.courses.add(course);
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", location=" + location + ", birthDate="
        + birthDate + "]";
  }
}
