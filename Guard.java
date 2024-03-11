package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Guard {

    public Vector2 pos;
    public Sprite sprite = new Sprite();
    public int farsight;
    public float speed = 7; //guard to player speed coversion is 1 to 50, meaning guard.speed 1 == player.speed 50, g 6 == p 300, etc.
    public float rotation_speed = 1; //lower = faster
    public int rotation_index = 0;
    public boolean clockwise = true;
    public int dir=315; //angle facing
    public int dirl=15;
    public int dirr=255;
    public int rad;
    public int state = 0; //0=scan, 1=chase, 2=return

    public Guard(Vector2 position, int radius, int fs){

        farsight = fs;
        pos=position;
        rad=radius;
        sprite.setScale(3);

    }
    public boolean Update(float deltaTime,int overlap,Vector2 v, Vector2 dim, Vector2 ogp, Barrier b){

        if(state==0) {
            rotation_index++;
            if(dirl>90){clockwise=true;}if(dirr<180){clockwise=false;}
            if(rotation_index%rotation_speed==0){
                if(clockwise){dir--;dirl--;dirr--;dir%=360;dirl%=360;dirl%=360;}else{dir++;dirl++;dirr++;dir%=360;dirl%=360;dirl%=360;}
            }
            //if cond, state transition
            double rise = v.y-pos.y;
            double run = v.x-pos.x;
            double distance = Math.sqrt((rise*rise)+(run*run));
            double theta = 90;
            if(run>0){theta = Math.toDegrees(Math.atan(rise/run))%360;}
            if(run==0 && rise<0){theta+=180;}
            if(run<0){theta = Math.toDegrees((Math.atan(rise/run)))+180%360;}

            double greater=dir,lesser=theta;
            if(theta>dir){greater=theta; lesser=dir;}
            double angle1 = greater-lesser;
            double angle2 = lesser + (360-greater);

            if((angle1<60||angle2<60) && distance<rad+farsight && !b.intersection(v,pos)){
                state=1;
            }
            if(distance<rad){
                return true;
            }


        }

        if(state==1){



            double rise = v.y-pos.y;
            double run = v.x-pos.x;
            double distance = Math.sqrt((rise*rise)+(run*run));

            if(run>0&&overlap==1){
                run=0; rise=1;
            }
            else if(run<0&&overlap==3){
                run=0; rise=1;
            }
            else if(rise>0&&overlap==2){
                rise=0; run=1;
            }
            else if(rise<0&&overlap==4){
                rise=0; run=1;
            }else{
                rise /= distance; rise *= speed; run /= distance; run *= speed;
            }

            //double distance = Math.sqrt((rise*rise)+(run*run));
            //rise /= distance; run /= distance;
            pos = new Vector2((float) (pos.x+run),(float) (pos.y+rise));


            double theta = 90;
            if(run>0){theta = Math.toDegrees(Math.atan(rise/run))%360;}
            if(run==0 && rise<0){theta+=180;}
            if(run<0){theta = Math.toDegrees((Math.atan(rise/run)))+180%360;}
            dir = (int) theta; dirl=(dir+60)%360; dirr=(dir-60)%360;

            double greater=dir,lesser=theta;
            if(theta>dir){greater=theta; lesser=dir;}
            double angle1 = greater-lesser;
            double angle2 = lesser + (360-greater);

            if(!((angle1<60||angle2<60) && distance<rad+farsight) || b.intersection(v,pos)){
                state=2;
            }
            if(distance<rad){
                return true;
            }

        }
        if(state==2){

            double rise = ogp.y-pos.y;
            double run = ogp.x-pos.x;
            double distance = Math.sqrt((rise*rise)+(run*run));

            if(run>0&&overlap==1){
                run=0; rise=1;
            }
            else if(run<0&&overlap==3){
                run=0; rise=1;
            }
            else if(rise>0&&overlap==2){
                rise=0; run=1;
            }
            else if(rise<0&&overlap==4){
                rise=0; run=1;
            }else{
                rise /= distance; rise *= speed; run /= distance; run *= speed;
            }

            //double distance = Math.sqrt((rise*rise)+(run*run));
            //rise /= distance; run /= distance;
            pos = new Vector2((float) (pos.x+run),(float) (pos.y+rise));


            if(distance<5){
                state=0;
                dir=315;dirl=15;dirr=255;
            }else{

                double theta = 90;
                if(run>0){theta = Math.toDegrees(Math.atan(rise/run))%360;}
                if(run==0 && rise<0){theta+=180;}
                if(run<0){theta = Math.toDegrees((Math.atan(rise/run)))+180%360;}
                dir = (int) theta; dirl=(dir+60)%360; dirr=(dir-60)%360;

                rise = v.y-pos.y;
                run = v.x-pos.x;
                distance = Math.sqrt((rise*rise)+(run*run));
                rise /= distance; run /= distance;

                theta = 90;
                if(run>0){theta = Math.toDegrees(Math.atan(rise/run))%360;}
                if(run==0 && rise<0){theta+=180;}
                if(run<0){theta = Math.toDegrees((Math.atan(rise/run)))+180%360;}

                double greater=dir,lesser=theta;
                if(theta>dir){greater=theta; lesser=dir;}
                double angle1 = greater-lesser;
                double angle2 = lesser + (360-greater);

                if((angle1<60||angle2<60) && distance<rad+farsight && !b.intersection(v,pos)){
                    state=1;
                }
                if(distance<rad){
                    return true;
                }

            }

        }

        if(pos.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) pos.x = (sprite.getWidth()*sprite.getScaleX()/2);
        if(pos.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) pos.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(pos.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) pos.y = (sprite.getHeight()*sprite.getScaleY()/2);
        if(pos.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) pos.y = Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);



        return false;

    }
    public void Draw(SpriteBatch batch,int overlap,Vector2 v,Vector2 dim,Vector2 ogp, Barrier b){
        Update(Gdx.graphics.getDeltaTime(),overlap,v,dim,ogp,b);
        sprite.setPosition(pos.x, pos.y);
        //sprite.draw(batch);
    }

    public Vector2 endCoords(double x, double y, double length, double theta){

        // Convert angle from degrees to radians
        double angleInRadians = Math.toRadians(theta);

        // Calculate the displacement along x-axis and y-axis
        double deltaX = length * Math.cos(angleInRadians);
        double deltaY = length * Math.sin(angleInRadians);

        // Calculate the coordinates of the other endpoint
        float otherX = (float) (x + deltaX);
        float otherY = (float) (y + deltaY);

        return new Vector2(otherX,otherY);

    }


}

