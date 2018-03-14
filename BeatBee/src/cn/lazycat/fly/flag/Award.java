package cn.lazycat.fly.flag;

/**
 * 奖励接口<br>
 * 这个奖励指的是击中后得到的奖励
 */
public interface Award {

    int DOUBLE_FIRE = 0;   // 双倍火力
    int LIFE = 1;          // +英雄机生命
    int SPEED_SLOW = 2;         // +子弹速度（低）
    int SPEED_MID = 3;          // +子弹速度（中）
    int SPEED_HIGH = 4;         // +子弹速度（高）

    /**
     * 获取奖励类型
     * @return 由上面定义的奖励类型(0 or 1)
     */
    int getType();

}
