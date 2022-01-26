package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    Point D = new Point(10, 20);
    Assert.assertEquals(D.distance(), 22.360679774997898);
  }

  @Test
  public void testPoint2() {
    Point D = new Point(10, 20);
    assert D.distance() > 21;
  }

  @Test
  public void testPoint3() {
    Point D = new Point(10, 20);
    double X = 22.360679774997898;
    assert D.distance() == X;
  }
}