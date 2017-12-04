package org.example.groovy

/**
 * @DATE 2017/12/1 12:54
 * @author yangxiuchu
 */
class EmailDsl {
    String toText
    String fromText
    String body

    /**
     * This method accepts a closure which is essentially the DSL. Delegate the
     * closure methods to
     * the DSL class so the calls can be processed
     */

    def static make(closure) {
        EmailDsl emailDsl = new EmailDsl()
        // any method called in closure will be delegated to the EmailDsl class
        closure.delegate = emailDsl
        closure()
    }

    /**
     * Store the parameter as a variable and use it later to output a memo
     */

    def to(String toText) {
        this.toText = toText
    }

    def from(String fromText) {
        this.fromText = fromText
    }

    def body(String bodyText) {
        this.body = bodyText
    }
}

class Demo04 {

    static void main(String[] args) {
        /**
         * 使用接受闭包的静态方法。这是一个很麻烦的方式来实现DSL。
         * 在电子邮件示例中，类EmailDsl具有make方法。它创建一个实例，并将闭包中的所有调用委派给实例。这是一种机制，其中“to”和“from”节结束了EmailDsl类中的执行方法。
         * 一旦to（）方法被调用，我们将文本存储在实例中以便以后格式化。
         * 我们现在可以使用易于为最终用户理解的简单语言调用EmailDSL方法。
         */
        EmailDsl.make {
            to "Nirav Assar"
            from "Barack Obama"
            body "How are things? We are doing well. Take care"
        }
    }
}
