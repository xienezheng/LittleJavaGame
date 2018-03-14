package cn.lazycat.fly.obj.gift;

import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.flag.Gift;
import cn.lazycat.fly.FlyGame;

import java.util.Random;

/**
 * 加血道具
 */
public class Blood extends FlyingObject implements Gift {

    private int speed = 3;

    public Blood() {
        super.image = FlyGame.blood;
        this.width = image.getWidth();
        this.height = image.getHeight();
        Random random = new Random();
        super.x = random.nextInt(FlyGame.WIDTH - this.width);
        super.y = -this.height;

    }

    @Override
    public void step() {
        super.y += speed;
    }

    @Override
    public int getType() {
        return Gift.BLOOD;
    }
}
