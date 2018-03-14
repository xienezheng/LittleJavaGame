package cn.lazycat.fly.obj.enemy;

import cn.lazycat.fly.flag.Award;
import cn.lazycat.fly.flag.Enemy;
import cn.lazycat.fly.flag.FlyingObject;
import cn.lazycat.fly.FlyGame;

import java.util.Random;

/**
 * 蜜蜂既是敌人又是奖励
 */
public class Bee extends FlyingObject implements Award, Enemy {

    // 蜜蜂移动是横向移动的，所以需要x和y的移动速度
    private int xSpeed = 2;
    private int ySpeed = 4;

    // 蜜蜂的奖励类型
    private int awardType;

    public Bee() {

        super.image = FlyGame.bee;
        super.width = super.image.getWidth();
        super.height = super.image.getHeight();
        Random random = new Random();
        super.x = random.nextInt(FlyGame.WIDTH - this.width);
        super.y = -this.height;
        // 蜜蜂的奖励类型是随机的
        this.awardType = random.nextInt(2);  // 0 或 1
        // 蜜蜂刚开始的方向是随机的
        int way = random.nextInt(2);
        if (way == 1) {
            xSpeed = -xSpeed;
        }
    }

    @Override
    public int getType() {
        return awardType;
    }

    @Override
    public void step() {
        x += xSpeed;
        y += ySpeed;
        if (x <= 0) {  // 此时到最左边了
            xSpeed = 1;
        }
        if (x >= FlyGame.WIDTH - this.width) {  // 此时到最右边了
            xSpeed = -1;
        }
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public boolean isDead() {
        return true;
    }

    @Override
    public int getHit() {
        return 2;
    }
}
