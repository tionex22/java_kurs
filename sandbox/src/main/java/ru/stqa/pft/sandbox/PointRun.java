package ru.stqa.pft.sandbox;

public class PointRun {

  public static void main(String[] args) {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(10, 10);
    System.out.println("Расстояние между 2 точками на плоскости = " + p1.distance(p2));
  }
}