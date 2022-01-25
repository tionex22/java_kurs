package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.XMLFormatter;

public class PointTests {

  @Test
  public void testPoint() {
    PointRun D = new PointRun(-2.3, 4, 8.5, 0.7);
    Assert.assertEquals(D.distance(), 11.292918134831227);
  }

  @Test
  public void testPoint2() {
    PointRun D = new PointRun(-2.3, 4, 8.5, 0.7);
    Assert.assertNotNull (D.distance());
  }

  @Test
  public void testPoint3() {
    PointRun D = new PointRun(-2.3, 4, 8.5, 0.7);
    double X = 11.292918134831227;
    assert D.distance() == X;
  }
}