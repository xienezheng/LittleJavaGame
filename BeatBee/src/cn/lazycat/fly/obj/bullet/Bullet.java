package cn.lazycat.fly.obj.bullet;

import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.FlyGame;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {

    private int speed = 3;  // 子弹的飞行速度

    public Bullet(int x, int y, BufferedImage image) {
        super.image = FlyGame.bullet;
        super.width = super.image.getWidth();
        super.height = super.image.getHeight();
        super.x = x;
        super.y = y;
        this.image = image;
    }

    @Override
    public void step() {
        super.y -= this.speed;
    }

}
