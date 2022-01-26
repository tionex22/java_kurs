package ru.stqa.pft.sandbox;

public class PointData {
  public double x1;
  public double y1;
  public double x2;
  public double y2;

  public PointData(double p1, double p2, double p3, double p4) {
    this.x1 = p1;
    this.y1 = p2;
    this.x2 = p3;
    this.y2 = p4;

  }

  public double distance() {
    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
  }
}
