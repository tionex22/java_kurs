package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    save(contacts, new File(file));
  }

  private void save (List<ContactData> contacts, File file) throws IOException { //Записываем данные
    System.out.println(new File(".").getAbsoluteFile());
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getName(), contact.getLastname(), contact.getAddress()
              , contact.getEmail(), contact.getEmail2(), contact.getEmail3()
              , contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhone2()));
              /*, contact.getPhoto())));*/
    }
    writer.close();
  }

  private List<ContactData> generateContacts(int count) { //Генерируем данные
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withName(String.format("name %s", i))
              .withLastName(String.format("lastname %s", i)).withAddress(String.format("address %s", i))
              .withEmail(String.format("email %s", i)).withEmail2(String.format("email2 %s", i)).withEmail3(String.format("email3 %s", i))
              .withHomePhone(String.format("111 %s", "")).withMobilePhone(String.format("+7 (222) %s", "")).withWorkPhone(String.format("+7 (333) %s", "")).withHomePhone2(String.format("+7 (444) %s", "")));
              /*.withPhoto(new File(String.format("photo %s", i))));*/
    }
    return contacts;
  }
}
