package com.driver.model;

import javax.persistence.*;


@Entity
@Table(name="admin")
public class Admin {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int admnNo;
   private String userName;
   private String password;

   public int getAdmnNo() {
      return admnNo;
   }

   public void setAdmnNo(int admnNo) {
      this.admnNo = admnNo;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Admin() {
   }

   public Admin(int admnNo, String userName, String password) {
      this.admnNo = admnNo;
      this.userName = userName;
      this.password = password;
   }
}