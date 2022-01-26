package ru.stqa.pft.sandbox;

public class PointRun {

  public static void main(String[] args) {
    Point p1 = new Point(10, 20);
    System.out.println("Расстояние между 2 точками на плоскости = " + p1.distance());
  }
}