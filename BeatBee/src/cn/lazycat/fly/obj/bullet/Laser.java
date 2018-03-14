package cn.lazycat.fly.obj.bullet;

import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.flag.FlyingObject;

/**
 * 可以拐弯的激光
 */
public class Laser extends Bullet {


    public static final int RIGHT = 1;
    public static final int LEFT = 2;

    private int xSpeed = 1;
    private int ySpeed = 1;
    private int way;    // 表示向左还是向右

    public Laser(int way, int x, int y) {
        super(x, y, FlyGame.bossBullet2);
        this.way = way;
//        if (way == RIGHT) {
//            image = RotateImage.Rotate(image, 45);  // 向右旋转 45 度
//        } else {
//            image = RotateImage.Rotate(image, 315);  // 向左旋转 45 度
//        }
    }


    @Override
    public void step() {
        if (way == RIGHT) {
            x += xSpeed;
        } else {
            x -= xSpeed;
        }
        y -= ySpeed;
    }
}
