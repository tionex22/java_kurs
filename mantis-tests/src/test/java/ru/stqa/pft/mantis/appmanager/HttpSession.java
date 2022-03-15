package ru.stqa.pft.mantis.appmanager;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class HttpSession {
  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build(); //Шаблон для работы по http request с перенаправлениями "redirect"
  }
  public boolean login(String username, String password) throws IOException { //Метод post для login
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //Метод post на url /login, с парметрами ниже
    List<BasicNameValuePair> params = new ArrayList<>(); //Переводим параметры в список для отправки
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params)); // Параметры "выше" упаковываются в params.setEntity
    CloseableHttpResponse response = httpclient.execute(post); //Отправка запроса "HttpPost post" и получаем ответ в "response"
    String body = getTextFrom(response); //Полчучаем текст из response
    return body.contains(format("<span class=\"user-info\">%s</span>", username)); //Проверяем что в тексте содержится нужный фрагмент "username"
  }
  private String getTextFrom(CloseableHttpResponse response) throws IOException { //Функция получения текста из response
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php"); //Метод get на /index.php
    CloseableHttpResponse response = httpclient.execute(get); //Выполняем запрос и получаем ответ в "response"
    String body = getTextFrom(response); //Полчучаем текст из response
    return body.contains(format("<span class=\"user-info\">%s</span>", username)); //Проверяем что в тексте содержится нужный фрагмент "username"
  }
}
