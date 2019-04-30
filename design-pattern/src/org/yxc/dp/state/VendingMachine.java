package org.yxc.dp.state;

public class VendingMachine {

    private State noMoneyState;
    private State hasMoneyState;
    private State soldState;
    private State soldOutState;
    private State winnerState;

    private State currentState;

    private int count;

    public VendingMachine(int count) {
        noMoneyState = new NoMoneyState(this);
        hasMoneyState = new HasMoneyState(this);
        soldState = new SoldState(this);
        soldOutState = new SoldOutState(this);
        winnerState = new WinnerState(this);

        if (count > 0) {
            this.count = count;
            currentState = noMoneyState;
        }
    }

    public State getHasMoneyState() {
        return hasMoneyState;
    }

    public State getNoMoneyState() {
        return noMoneyState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public int getCount() {
        return this.count;
    }

    public void insertMoney()
    {
        currentState.insertMoney();
    }

    public void backMoney()
    {
        currentState.backMoney();
    }

    public void turnCrank()
    {
        currentState.turnCrank();

        if (currentState == soldState || currentState == winnerState)
            currentState.dispense();
    }

    public void dispense() {
        System.out.println("发出一件商品...");

        if (count != 0) {
            count -= 1;
        }
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(10);
        machine.insertMoney();
        machine.backMoney();

        System.out.println("----我要中奖----");

        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();
        machine.insertMoney();
        machine.turnCrank();

        System.out.println("-------压力测试------");

        machine.insertMoney();
        machine.backMoney();
        machine.backMoney();
        machine.turnCrank();// 无效操作
        machine.turnCrank();// 无效操作
        machine.backMoney();
    }
}
