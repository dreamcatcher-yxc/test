package org.yxc.dp.state;

public class SoldOutState implements State {

    private VendingMachine machine;

    public SoldOutState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney() {
        System.out.println("投币失败, 商品已售馨");
    }

    @Override
    public void backMoney() {
        System.out.println("未投币");
    }

    @Override
    public void turnCrank() {
        System.out.println("商品已售馨");
    }

    @Override
    public void dispense() {
        throw new IllegalStateException("非法状态！");
    }
}
