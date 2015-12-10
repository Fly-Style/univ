/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;

/**
 *
 * @author MakeYouHappy
 */
public class SimpleSphere  {
       
       private Node node;
       private SphereCollisionShape collisionShape;
       private float mass;
       private float speed;
       public boolean collisionAction = false;
       
       public SimpleSphere (String nodeName, float mass, float speed, int xCoord) {
           node = new Node (nodeName);
           collisionShape = new SphereCollisionShape(.8f);
           node.addControl(new RigidBodyControl(collisionShape, mass));
           node.getControl(RigidBodyControl.class).setKinematic(true);
           node.move(xCoord, 0, 0);
           this.mass = mass;
       }
       
       public Node getNode () {
           return this.node;
       }
       
       public SphereCollisionShape getCollisonShape() {
           return this.collisionShape;
       }
       
       public float getMass () {
           return this.mass;
       }
       
       public float getSpeed() {
           return speed;
       }
       
       public float getAbsSpeed () {
           if (this.speed > 0)
               return this.speed;
           else return this.speed * (-1);
       }
       
       public boolean getSpeedSign () {
          if (this.speed > 0)
               return true;
           else return false;
       }
       
       public float setSpeed (float speed) {
           this.speed = speed;
           return this.speed;
       }
}
