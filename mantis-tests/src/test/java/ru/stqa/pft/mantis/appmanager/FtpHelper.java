package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
  private final ApplicationManager app;
  private FTPClient ftp;

  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException { //Загрузка файла
    ftp.connect(app.getProperty("ftp.host")); //Коннект
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); //Логин
    ftp.deleteFile(backup); //Удаляем резервную копию
    ftp.rename(target, backup); //Переименовываем удаленный файл
    ftp.enterLocalPassiveMode(); //Пассивный режим передачи данных
    ftp.storeFile(target, new FileInputStream(file)); //Передача файла
    ftp.disconnect();
  }

  public void restore(String backup, String target) throws IOException { //Востановление исходного файла
    ftp.connect(app.getProperty("ftp.host")); //Коннект
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); //Логин
    ftp.deleteFile(target); //Удаляем загруженный файл
    ftp.rename(backup, target); //Восстанавливаем файл из рез копии
    ftp.disconnect();
  }
}
