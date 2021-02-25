package com.example.Tutorial.JDBC_JPA_H2.repository;

import com.example.Tutorial.JDBC_JPA_H2.entity.EntityHibernateCheck;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.Tutorial.JDBC_JPA_H2.entity.Person;

@Repository
@Transactional // When we want to do multiple transactions to be passed or failed completely
// (eg: to transfer money) but it is recomended to use this at business services level
public class PersonJpaRepository {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  // connect to the database
  // @PersistenceContext
  @Autowired
  EntityManager entityManager; // EntityManager is an interface to the Persistance Context

  public Person save(Person person) {
    EntityHibernateCheck entityHibernateCheck = new EntityHibernateCheck("Hello", "Hello again");
    person.setEntityHibernateCheck(entityHibernateCheck);
    if (person.getId() == null) {
      entityManager.persist(person);
    } else {
      entityManager.merge(person);
    }
    return person;
  }

  public Person findById(Long id) {
    return entityManager.find(Person.class, id);
  }

  public void updateById(Long id) {
    Person p = entityManager.find(Person.class, id);
    p.setName("Pratik Todi");
  }

  public void deleteById(Long id) {
    Person person = entityManager.find(Person.class, id);
    if (person != null) {
      entityManager.remove(person);
    }
  }

  public void playWithEntityManager(Person p) {
    entityManager.persist(p);
    entityManager.flush(); //flush will insert the data to the database
    //entityManager.detach(p); //detach or clear will let the previous thing not be tracked by entity manager anymore.
    p.setLocation("Gurgaon"); //with detach or clear it's not going to work.
    entityManager.refresh(
        p); //with this we are going to fetch p from database & the earlier changes will not be tracked anymore
    entityManager.flush();
  }

  // JPQL Queries-------------------------------------------------------

  public List<Person> jpql_basic_find() {
    Query query = entityManager.createQuery("Select p from Person p where name like '%Girdher' ");
    return query.getResultList();
  }

  public List<Person> jpql_typed_find() {
    TypedQuery<Person> query = entityManager
        .createQuery("Select p from Person p where id=1", Person.class);
    return query.getResultList();
  }


  //Named Query
  public List<Person> findAll() {
    TypedQuery<Person> namedQuery = entityManager
        .createNamedQuery("find_all_persons", Person.class);
    return namedQuery.getResultList();
  }

  // Native queris----------------------------------

  public void nativeQueriesBasics() {
    Query query = entityManager.createNativeQuery("Select * from Person", Person.class);
    List<Person> resultList = query.getResultList();
    logger.info("{}", resultList);
  }

  public void nativeQueriesWithParameters() {
    Query query = entityManager.createNativeQuery("Select * from Person where id= ?", Person.class);
    query.setParameter(1, 3L);
    List<Person> resultList = query.getResultList();
    logger.info("{}", resultList);
  }

  public void nativeQueriesWithNamedParameters() {
    Query query = entityManager
        .createNativeQuery("Select * from Person where id= :id", Person.class);
    query.setParameter("id", 3L);
    List<Person> resultList = query.getResultList();
    logger.info("{}", resultList);
  }

  // to do mass update native query comes in handy as in jpa it is only able to update all rows one by one.
  public void nativeQueriesToUpdate() {
    Query query = entityManager
        .createNativeQuery("Update Person set last_updated_date=sysdate()", Person.class);
    int numberOfRowsUpdate = query.executeUpdate();
    logger.info("{}", numberOfRowsUpdate);
  }


}
