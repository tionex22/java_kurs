package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    Point p1 = new Point(10, 20);
    Assert.assertEquals(p1.distance(), 22.360679774997898);
  }

  @Test
  public void testPoint2() {
    Point p1 = new Point(10, 20);
    assert p1.distance() > 21;
  }

  @Test
  public void testPoint3() {
    Point p1 = new Point(10, 20);
    double X = 22.360679774997898;
    assert p1.distance() == X;
  }
}