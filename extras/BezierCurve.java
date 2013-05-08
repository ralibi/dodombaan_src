package com.ralibi.dodombaan.extras;

import com.badlogic.gdx.math.Vector2;

public class BezierCurve {
  
  public Vector2 getP1() {
    return p1;
  }

  public Vector2 getCtrlP1() {
    return ctrlP1;
  }

  public Vector2 getCtrlP2() {
    return ctrlP2;
  }

  public Vector2 getP2() {
    return p2;
  }

  private Vector2 p1;
  private Vector2 ctrlP1;
  private Vector2 ctrlP2;
  private Vector2 p2;
  
  public BezierCurve(Vector2 p1, Vector2 controlP1, Vector2 controlP2, Vector2 p2){
    
  }
}
