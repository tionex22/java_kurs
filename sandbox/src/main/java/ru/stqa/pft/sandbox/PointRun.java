package ru.stqa.pft.sandbox;

public class PointRun {

  public static void main(String[] args) {
    Point p2 = new Point(10, 20);
    System.out.println("Расстояние между 2 точками на плоскости = " + distance(p2));
  }

  public static double distance(Point p2) {
    return Math.sqrt(p2.p1 * p2.p1 + p2.p2 * p2.p2);
  }
}