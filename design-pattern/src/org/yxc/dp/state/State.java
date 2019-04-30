package org.yxc.dp.state;

public interface State {

    /**
     * 投币
     */
    void insertMoney();

    /**
     * 退币
     */
    void backMoney();

    /**
     * 转动手柄
     */
    void turnCrank();

    /**
     * 出货
     */
    void dispense();
}
