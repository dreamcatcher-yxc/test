package com.example.java.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class ProxyTests {

    interface Action {
        void doSomething();
    }

    static class Executor implements Action {

        @Override
        public void doSomething() {
            System.out.println("executor do action...");
        }
    }

    static class ActionProxy implements InvocationHandler {

        private Object actionImpl;

        public ActionProxy(Object actionImpl) {
            this.actionImpl = actionImpl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before action execute...");
            Object result = method.invoke(actionImpl, args);
            System.out.println("after action execute...");
            return result;
        }
    }

    public static void main(String[] args) {
        ActionProxy actionProxy = new ActionProxy(new Executor());
        Action action = (Action) Proxy.newProxyInstance(ProxyTests.class.getClassLoader(), Executor.class.getInterfaces(), actionProxy);
        action.doSomething();
    }
}
