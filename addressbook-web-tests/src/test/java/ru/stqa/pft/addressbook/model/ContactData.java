package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@XStreamAlias("contact") //Наименование XML
@Entity //Для SQL определяет класс ContactData привязанным к базе
@Table(name="addressbook") //Для SQL привязка к таблице

public class ContactData {

  @XStreamOmitField
  @Id
  @Column(name = "id") //Для SQL
  private int id = Integer.MAX_VALUE;

  @Expose
  @Column(name = "firstname") //Для SQL
  private String name;

  @Expose
  @Column(name = "lastname") //Для SQL
  private String lastname;

  @Expose
  @Column(name = "address") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String address;

  @Expose
  @Column(name = "email") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String email;

  @Expose
  @Column(name = "email2") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String email2;

  @Expose
  @Column(name = "email3") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String email3;

  @Expose
  @Column(name = "home") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String home;

  @Expose
  @Column(name = "mobile") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String mobile;

  @Expose
  @Column(name = "work") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String work;

  @Expose
  @Column(name = "phone2") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String home2;

  @Expose
  @Transient //Для SQL аннотация исключает колонку
  private String allPhones;

  @Expose
  @Transient //Для SQL аннотация исключает колонку
  private String allEmails;

  @Expose
  @Column(name = "photo") //Для SQL
  @Type(type = "text") ////Для SQL устанавливаем тип колонки/поля для (varchar(225))
  private String photo; //Картинка

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "address_in_groups",
          joinColumns = @JoinColumn(name = "id"),
          inverseJoinColumns = @JoinColumn(name = "group_id")) //Для SQL джоиним таблицу
  private Set<GroupData> groups = new HashSet<GroupData>();


  public Groups getGroups() {
    return new Groups(groups);
  }

  public int getId() {
    return id;
  }

  public File getPhoto() {
    return new File(this.photo);
  }

  public String getAllEmails() {
    return allEmails;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getName() {
    return name;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getMobilePhone() {
    return mobile;
  }

  public String getEmail() {
    return email;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
  }

  public String getHomePhone() {
    return home;
  }

  public String getHomePhone2() {
    return home2;
  }

  public String getWorkPhone() {
    return work;
  }



  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withName(String name) {
    this.name = name;
    return this;
  }

  public ContactData withLastName(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withHomePhone(String home) {
    this.home = home;
    return this;
  }

  public ContactData withHomePhone2(String home2) {
    this.home2 = home2;
    return this;
  }

  public ContactData withMobilePhone(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public ContactData withWorkPhone(String work) {
    this.work = work;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactData that = (ContactData) o;

    if (id != that.id) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (address != null ? !address.equals(that.address) : that.address != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (email2 != null ? !email2.equals(that.email2) : that.email2 != null) return false;
    if (email3 != null ? !email3.equals(that.email3) : that.email3 != null) return false;
    if (home != null ? !home.equals(that.home) : that.home != null) return false;
    if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
    if (work != null ? !work.equals(that.work) : that.work != null) return false;
    return home2 != null ? home2.equals(that.home2) : that.home2 == null;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (email2 != null ? email2.hashCode() : 0);
    result = 31 * result + (email3 != null ? email3.hashCode() : 0);
    result = 31 * result + (home != null ? home.hashCode() : 0);
    result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
    result = 31 * result + (work != null ? work.hashCode() : 0);
    result = 31 * result + (home2 != null ? home2.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            ", email2='" + email2 + '\'' +
            ", email3='" + email3 + '\'' +
            ", home='" + home + '\'' +
            ", mobile='" + mobile + '\'' +
            ", work='" + work + '\'' +
            ", home2='" + home2 + '\'' +
            ", photo='" + photo + '\'' +
            '}';
  }
}
