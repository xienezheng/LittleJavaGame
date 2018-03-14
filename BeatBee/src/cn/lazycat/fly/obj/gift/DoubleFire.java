package cn.lazycat.fly.obj.gift;

import cn.lazycat.fly.FlyGame;
import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.flag.Gift;

import java.util.Random;

public class DoubleFire extends FlyingObject implements Gift {

    private int speed = 2;

    public DoubleFire() {
        super.image = FlyGame.doubleFire;
        this.width = image.getWidth();
        this.height = image.getHeight();
        Random random = new Random();
        super.x = random.nextInt(FlyGame.WIDTH - this.width);
        super.y = -this.height;
    }


    @Override
    public int getType() {
        return Gift.DOUBLR_FIRE;
    }

    @Override
    public void step() {
        super.y += speed;
    }
}
