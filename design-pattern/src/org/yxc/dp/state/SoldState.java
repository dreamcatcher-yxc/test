package org.yxc.dp.state;

public class SoldState implements State {

    private VendingMachine machine;

    public SoldState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney() {
        System.out.println("正在出货");
    }

    @Override
    public void backMoney() {
        System.out.println("正在出货, 无法退币");
    }

    @Override
    public void turnCrank() {
        System.out.println("正在出货, 请勿重复转动手柄");
    }

    @Override
    public void dispense() {
        machine.dispense();

        if(machine.getCount() > 0) {
            machine.setCurrentState(machine.getNoMoneyState());
        } else {
            System.out.println("商品已售馨");
            machine.setCurrentState(machine.getSoldOutState());
        }
    }
}
