package cn.lazycat.fly.flag;

/**
 * 代表敌人
 */
public interface Enemy {

    /**
     * 表示击落这个敌机得多少分
     * @return 得分
     */
    int getScore();

    /**
     * 判断这个敌机是否已经死亡了
     * @return 是否死亡
     */
    boolean isDead();

    /**
     * 返回敌机的伤害
     * @return 敌机伤害
     */
    int getHit();

}
