package com.ralibi.dodombaan.object;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.ralibi.dodombaan.manager.GameConfigurationManager;
import com.ralibi.dodombaan.manager.ResourcesManager;

public class Sheep {
	private final int SEGMENT_COUNT = 3;
	private final float PX_TO_M_RATIO = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	private FixtureDef fixtureDef;
	
	private final int LINEAR_DAMPING = 10;
	private final int ANGULAR_DAMPING = 10;
	private float force = 120;
	private final int ANGLE_LIMIT = 30;
	private final int ARENA_CENTER_FORCE = 3;
	private final int SHEEP_CENTER_FORCE = 10;
	private final int BACKWARD_FORCE = -50;

	private final int ARENA_WIDTH = 32;
	
	
	private List<Body> bodySegments = new ArrayList<Body>();
	private List<Sprite> spriteSegments = new ArrayList<Sprite>();
	private float[] segmentOut;
	
	private boolean out = false;
	
	public float x = 0;
	public float y = 0;
	public int sheepIndex = 0;
	public int direction = 1;

	public float segmentWidth = 32f;
	public float segmentHeight = 32f;
	
	Rectangle indicatorP1;
	Rectangle indicatorBackgroundP1;
	
	public float center = 0;
	public float absCenter = 0;
	
	
	
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
	
	
	// ///////////////
	
	
	public Sheep(float pX, float pY, int sIdx, int dir){
		this.x = pX;
		this.y = pY;
		this.sheepIndex = sIdx;
		this.direction = dir;
	}
	
	public void createAndAttachSheep(Scene pScene, PhysicsWorld mPhysicsWorld, VertexBufferObjectManager vbom) {
		final Vector2[] vertices = getSegmentVertices();
		
		segmentOut = new float[SEGMENT_COUNT];
		// Segment 0 is the head
		for (int i = 0; i < SEGMENT_COUNT; i++) {
			final int itemI = i;
			Sprite sprite = new Sprite(this.x + (32*direction*i), 240, ResourcesManager.getInstance().gamePlaySheepSegmentRegions.get(sheepIndex), vbom){
				@Override
				protected void onManagedUpdate(final float pSecondsElapsed) {
					if(this.mY < 240 + ARENA_WIDTH/2 && this.mY > 240 - ARENA_WIDTH/2){
						segmentOut[itemI] = this.mY - 240;
					}
					else if(this.mY >= 240 + ARENA_WIDTH/2){
						segmentOut[itemI] = ARENA_WIDTH/2;
					}
					else{
						segmentOut[itemI] = -ARENA_WIDTH/2;
					}
					if(bodySegments.size() == SEGMENT_COUNT){
						if(itemI == 0){
							Body body = bodySegments.get(itemI);
							float dY = (body.getPosition().y - 240/PX_TO_M_RATIO + getDeltaHeadY()) * ARENA_CENTER_FORCE;
							body.applyForce(  new Vector2(ARENA_CENTER_FORCE * direction, -dY)  , new Vector2(body.getPosition().x + direction/2, body.getPosition().y));
							
						}
						else if(itemI == SEGMENT_COUNT - 1){
							Body body = bodySegments.get(itemI);
							float dY = (body.getPosition().y - 240/PX_TO_M_RATIO + getDeltaTailY()) * ARENA_CENTER_FORCE;
							body.applyForce( new Vector2(-ARENA_CENTER_FORCE * direction, -dY)  , new Vector2(body.getPosition().x - direction/2, body.getPosition().y));
						}
					}
					indicatorP1.setPosition(getHeadPosX(), getHeadPosY() + (absCenter + 24) * center/absCenter);
					indicatorBackgroundP1.setPosition(getHeadPosX(), getHeadPosY() + (32 + 24) * center/absCenter);
					indicatorP1.setWidth(2 * absCenter);
					if(absCenter <= 16){
						indicatorP1.setColor(absCenter / 16, 1, 0);
					}
					else{
						indicatorP1.setColor(1, 1 - ((absCenter - 16) / 16), 0);
					}
					
					super.onManagedUpdate(pSecondsElapsed);
				}
			};
			fixtureDef = PhysicsFactory.createFixtureDef(GameConfigurationManager.DENSITY[GameConfigurationManager.STRENGTH[sheepIndex]], 0.5f, 0.5f);
			Body body = PhysicsFactory.createPolygonBody(mPhysicsWorld, sprite, vertices, BodyType.DynamicBody, fixtureDef);
			body.setLinearDamping(LINEAR_DAMPING);
			body.setAngularDamping(ANGULAR_DAMPING);
			pScene.attachChild(sprite);
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
			
			force = GameConfigurationManager.DENSITY[GameConfigurationManager.STRENGTH[sheepIndex]] * 3/5 * GameConfigurationManager.FORCE[GameConfigurationManager.SPEED[sheepIndex]];

			spriteSegments.add(sprite);
			bodySegments.add(body);
			segmentOut[i] = -1;
		}

		for (int i = 0; i < SEGMENT_COUNT - 1; i++) {

			final float halfWidth = segmentWidth/PX_TO_M_RATIO/2f;
			
			final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
			revoluteJointDef.initialize(bodySegments.get(i), bodySegments.get(i+1), bodySegments.get(i).getWorldCenter());
			revoluteJointDef.enableMotor = false;
			revoluteJointDef.localAnchorA.set(-halfWidth * direction, 0f);
			revoluteJointDef.localAnchorB.set(halfWidth * direction, 0f);
			revoluteJointDef.enableLimit = true;
			revoluteJointDef.lowerAngle = MathUtils.degToRad(-ANGLE_LIMIT);
			revoluteJointDef.upperAngle = MathUtils.degToRad(ANGLE_LIMIT);

			mPhysicsWorld.createJoint(revoluteJointDef);
		}
		
		createIndicator(pScene);
	}
	
	private Vector2[] getSegmentVertices() {
		final float mCenter = 0f;
		final float halfWidth = segmentWidth/PX_TO_M_RATIO/2f;
		final float halfHeight = segmentHeight/PX_TO_M_RATIO/2f;

		Vector2[] vertices = {
		    new Vector2(mCenter + halfWidth,  mCenter + halfHeight),
		    new Vector2(mCenter - halfWidth,  mCenter + halfHeight),
		    new Vector2(mCenter - halfWidth,  mCenter - halfHeight),
		    new Vector2(mCenter + halfWidth,  mCenter - halfHeight)
		 };
		
		return vertices;
	}

	public void moveForward(int orientation_type) {
		//float dY = (bodySegments.get(0).getPosition().y - 240/PX_TO_M_RATIO + getDeltaHeadY()) * SHEEP_CENTER_FORCE;
		// Debug.d("dy: " + dY);
		
		float dY = orientation_type * (float) Math.sqrt((double) force);
		
		bodySegments.get(0).applyForce(  bodySegments.get(0).getWorldVector(new Vector2(force * direction, -dY))  , new Vector2(bodySegments.get(0).getPosition().x + direction/2, bodySegments.get(0).getPosition().y));
	}
	
	public void jumpBackward() {
		int segment_idx = SEGMENT_COUNT - 1;
		float dY = (bodySegments.get(segment_idx).getPosition().y - 240/PX_TO_M_RATIO + getDeltaHeadY()) * SHEEP_CENTER_FORCE;
		bodySegments.get(segment_idx).applyForce(  bodySegments.get(segment_idx).getWorldVector(new Vector2(force * BACKWARD_FORCE * direction, -dY))  , new Vector2(bodySegments.get(segment_idx).getPosition().x + direction/2, bodySegments.get(segment_idx).getPosition().y));
	}
	
	private float getDeltaHeadY() {
		return ((float)Math.sin((double) bodySegments.get(0).getAngle())) * 0.5f * direction;
	}
	private float getDeltaTailY() {
		return ((float)Math.sin((double) bodySegments.get(SEGMENT_COUNT-1).getAngle())) * 0.5f;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public boolean isOut() {
		float sum = 0;
		for (int i = 0; i < SEGMENT_COUNT - 1; i++) {
			sum += segmentOut[i];
		}
		
		setCenter(sum);
		setAbsCenter(Math.abs(sum));
		
		if(absCenter >= 32){
			setOut(true);
		}
		else{
			setOut(false);
		}
		return this.out;
	}
	
	private void createIndicator(Scene pScene) {
		indicatorP1 = new Rectangle(getHeadPosX(), getHeadPosY(), 64, 4, ResourcesManager.getInstance().vbom);
		indicatorP1.setColor(0, .6f, 0);
		indicatorBackgroundP1 = new Rectangle(getHeadPosX(), getHeadPosY(), 64, 4, ResourcesManager.getInstance().vbom);
		indicatorBackgroundP1.setColor(0, 0, 0, .3f);

		ResourcesManager.getInstance().gamePlayIndicatorRegion.setTextureWidth(32);
		ResourcesManager.getInstance().gamePlayIndicatorRegion.setTextureX(16);
		
		rotateIndicator(90);
		
		pScene.attachChild(indicatorBackgroundP1);
		pScene.attachChild(indicatorP1);
	}
	
	private float getHeadPosX(){
		return spriteSegments.get(0).getX();// + bodySegments.get(0).getWorldCenter().x;
	}
	private float getHeadPosY(){
		return spriteSegments.get(0).getY();// + bodySegments.get(0).getWorldCenter().y;
	}
	
	private void rotateIndicator(int degree) {
		indicatorP1.setRotation(degree);
		indicatorBackgroundP1.setRotation(degree);
	}
}
