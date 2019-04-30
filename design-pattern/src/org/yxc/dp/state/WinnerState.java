package org.yxc.dp.state;

public class WinnerState implements State {

    private VendingMachine machine;

    public WinnerState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertMoney() {
        System.out.println("非法操作");
    }

    @Override
    public void backMoney() {
        System.out.println("非法操作");
    }

    @Override
    public void turnCrank() {
        System.out.println("非法操作");
    }

    @Override
    public void dispense() {
        System.out.println("你中奖了，恭喜你，将得到2件商品");
        machine.dispense();

        if (machine.getCount() == 0) {
            System.out.println("商品已经售罄");
            machine.setCurrentState(machine.getSoldOutState());
        } else {
            machine.dispense();

            if (machine.getCount() > 0) {
                machine.setCurrentState(machine.getNoMoneyState());
            } else {
                System.out.println("商品已经售罄");
                machine.setCurrentState(machine.getSoldOutState());
            }
        }
    }
}
