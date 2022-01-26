package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(10, 10);
    Assert.assertEquals(p1.distance(p2), 14.142135623730951);
  }

  @Test
  public void testPoint2() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(10, 10);
    assert p1.distance(p2) < 15;
  }

  @Test
  public void testPoint3() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(10, 10);
    double X = 14.142135623730951;
    assert p1.distance(p2) == X;
  }
}