package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testPoint() {
    PointData D = new PointData(-2.3, 4, 8.5, 0.7);
    Assert.assertEquals(D.distance(), 11.292918134831227);
  }

  @Test
  public void testPoint2() {
    PointData D = new PointData(-2.3, 4, 8.5, 0.7);
    Assert.assertNotNull (D.distance());
  }

  @Test
  public void testPoint3() {
    PointData D = new PointData(-2.3, 4, 8.5, 0.7);
    double X = 11.292918134831227;
    assert D.distance() == X;
  }
}