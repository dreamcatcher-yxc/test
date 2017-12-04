//package org.example.groovy
//
///**
// * @DATE 2017/11/30 15:17
// * @author yangxiuchu
// */
//
//interface Total {
//    void displayTotal();
//}
//
//trait Marks implements Total {
//    void displayMarks() {
//        println 'Display Marks';
//    }
//
//    void displayTotal() {
//        println 'Display Total';
//    }
//}
//
//class MarksImpl implements Marks {
//    int StudentID
//    int Marks1;
//}
//
//class MarkImpl2 implements Total, Marks {
//
//}
//
//class Demo02 {
//
//    static void normalClassTest() {
//        Student student = new Student();
//        student.setUsername('zhangsan');
//        student.setAge(20);
//        student.setGender('male');
//        student.setEmail('zhangsan@sina.com');
//        student.setAddress('china');
//        println student;
//    }
//
//    static void abstractClassTest() {
//        Animal dog = new Dog('dog');
//        Animal pig = new Pig('pig');
//        println dog.getType();
//        println pig.getType();
//    }
//
//    static void traitTest() {
//        println new MarksImpl().displayMarks();
//    }
//
//    static void main(String[] args) {
////       abstractClassTest();
////        traitTest();
//        MarkImpl2 imp2 = new MarkImpl2();
//        imp2.displayTotal();
//        imp2.displayMarks();
//    }
//}
