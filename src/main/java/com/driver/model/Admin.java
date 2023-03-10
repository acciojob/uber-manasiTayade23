package com.driver.model;

import javax.persistence.*;


@Entity
@Table(name="admin")
public class Admin {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int AdminId;
   private String Username;
   private String password;

   public int getAdminId() {
      return AdminId;
   }

   public void setAdminId(int adminId) {
      this.AdminId = adminId;
   }

   public String getUsername() {
      return Username;
   }

   public void setUsername(String username) {
      this.Username = username;
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
      this.AdminId = admnNo;
      this.Username = userName;
      this.password = password;
   }
}