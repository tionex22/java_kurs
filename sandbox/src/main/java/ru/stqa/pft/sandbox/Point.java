package ru.stqa.pft.sandbox;

public class Point {
  public static void main(String[] args) {

    PointData D = new PointData(-2.3, 4, 8.5, 0.7);
    System.out.println ("Расстояние между 2 точками на плоскости = " + D.distance());
  }

  
}
