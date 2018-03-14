package cn.lazycat.fly.obj.gift;

import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.flag.Gift;

import java.util.Random;

/**
 * 炸弹用于清除屏幕上的所有敌人(不包括BOSS)
 */
public class Bomb extends FlyingObject implements Gift {

    private int speed = 3;

    public Bomb() {
        super.image = FlyGame.bomb;
        this.width = image.getWidth();
        this.height = image.getHeight();
        Random random = new Random();
        super.x = random.nextInt(FlyGame.WIDTH - this.width);
        super.y = -this.height;
    }

    @Override
    public int getType() {
        return Gift.CLEAR;
    }

    @Override
    public void step() {
        super.y += speed;
    }
}
