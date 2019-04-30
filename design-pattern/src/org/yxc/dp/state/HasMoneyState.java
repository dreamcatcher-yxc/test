package org.yxc.dp.state;

import java.util.Random;

/**
 * 已投币状态
 */
public class HasMoneyState implements State {

    private VendingMachine machine;

    private Random random;

    public HasMoneyState(VendingMachine machine) {
        this.machine = machine;
        this.random = new Random();
    }

    @Override
    public void insertMoney() {
        System.out.println("你已经投过硬币了, 无需再投币");
    }

    @Override
    public void backMoney() {
        System.out.println("退币成功");
        machine.setCurrentState(machine.getNoMoneyState());
    }

    @Override
    public void turnCrank() {
        System.out.println("你转动了手柄");
        int winner = random.nextInt(10);

        if(winner == 1 && machine.getCount() > 1) {
            machine.setCurrentState(machine.getWinnerState());
        } else {
            machine.setCurrentState(machine.getSoldState());
        }
    }

    @Override
    public void dispense() {
        throw new IllegalStateException("非法状态！");
    }
}
