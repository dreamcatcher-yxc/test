package org.yxc.dp.state;

/**
 * 未投币状态
 */
public class NoMoneyState implements State {

    private VendingMachine machine;

    public NoMoneyState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney() {
        System.out.println("投币投币");
        machine.setCurrentState(machine.getHasMoneyState());
    }

    @Override
    public void backMoney() {
        System.out.println("未投币, 不能退钱!");
    }

    @Override
    public void turnCrank() {
        System.out.println("未投币, 不能转动手臂!");
    }

    @Override
    public void dispense() {
        throw new IllegalStateException("非法状态!");
    }
}
