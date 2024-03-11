package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Sound;
import java.lang.Math;


public class MyGdxGame extends ApplicationAdapter {

    ShapeRenderer shape;
    //SpriteBatch batch;
    //Texture img_glass;
    //Texture img_solid;
    Player p;
    Guard g;
    Barrier b;
    Vector2 original_guard_position;
    Vector2 original_player_position;
    boolean game_over=false;
    int blink=0;



    public void create(){

        shape = new ShapeRenderer();
        //batch = new SpriteBatch();
        int radius = 10;
        int farsight = 150;
        b = new Barrier(new Vector2(300,150));
        original_guard_position = new Vector2(b.pos.x+b.dim.x+radius,b.pos.y-radius);
        original_player_position = new Vector2(b.pos.x-radius,b.pos.y+b.dim.y+radius);
        p = new Player(original_player_position,radius);
        g = new Guard(original_guard_position,radius,farsight);
    }


    public void render(){

        blink++;
        float deltaTime = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0,0,0,0);
        shape.begin(ShapeRenderer.ShapeType.Filled);

        if(g.state==1 && (game_over || blink%5==0)){

            shape.setColor(Color.RED);
            shape.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        }else if(game_over && g.state != 1){

            shape.setColor(Color.GREEN);
            shape.rect(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        }else{

            shape.setColor(Color.GREEN);
            shape.circle(p.pos.x,p.pos.y,p.rad);
            shape.setColor(Color.WHITE);
            shape.rect(b.pos.x,b.pos.y,b.dim.x,b.dim.y);
            shape.setColor(Color.RED);
            shape.circle(g.pos.x,g.pos.y,g.rad);
            //shape.line(new Vector2(g.pos.x,g.pos.y), endCoords(g.pos.x,g.pos.y,g.rad+g.farsight,g.dir));
            shape.line(new Vector2(g.pos.x,g.pos.y), endCoords(g.pos.x,g.pos.y,g.rad+g.farsight,g.dirl));
            shape.line(new Vector2(g.pos.x,g.pos.y), endCoords(g.pos.x,g.pos.y,g.rad+g.farsight,g.dirr));
            shape.end();
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.circle(g.pos.x,g.pos.y,g.rad+g.farsight);
            //batch.begin();
            p.Update(Gdx.graphics.getDeltaTime(),overlap(p,b));
            game_over = g.Update(Gdx.graphics.getDeltaTime(),overlap(g,b),p.pos, b.dim, original_guard_position,b);
        }
        //b.Draw(batch);









        shape.end();
        //batch.end();
    }

    public void dispose(){
        //batch.dispose();
        //img_glass.dispose();
    }

    public int overlap(Player p, Barrier b){

        if(p.pos.x>b.pos.x && p.pos.y>b.pos.y && p.pos.x<b.pos.x+b.dim.x && p.pos.y<b.pos.y+b.dim.y){
            if(p.pos.x-p.rad < b.pos.x){return 1;} //go west
            if(p.pos.y-p.rad < b.pos.y){return 2;} //go south
            if(p.pos.x+p.rad > b.pos.x+b.dim.x){return 3;} //go east
            if(p.pos.y+p.rad > b.pos.y+b.dim.y){return 4;} //go north
        }
        return 0;
    }

    public int overlap(Guard p, Barrier b){

        if(p.pos.x>b.pos.x && p.pos.y>b.pos.y && p.pos.x<b.pos.x+b.dim.x && p.pos.y<b.pos.y+b.dim.y){
            if(p.pos.x-p.rad < b.pos.x){return 1;} //go west
            if(p.pos.y-p.rad < b.pos.y){return 2;} //go south
            if(p.pos.x+p.rad > b.pos.x+b.dim.x){return 3;} //go east
            if(p.pos.y+p.rad > b.pos.y+b.dim.y){return 4;} //go north
        }
        return 0;
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


