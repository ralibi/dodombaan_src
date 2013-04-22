package com.ralibi.dodombaan.object;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.ralibi.dodombaan.manager.ResourcesManager;

public class Sheep {
	private final int SEGMENT_COUNT = 3;
	private final float PX_TO_M_RATIO = PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	private final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
	
	private final int LINEAR_DAMPING = 10;
	private final int ANGULAR_DAMPING = 10;
	private final int FORCE = 120;
	private final int ANGLE_LIMIT = 30;
	private final int ARENA_CENTER_FORCE = 3;
	private final int SHEEP_CENTER_FORCE = 10;
	
	private List<Body> bodySegments = new ArrayList<Body>();
	private List<Sprite> spriteSegments = new ArrayList<Sprite>();
	private int[] segmentOut;
	
	private boolean out = false;
	
	public float x = 0;
	public float y = 0;
	public int sheepIndex = 0;
	public int direction = 1;

	public float segmentWidth = 32f;
	public float segmentHeight = 32f;
	
	public Sheep(float pX, float pY, int sIdx, int dir){
		this.x = pX;
		this.y = pY;
		this.sheepIndex = sIdx;
		this.direction = dir;
	}
	
	public void createAndAttachSheep(Scene pScene, PhysicsWorld mPhysicsWorld, VertexBufferObjectManager vbom) {
		final Vector2[] vertices = getSegmentVertices();
		
		segmentOut = new int[SEGMENT_COUNT];
		// Segment 0 is the head
		for (int i = 0; i < SEGMENT_COUNT; i++) {
			final int itemI = i;
			Sprite sprite = new Sprite(this.x + (32*direction*i), 240, ResourcesManager.getInstance().gamePlaySheepSegmentRegions.get(sheepIndex), vbom){
				@Override
				protected void onManagedUpdate(final float pSecondsElapsed) {
					if(this.mY < 240 - 12 || this.mY > 240 + 12){
						segmentOut[itemI] = 1;
					}
					else{
						segmentOut[itemI] = -1;
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
					super.onManagedUpdate(pSecondsElapsed);
				}
			};
			Body body = PhysicsFactory.createPolygonBody(mPhysicsWorld, sprite, vertices, BodyType.DynamicBody, FIXTURE_DEF);
			body.setLinearDamping(LINEAR_DAMPING);
			body.setAngularDamping(ANGULAR_DAMPING);
			pScene.attachChild(sprite);
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));

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
		
		float dY = orientation_type * 5f;
		
		bodySegments.get(0).applyForce(  bodySegments.get(0).getWorldVector(new Vector2(FORCE * direction, -dY))  , new Vector2(bodySegments.get(0).getPosition().x + direction/2, bodySegments.get(0).getPosition().y));
	}
	
	public void jumpBackward() {
		int segment_idx = SEGMENT_COUNT - 1;
		float dY = (bodySegments.get(segment_idx).getPosition().y - 240/PX_TO_M_RATIO + getDeltaHeadY()) * SHEEP_CENTER_FORCE;
		bodySegments.get(segment_idx).applyForce(  bodySegments.get(segment_idx).getWorldVector(new Vector2(FORCE * -50 * direction, -dY))  , new Vector2(bodySegments.get(segment_idx).getPosition().x + direction/2, bodySegments.get(segment_idx).getPosition().y));
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
		int sum = 0;
		for (int i = 0; i < SEGMENT_COUNT; i++) {
			sum += segmentOut[i];
		}
		if(sum > 0){
			setOut(true);
		}
		else{
			setOut(false);
		}
		return this.out;
	}
}
