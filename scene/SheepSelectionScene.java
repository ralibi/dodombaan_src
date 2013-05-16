package com.ralibi.dodombaan.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import com.ralibi.dodombaan.base.BaseScene;
import com.ralibi.dodombaan.component.ScrollMenuEntity;
import com.ralibi.dodombaan.component.ScrollMenuEntity.DeselectListener;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.SceneManager;
import com.ralibi.dodombaan.manager.SceneManager.SceneType;

public class SheepSelectionScene extends BaseScene {

  // ---------------------------------------------
  // VARIABLES
  // ---------------------------------------------
  private ScrollMenuEntity scrollMenuSheepP1;
  private ScrollMenuEntity scrollMenuSheepP2;

  ButtonSprite nextButton;
  ButtonSprite backButton;

  @Override
  public void createScene() {
    createBackground();
    createMenuChildScene();
    createScrollMenuSheep();
  }

  private void createScrollMenuSheep() {
    scrollMenuSheepP1 = new ScrollMenuEntity(200, 268, 280, 380, new Sprite(0, 0, resourcesManager.sheepScrollBackgroundRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        if (scrollMenuSheepP2.getSelectedMenuIndex() >= 0) {
          nextButton.setEnabled(true);
        }
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollMenuSheepP1.buildSprite(130, 190, 260, 380, resourcesManager.sheepSelectionSheepRegions, this, vbom);
    registerTouchArea(scrollMenuSheepP1);
    registerTouchArea(scrollMenuSheepP1.getScrollPanel().getChildByIndex(0));
    attachChild(scrollMenuSheepP1);
    // attachChild(scrollMenuSheepP1.getSelectButton());

    buildSheepCharacteristicMenu(scrollMenuSheepP1);

    scrollMenuSheepP2 = new ScrollMenuEntity(600, 268, 280, 380, new Sprite(0, 0, resourcesManager.sheepScrollBackgroundRegion, vbom), this, new DeselectListener() {
      @Override
      public void onSelect() {
        if (scrollMenuSheepP1.getSelectedMenuIndex() >= 0) {
          nextButton.setEnabled(true);
        }
      }

      @Override
      public void onDeselect() {
        nextButton.setEnabled(false);
      }
    });
    scrollMenuSheepP2.buildSprite(130, 190, 260, 380, resourcesManager.sheepSelectionSheepRegions, this, vbom);
    registerTouchArea(scrollMenuSheepP2);
    registerTouchArea(scrollMenuSheepP2.getScrollPanel().getChildByIndex(0));
    attachChild(scrollMenuSheepP2);

    buildSheepCharacteristicMenu(scrollMenuSheepP2);

    scrollMenuSheepP1.selectMenu(1);
    scrollMenuSheepP2.selectMenu(2);
  }

  private void buildSheepCharacteristicMenu(ScrollMenuEntity scrollMenuSheep) {
    // TODO Auto-generated method stub

    for (int i = 0; i < scrollMenuSheep.getScrollPanel().getItemCount(); i++) {

      final Entity characteristicGroup = new Entity(0, 0);
      int anchorX = 52 + i * 260;
      int anchorY = 175;
      int rightPadding = 26;

      // Strength
      final Entity strengthGroup = new Entity(0, 0);
      Text strenghtText = new Text(0, 0, resourcesManager.fontSmall, "Strength", vbom);
      strenghtText.setPosition(anchorX + rightPadding + strenghtText.getWidth() / 2f, anchorY + 10);
      strengthGroup.attachChild(new Sprite(anchorX, anchorY, resourcesManager.strengthThumbRegion, vbom));
      strengthGroup.attachChild(strenghtText);
      strengthGroup.attachChild(getCharacteristicMeter(anchorX + rightPadding, anchorY - 10, GameConfigurationManager.STRENGTH[i]));
      characteristicGroup.attachChild(strengthGroup);

      anchorY -= 43;

      // Speed
      final Entity speedGroup = new Entity(0, 0);
      Text speedText = new Text(0, 0, resourcesManager.fontSmall, "Speed", vbom);
      speedText.setPosition(anchorX + rightPadding + speedText.getWidth() / 2f, anchorY + 10);
      speedGroup.attachChild(new Sprite(anchorX, anchorY, resourcesManager.speedThumbRegion, vbom));
      speedGroup.attachChild(speedText);
      speedGroup.attachChild(getCharacteristicMeter(anchorX + rightPadding, anchorY - 10, GameConfigurationManager.SPEED[i]));
      characteristicGroup.attachChild(speedGroup);

      anchorY -= 43;

      final Entity agilityGroup = new Entity(0, 0);
      Text agilityText = new Text(0, 0, resourcesManager.fontSmall, "Agility", vbom);
      agilityText.setPosition(anchorX + rightPadding + agilityText.getWidth() / 2f, anchorY + 10);
      agilityGroup.attachChild(new Sprite(anchorX, anchorY, resourcesManager.agilityThumbRegion, vbom));
      agilityGroup.attachChild(agilityText);
      agilityGroup.attachChild(getCharacteristicMeter(anchorX + rightPadding, anchorY - 10, GameConfigurationManager.AGILITY[i]));
      characteristicGroup.attachChild(agilityGroup);

      scrollMenuSheep.getScrollPanel().attachChild(characteristicGroup);
    }
  }

  private Entity getCharacteristicMeter(int posX, int posY, int nRect) {

    /* Create the rectangles. */
    final Entity strengthRectangleGroup = new Entity(0, 0);
    for (int i = 0; i < nRect; i++) {
      strengthRectangleGroup.attachChild(new Sprite(posX + 16 + i * 38, posY, resourcesManager.powerFilledRegion, vbom));
    }
    for (int i = nRect; i < 4; i++) {
      strengthRectangleGroup.attachChild(new Sprite(posX + 16 + i * 38, posY, resourcesManager.powerBlankRegion, vbom));
    }
    return strengthRectangleGroup;
  }

  protected void itemClick(int i) {
    Debug.d("Clicking: " + i);
  }

  private void createBackground() {
    attachChild(new Sprite(400, 240, resourcesManager.baseBackgroundRegion, vbom) {
      @Override
      protected void preDraw(GLState pGLState, Camera pCamera) {
        super.preDraw(pGLState, pCamera);
        pGLState.enableDither();
      }
    });
  }

  private void createMenuChildScene() {

    nextButton = new ButtonSprite(700, 35, resourcesManager.nextNormalRegion, resourcesManager.nextPressedRegion, resourcesManager.nextDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        gameDataManager.p1SheepIndex = scrollMenuSheepP1.getSelectedMenuIndex();
        gameDataManager.p2SheepIndex = scrollMenuSheepP2.getSelectedMenuIndex();
        SceneManager.getInstance().loadMatchSettingsSceneFromSheepSelection(engine);
        playSound(CLICK_SOUND);
      }
    });
    registerTouchArea(nextButton);
    attachChild(nextButton);

    backButton = new ButtonSprite(100, 30, resourcesManager.backNormalRegion, resourcesManager.backPressedRegion, resourcesManager.backDisabledRegion, vbom, new OnClickListener() {
      @Override
      public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
        playSound(CLICK_SOUND);
      }
    });
    registerTouchArea(backButton);
    attachChild(backButton);
  }

  @Override
  public void onBackKeyPressed() {
    SceneManager.getInstance().loadMenuSceneFromSheepSelection(engine);
  }

  @Override
  public SceneType getSceneType() {
    return SceneType.SCENE_SHEEP_SELECTION;
  }

  @Override
  public void disposeScene() {
    // scrollMenuSheepP1.detachChildren();
    // scrollMenuSheepP2.detachChildren();
    // detachChildren();
  }

  @Override
  public void unTouchScrollMenu() {
    this.scrollMenuSheepP1.getScrollPanel().setTouching(false);
    this.scrollMenuSheepP2.getScrollPanel().setTouching(false);
  }

}
