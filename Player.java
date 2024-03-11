package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player {

    public Vector2 pos;
    public Sprite sprite = new Sprite();
    public float speed = 300;
    public int rad;
    public int dir = 90;

    public Player(Vector2 position, int radius){

        pos=position;
        rad=radius;
        sprite.setScale(3);

    }
    public void Update(float deltaTime,int overlap){

        if(Gdx.input.isKeyPressed(Keys.A) && overlap!=3) pos.x-=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.D) && overlap!=1) pos.x+=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.S) && overlap!=4) pos.y-=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.W) && overlap!=2) pos.y+=deltaTime*speed;

        if(pos.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) pos.x = (sprite.getWidth()*sprite.getScaleX()/2);
        if(pos.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) pos.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(pos.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) pos.y = (sprite.getHeight()*sprite.getScaleY()/2);
        if(pos.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) pos.y = Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);

    }
    public void Draw(SpriteBatch batch,int overlap){
        Update(Gdx.graphics.getDeltaTime(),overlap);
        sprite.setPosition(pos.x, pos.y);
        //sprite.draw(batch);
    }
}
