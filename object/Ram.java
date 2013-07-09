package com.ralibi.dodombaan.object;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.GameDataManager;
import com.ralibi.dodombaan.manager.ResourcesManager;

public class Ram {
  private final int SEGMENT_COUNT = 3;
  private final float PX_TO_M_RATIO = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
  private FixtureDef fixtureDef;

  private float density;
  private float force;
  private float angle;
  private int linear_damping;
  private int angular_damping;
  private final int ARENA_CENTER_FORCE = 3;

  private final int ARENA_WIDTH = 32;

  private List<Body> bodySegments = new ArrayList<Body>();
  private List<Sprite> spriteSegments = new ArrayList<Sprite>();
  private float[] segmentOut;

  private boolean out = false;

  public float x = 0;
  public float y = 0;
  public int ramIndex = 0;
  public int direction = 1;
  Sprite ram_drop;

  public float segmentWidth = 32f;
  public float segmentHeight = 32f;

  Rectangle indicator;
  Rectangle indicatorBackground;
  Rectangle indicatorBlackBackground;

  private float center = 0;
  private float absCenter = 0;
  private boolean matchOver = false;

  private List<MouseJoint> mMouseJointActive = new ArrayList<MouseJoint>();
  private List<Body> mGroundBody = new ArrayList<Body>();

  private PhysicsWorld mPhysicsWorld;

  // GETTER SETTER

  public float getCenter() {
    return center;
  }

  public void setCenter(float center) {
    this.center = center;
  }

  public float getAbsCenter() {
    return absCenter;
  }

  public void setAbsCenter(float absCenter) {
    this.absCenter = absCenter;
  }

  public void setMatchOver(boolean matchOver) {
    this.matchOver = matchOver;
  }

  // ///////////////

  public Ram(float pX, float pY, int sIdx, int dir) {
    this.x = pX;
    this.y = pY;
    this.ramIndex = sIdx;
    this.direction = dir;
  }

  public void createAndAttachRam(Scene pScene, PhysicsWorld mPhysicsWorld, VertexBufferObjectManager vbom) {
    final Vector2[] vertices = getSegmentVertices();

    this.mPhysicsWorld = mPhysicsWorld;
    segmentOut = new float[SEGMENT_COUNT];

    density = GameConfigurationManager.DENSITY[GameConfigurationManager.STRENGTH[ramIndex]];
    force = density * GameConfigurationManager.FORCE[GameConfigurationManager.SPEED[ramIndex]];
    angle = GameConfigurationManager.ANGLE[GameConfigurationManager.AGILITY[ramIndex]];
    linear_damping = GameConfigurationManager.DAMPING[GameDataManager.getInstance().arenaIndex];
    angular_damping = linear_damping;

    // Segment 0 is the head
    List<ITextureRegion> ramSegmentRegion = new ArrayList<ITextureRegion>(); 
    ramSegmentRegion.add(ResourcesManager.getInstance().gamePlayRamSegment1Regions.get(ramIndex));
    ramSegmentRegion.add(ResourcesManager.getInstance().gamePlayRamSegment2Regions.get(ramIndex));
    ramSegmentRegion.add(ResourcesManager.getInstance().gamePlayRamSegment3Regions.get(ramIndex));
    
    for (int i = 0; i < SEGMENT_COUNT; i++) {
      final int itemI = i;
      Sprite sprite = new Sprite(this.x + (32 * direction * i), 240 - 60 * direction, ramSegmentRegion.get(i), vbom) {
        @Override
        protected void onManagedUpdate(final float pSecondsElapsed) {
          if (!matchOver) {
            if (this.mY < 240 + ARENA_WIDTH / 2 && this.mY > 240 - ARENA_WIDTH / 2) {
              segmentOut[itemI] = this.mY - 240;
            } else if (this.mY >= 240 + ARENA_WIDTH / 2) {
              segmentOut[itemI] = ARENA_WIDTH / 2;
            } else {
              segmentOut[itemI] = -ARENA_WIDTH / 2;
            }
            if (bodySegments.size() == SEGMENT_COUNT) {
              if (itemI == 0) {
                Body body = bodySegments.get(itemI);
                float dY = (body.getPosition().y - 240 / PX_TO_M_RATIO + getDeltaHeadY()) * ARENA_CENTER_FORCE;
                body.applyForce(new Vector2(ARENA_CENTER_FORCE * direction, -dY), new Vector2(body.getPosition().x + direction / 2, body.getPosition().y));

              } else if (itemI == SEGMENT_COUNT - 1) {
                Body body = bodySegments.get(itemI);
                float dY = (body.getPosition().y - 240 / PX_TO_M_RATIO + getDeltaTailY()) * ARENA_CENTER_FORCE;
                body.applyForce(new Vector2(-ARENA_CENTER_FORCE * direction, -dY), new Vector2(body.getPosition().x - direction / 2, body.getPosition().y));
              }
            }
          } else {
            if (isOut()) {
              for (int j = 0; j < spriteSegments.size(); j++) {
                if (Math.abs(spriteSegments.get(j).getY() - 240) < 50) {
                  bodySegments.get(j).applyForce(bodySegments.get(j).getWorldVector(new Vector2(0, center / absCenter * linear_damping * force / 100)), bodySegments.get(j).getPosition());
                }
              }
            }
          }

          updateCenter();
          updateIndicator();

          super.onManagedUpdate(pSecondsElapsed);
        }
      };
      
      if(direction < 0) {
        sprite.setFlippedHorizontal(true);
      }
      
      fixtureDef = PhysicsFactory.createFixtureDef(density, 0.5f, 0.5f);
      Body body = PhysicsFactory.createPolygonBody(mPhysicsWorld, sprite, vertices, BodyType.DynamicBody, fixtureDef);
      body.setLinearDamping(linear_damping);
      body.setAngularDamping(angular_damping);
      pScene.attachChild(sprite);
      mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
      spriteSegments.add(sprite);
      bodySegments.add(body);
    }

    for (int i = 0; i < SEGMENT_COUNT - 1; i++) {

      final float halfWidth = segmentWidth / PX_TO_M_RATIO / 2f;

      final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
      revoluteJointDef.initialize(bodySegments.get(i), bodySegments.get(i + 1), bodySegments.get(i).getWorldCenter());
      revoluteJointDef.enableMotor = false;
      revoluteJointDef.localAnchorA.set(-halfWidth * direction, 0f);
      revoluteJointDef.localAnchorB.set(halfWidth * direction, 0f);
      revoluteJointDef.enableLimit = true;
      revoluteJointDef.lowerAngle = MathUtils.degToRad(-angle);
      revoluteJointDef.upperAngle = MathUtils.degToRad(angle);

      mPhysicsWorld.createJoint(revoluteJointDef);
    }
    

    ram_drop = new Sprite(0, 0, ResourcesManager.getInstance().gamePlayRamDropRegions.get(ramIndex), vbom);
    if(direction < 0) {
    	ram_drop.setFlippedHorizontal(true);
    }
    pScene.attachChild(ram_drop);

    createIndicator(pScene);
  }

  private Vector2[] getSegmentVertices() {
    final float mCenter = 0f;
    final float halfWidth = segmentWidth / PX_TO_M_RATIO / 2f;
    final float halfHeight = segmentHeight / PX_TO_M_RATIO / 2f;

    Vector2[] vertices = { new Vector2(mCenter + halfWidth, mCenter + halfHeight), new Vector2(mCenter - halfWidth, mCenter + halfHeight), new Vector2(mCenter - halfWidth, mCenter - halfHeight), new Vector2(mCenter + halfWidth, mCenter - halfHeight) };

    return vertices;
  }

  public void moveForward(int orientation_type) {
    float dY = orientation_type * density * 4;

    bodySegments.get(0).applyForce(bodySegments.get(0).getWorldVector(new Vector2(force * direction, -dY)), new Vector2(bodySegments.get(0).getPosition().x + direction / 2, bodySegments.get(0).getPosition().y));
  }

  public void jumpBackward() {
    // emporary disable jump backward 
//    int segment_idx = SEGMENT_COUNT - 1;
//    float dY = (bodySegments.get(segment_idx).getPosition().y - 240 / PX_TO_M_RATIO + getDeltaHeadY()) * SHEEP_CENTER_FORCE;
//    bodySegments.get(segment_idx).applyForce(bodySegments.get(segment_idx).getWorldVector(new Vector2(force * BACKWARD_FORCE * direction, -dY)), new Vector2(bodySegments.get(segment_idx).getPosition().x + direction / 2, bodySegments.get(segment_idx).getPosition().y));
  }
  
  private void jumpForward() {
    int segment_idx = 0;
    bodySegments.get(segment_idx).applyForce(new Vector2(force * 30 * direction, 0), new Vector2(bodySegments.get(segment_idx).getPosition().x + direction / 2, bodySegments.get(segment_idx).getPosition().y));
  }
  
  public void specialStrike() {
  	jumpForward();
  }
  public void specialSpeed() {
  	
  }
  public void specialDefence() {
  	
  }

  private float getDeltaHeadY() {
    return ((float) Math.sin((double) bodySegments.get(0).getAngle())) * 0.5f * direction;
  }

  private float getDeltaTailY() {
    return ((float) Math.sin((double) bodySegments.get(SEGMENT_COUNT - 1).getAngle())) * 0.5f;
  }

  public void setOut(boolean out) {
    this.out = out;
  }

  public boolean isOut() {
    return this.out;
  }

  protected void updateCenter() {
    float sum = 0;
    for (int i = 0; i < SEGMENT_COUNT - 1; i++) {
      sum += segmentOut[i];
    }

    setCenter(sum);
    setAbsCenter(Math.abs(sum));

		
    if (absCenter >= 32) {
      setOut(true);

  		ram_drop.setPosition(spriteSegments.get(1));
  		ram_drop.setRotation(spriteSegments.get(1).getRotation());
  		
      if(sum < 0){
	    	ram_drop.setFlippedVertical(true);
	  		ram_drop.setY(ram_drop.getY() - 20);
      }
      else{
	    	ram_drop.setFlippedVertical(false);
	  		ram_drop.setY(ram_drop.getY() + 20);
      }
    } else {
      setOut(false);
    }
  }

  protected void updateIndicator() {
    indicator.setPosition(getHeadPosX(), getHeadPosY() + (absCenter + 24) * center / absCenter);
    indicatorBlackBackground.setPosition(getHeadPosX(), getHeadPosY() + (32 + 24) * center / absCenter);
    indicatorBackground.setPosition(getHeadPosX(), getHeadPosY() + (32 + 24) * center / absCenter);
    
    indicator.setWidth(2 * absCenter);
    if (absCenter <= 16) {
      // indicatorP1.setColor(absCenter / 16, 1, 0);
    } else {
      // indicatorP1.setColor(1, 1 - ((absCenter - 16) / 16), 0);
    }
  }

  public boolean isInPosition() {

    Debug.d("CHECK POSITIONAINg");
    boolean result = true;
    for (int i = 0; i < SEGMENT_COUNT; i++) {
      result = result && Math.abs(spriteSegments.get(i).getY() - 240) < 3;
    }
    return result;
  }

  private void createIndicator(Scene pScene) {
    indicator = new Rectangle(getHeadPosX(), getHeadPosY(), 64, 4, ResourcesManager.getInstance().vbom);
    indicator.setColor(.6f, 0, 0);

    indicatorBlackBackground = new Rectangle(getHeadPosX(), getHeadPosY(), 66, 6, ResourcesManager.getInstance().vbom);
    indicatorBlackBackground.setColor(0, 0, 0);
    
    indicatorBackground = new Rectangle(getHeadPosX(), getHeadPosY(), 64, 4, ResourcesManager.getInstance().vbom);
    indicatorBackground.setColor(0, .6f, 0);

    rotateIndicator(90);

    pScene.attachChild(indicatorBlackBackground);
    pScene.attachChild(indicatorBackground);
    pScene.attachChild(indicator);
  }

  private float getHeadPosX() {
    return spriteSegments.get(0).getX();// +
                                        // bodySegments.get(0).getWorldCenter().x;
  }

  private float getHeadPosY() {
    return spriteSegments.get(0).getY();// +
                                        // bodySegments.get(0).getWorldCenter().y;
  }

  private void rotateIndicator(int degree) {
    indicator.setRotation(degree);
    indicatorBackground.setRotation(degree);
    indicatorBlackBackground.setRotation(degree);
  }

  public void restartPosition() {
    Debug.d("REs Pos");
    mMouseJointActive.clear();
    mGroundBody.clear();

    for (int i = 0; i < SEGMENT_COUNT; i++) {
      mGroundBody.add(mPhysicsWorld.createBody(new BodyDef()));

      // final Vector2 localPoint =
      // bodySegments.get(i).getWorldPoint(Vector2Pool.obtain(spriteSegments.get(i).getX()/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
      // spriteSegments.get(i).getY()/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT));

      final MouseJointDef mouseJointDef = new MouseJointDef();
      // mGroundBody.get(i).setTransform(localPoint, 0);
      mouseJointDef.bodyA = mGroundBody.get(i);
      mouseJointDef.bodyB = bodySegments.get(i);
      mouseJointDef.dampingRatio = 8f / linear_damping;
      mouseJointDef.frequencyHz = 10;
      mouseJointDef.maxForce = (12f * linear_damping * bodySegments.get(i).getMass());
      mouseJointDef.collideConnected = true;

      mouseJointDef.target.set(bodySegments.get(i).getWorldCenter());
      // Vector2Pool.recycle(localPoint);

      mMouseJointActive.add((MouseJoint) mPhysicsWorld.createJoint(mouseJointDef));

    }
  }

  public void positioning() {

    for (int i = 0; i < SEGMENT_COUNT; i++) {
      final Vector2 vec = Vector2Pool.obtain((this.x + (direction * 64) - (32 * direction * i)) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (240) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
      mMouseJointActive.get(i).setTarget(vec);
      Vector2Pool.recycle(vec);
      showDrop(false);
    }
  }

  public void positionReady() {
    matchOver = false;
    setOut(false);
    for (int i = 0; i < SEGMENT_COUNT; i++) {
      mPhysicsWorld.destroyJoint(mMouseJointActive.get(i));
    }
    mMouseJointActive.clear();
    mGroundBody.clear();
  }

	public void showDrop(boolean showed) {
    ram_drop.setVisible(showed);
    for (int i = 0; i < SEGMENT_COUNT; i++) {
    	spriteSegments.get(i).setVisible(!showed);
    }
  }
}
