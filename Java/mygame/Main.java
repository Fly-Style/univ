package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.PhysicsSweepTestResult;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Transform;
import com.jme3.scene.Node;
import java.util.List;

/**
 *
 * A spatial moves and sweeps its next movement for obstacles before moving
 * there Run this example with Vsync enabled
 */

public class Main extends SimpleApplication 
{
    
    private SimpleSphere capsule;
    private SimpleSphere spectator;
    private SimpleSphere comentator;

    private BulletAppState bulletAppState = new BulletAppState();

    
    private float bigMass = 3.0f;
    private float smallMass = 1.0f;
    
    private float bigBodySpeed = 2.0f;
    private float littleBodySpeed = 0.0f;
    
    protected float dist = 0.1f;
    
    protected boolean wasCollided = false;
    
    private float getAbs (float digit) {
        if (digit > 0)
            return digit;
        else return digit * (-1);
    }
    
    private void MovingListener (SimpleSphere sphere, float tpf) {
        
        if(sphere.collisionAction) {            
            sphere.getNode().move(sphere.getSpeed() * tpf, 0, 0);
            if (sphere.getSpeedSign())
                sphere.setSpeed(sphere.getSpeed() - (tpf/3));
            else sphere.setSpeed(sphere.getSpeed() + (tpf/3));
            
            if (getAbs(sphere.getSpeed()) < tpf) {
                sphere.collisionAction = false;
                sphere.setSpeed(0.0f);
            }
        }
    }
    
    
    public static void main(String[] args) {
        new Main().start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(bulletAppState);
        
        capsule =      new SimpleSphere("capsule", bigMass, bigBodySpeed, -2);
        spectator =    new SimpleSphere("spectator", smallMass, littleBodySpeed, -6);
        comentator =   new SimpleSphere("comentator", smallMass, littleBodySpeed, 2);
        

        bulletAppState.getPhysicsSpace().add(capsule.getNode());
        rootNode.attachChild(capsule.getNode());

        bulletAppState.getPhysicsSpace().add(spectator.getNode());
        rootNode.attachChild(spectator.getNode());
        
        bulletAppState.getPhysicsSpace().add(comentator.getNode());
        rootNode.attachChild(comentator.getNode());
       
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        float move;
        if (!wasCollided)
            move = tpf * 1;
        else move = -tpf;      
        
        /*
         * v1` = ((m1 - m2)*v1 + 2*m2*u2) / (m1 + m2)
         * v2` = ((m1 - m2)*v2 + 2*m1*u1) / (m1 + m2)
         */
        
        List<PhysicsSweepTestResult> sweepTest = bulletAppState.getPhysicsSpace().
                sweepTest(capsule.getCollisonShape(), new Transform(capsule.getNode().getWorldTranslation()), new Transform(capsule.getNode().getWorldTranslation().add(dist, 0, 0)));
        
        System.out.println(sweepTest.size());

        if (sweepTest.size() > 0) {
            PhysicsSweepTestResult get = sweepTest.get(0);
            if (get.getCollisionObject().getUserObject().equals(capsule.getNode())) {
                System.out.println("boo");
                sweepTest.clear();
            } 
            
            else if (get.getCollisionObject().getUserObject().equals(spectator.getNode())) {
                wasCollided = false;
                System.out.println(get.getCollisionObject().getUserObject().toString());
                fpsText.setText("Almost colliding with " + get.getCollisionObject().getUserObject().toString());
                sweepTest.clear();
                capsule.getNode().move(move, 0, 0);
                float thisMass = spectator.getMass();   
                float otherMass = capsule.getMass();
                // We know that the sphere which collided with our moving body, always is in calm.                 * 

                spectator.setSpeed(-((2* otherMass * bigBodySpeed ) / (thisMass + otherMass)) / 2);
                spectator.collisionAction = true;
                
            }
            
            else if (get.getCollisionObject().getUserObject().equals(comentator.getNode())) {
                wasCollided = true;
                System.out.println(get.getCollisionObject().getUserObject().toString());
                fpsText.setText("Almost colliding with " + get.getCollisionObject().getUserObject().toString());
                sweepTest.clear();
                capsule.getNode().move(-move, 0, 0);
                float thisMass = comentator.getMass();    // reflexion example;
                float otherMass = capsule.getMass();
                    /*
                 *  We know that the sphere which collided with our moving body, always is in calm.                 * 
                    */
                comentator.setSpeed(((2* otherMass * bigBodySpeed ) / (thisMass + otherMass)) / 2);
                comentator.collisionAction = true;
          
            }
        } 
        else {
              // if the sweep is clear then move the spatial
              capsule.getNode().move(move * bigBodySpeed, 0, 0);
        }
        
        MovingListener(spectator,  tpf);
        MovingListener(comentator, tpf);
        
        System.out.println(spectator.getSpeed() + ", " + comentator.getSpeed());
                          
    }
}
