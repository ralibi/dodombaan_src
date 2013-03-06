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
	private final int LINEAR_DAMPING = 2;
	private final int FORCE = 75;
	
	private List<Body> bodySegments = new ArrayList<Body>();
	private List<Sprite> spriteSegments = new ArrayList<Sprite>();
	
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
		
		for (int i = 0; i < SEGMENT_COUNT; i++) {
			Sprite sprite = new Sprite(this.x, this.y, ResourcesManager.getInstance().gamePlaySheepSegmentRegions.get(sheepIndex), vbom);
			Body body = PhysicsFactory.createPolygonBody(mPhysicsWorld, sprite, vertices, BodyType.DynamicBody, FIXTURE_DEF);
			body.setLinearDamping(LINEAR_DAMPING);
			pScene.attachChild(sprite);			
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));

			spriteSegments.add(sprite);
			bodySegments.add(body);
		}

		for (int i = 0; i < SEGMENT_COUNT - 1; i++) {

			final float halfWidth = segmentWidth/PX_TO_M_RATIO/2f;
			
			final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
			revoluteJointDef.initialize(bodySegments.get(i), bodySegments.get(i+1), bodySegments.get(i).getWorldCenter());
			revoluteJointDef.enableMotor = false;
			revoluteJointDef.localAnchorA.set(-halfWidth * direction, 0f);
			revoluteJointDef.localAnchorB.set(halfWidth * direction, 0f);
			revoluteJointDef.enableLimit = true;
			revoluteJointDef.lowerAngle = MathUtils.degToRad(-45);
			revoluteJointDef.upperAngle = MathUtils.degToRad(45);

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

	public void moveForward() {
		bodySegments.get(0).applyForce(new Vector2(FORCE * direction, 0), bodySegments.get(0).getPosition());
	}
}
