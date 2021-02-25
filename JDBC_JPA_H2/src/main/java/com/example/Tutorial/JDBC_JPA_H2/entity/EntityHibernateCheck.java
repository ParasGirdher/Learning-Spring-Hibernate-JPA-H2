package com.example.Tutorial.JDBC_JPA_H2.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class EntityHibernateCheck {

  private String param1;
  private String param2;

  public EntityHibernateCheck() {
  }

  public EntityHibernateCheck(String param1, String param2) {
    this.param1 = param1;
    this.param2 = param2;
  }

  public String getParam1() {
    return param1;
  }

  public void setParam1(String param1) {
    this.param1 = param1;
  }

  public String getParam2() {
    return param2;
  }

  public void setParam2(String param2) {
    this.param2 = param2;
  }

  @Override
  public String toString() {
    return "EntityHibernateCheck{" +
        "param1='" + param1 + '\'' +
        ", param2='" + param2 + '\'' +
        '}';
  }
}
