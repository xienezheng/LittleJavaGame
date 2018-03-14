package cn.lazycat.fly.flag;

import cn.lazycat.fly.FlyGame;


/**
 * 代表所有Boss的接口
 */
public abstract class Boss extends FlyingObject
        implements Enemy, Award, ShootEnemy {

    protected int speed = 1;
    protected int health;
    protected int bossLevel;
    protected boolean move = true;

    protected Boss() {
        super.x = FlyGame.WIDTH / 2 - width;  // 从中间入场
        super.y = 0;
    }



    @Override
    public boolean isDead() {
        --health;
        return health <= 0;
    }

    @Override
    public void step() {
        if (super.y < 15) {  // 向下入场
            super.y += this.speed;
        } else { // 左右摇摆
            if (move) {
                x += speed;
                if (x <= 0) {  // 此时到最左边了
                    speed = 1;
                }
                if (x >= FlyGame.WIDTH - this.width) {  // 此时到最右边了
                    speed = -1;
                }
            }

        }

    }

    public int getHealth() {
        return health;
    }
}
