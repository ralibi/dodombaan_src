package com.ralibi.dodombaan.component;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.ralibi.dodombaan.manager.ResourcesManager;

public class CheckboxEntity extends Entity{

  private ToggleListener toggleListener;
  
  private boolean checked;
  Sprite checkedButton;
  Sprite uncheckedButton;

  public CheckboxEntity(int x, int y, Scene pScene, VertexBufferObjectManager vbom, String txt, boolean checked, ToggleListener toggleListener){
    super(x, y);
    this.checked = checked;
    this.toggleListener = toggleListener;
    
    attachChild(new Text(0, 0, ResourcesManager.getInstance().fontScore, txt, vbom));
    
    checkedButton = new Sprite(100,  0, ResourcesManager.getInstance().checkboxCheckedRegion, vbom);
    uncheckedButton = new Sprite(100,  0, ResourcesManager.getInstance().checkboxUncheckedRegion, vbom){
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        switch (pSceneTouchEvent.getAction()) {
        case TouchEvent.ACTION_DOWN: {
          setChecked(!isChecked());
          return false;
        }
        default: {
          return true;
        }
        }
      }
    };
    
    attachChild(uncheckedButton);
    attachChild(checkedButton);
    pScene.registerTouchArea(uncheckedButton);
    pScene.registerTouchArea(this);

    setChecked(checked);
  }
  

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
    checkedButton.setVisible(checked);
    toggleListener.onchange();
  }
  

  public interface ToggleListener {
    void onchange();
  }
}
