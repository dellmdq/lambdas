package com.dellmdq;

import javax.swing.plaf.TableHeaderUI;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //new Thread(() -> System.out.println("Printing from the Runnable")).start();
//        new Thread(() -> {
//            System.out.println("Printing from the Runnable");
//            System.out.println("Line 2");
//            System.out.format("This is line %d\n", 3);
//        }).start();

        /*Lambda expressions have an parameter, arrow, and body in its structure.*/

        Employee john = new Employee("John Doe", 30);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee jack = new Employee("Jack Hill", 40);
        Employee snow = new Employee("Snow White", 22);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(john);
        employeeList.add(tim);
        employeeList.add(jack);
        employeeList.add(snow);

        /*This can be done using a lambda expression.
        * */
//        Collections.sort(employeeList, new Comparator<Employee>() {
//            @Override
//            public int compare(Employee employee1, Employee employee2) {
//                return employee1.getName().compareTo(employee2.getName());
//            }
//        });

        /*We can use the lambda implementation even tought we have two methods to implement,
        * cause the equals method will always have a default implementation inherited from the
        * Object class from which all Object extend. (Collections)*/


        //parameters (compiler infers the type) and arrow
        Collections.sort(employeeList, (employee1, employee2) ->
                //body (compiler infers the return type)
                employee1.getName().compareTo(employee2.getName())
        );

        for(Employee employee : employeeList){
            System.out.println(employee.getName());
        }

/**Demo of applying an annonymous class to override an interface with a simple method.
 * We can do the same using a lambda expression. Cause we are implementing an interface
 * that has only one method to override. (CONTINUES BELOW) */
//        String sillyString = doStringStuff(new UpperConcat() {
//            @Override
//            public String upperAndConcat(String s1, String s2) {
//                return s1.toUpperCase() + s2.toUpperCase();
//            }
//        }, employeeList.get(0).getName(), employeeList.get(1).getName());
//        System.out.println(sillyString);

        /**To do this we have to define the method that we must override with a lambda*/
        UpperConcat uc = (s1, s2) -> s1.toUpperCase() + s2.toUpperCase();
        String sillyString = doStringStuff(uc, employeeList.get(0).getName(), employeeList.get(1).getName());

        AnotherClass anotherClass = new AnotherClass();
        String s = anotherClass.doSomething();
        System.out.println(s);

        String sLambda = anotherClass.doSomethingLambdaStyle();
        System.out.println(sLambda);

        System.out.println("Scope and functional programming");
        for(Employee employee : employeeList){
            System.out.println(employee.getName());
            System.out.println(employee.getAge());
            new Thread(() -> System.out.println(employee.getAge())).start();
        }
        /*Wait! The employee is OUTSIDE the lambda block, how is it that we can use it??
        * With each iteration of the for loop a new Employee with the index respective data is created.
        * So that Object is EFFECTIVELY FINAL and that's why we can use it on the lambda expression  */
//        System.out.println("Example with a old for loop and a thread that shows the employee's age");
//        for(int i = 0; i < employeeList.size(); i++){
//            Employee employee = employeeList.get(i);
//            System.out.println(employee.getName());
//            new Thread( () -> System.out.println(employee.getAge())).start();
//        }
        System.out.println("Functional foreach (with lambda)");
        employeeList.forEach(employee -> {
            System.out.println(employee.getName());
            System.out.println(employee.getAge());
        });


    }

    public final static String doStringStuff(UpperConcat uc, String s1, String s2) {
        return uc.upperAndConcat(s1, s2);
    }



}

class CodeToRun implements Runnable{

    @Override
    public void run() {
        System.out.println("Printing from the Runnable");
    }
}

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

interface UpperConcat{
    public String upperAndConcat(String s1, String s2);
}

class AnotherClass {

    public String doSomething(){
        System.out.println("The AnotherClass class's name is: " + getClass().getSimpleName());
        return Main.doStringStuff(new UpperConcat() {
            @Override
            public String upperAndConcat(String s1, String s2) {
                System.out.println("The anonymous class's name is: " + getClass().getSimpleName());//is nothing, it hasn't got any name
                return s1.toUpperCase()+s2.toUpperCase();
            }
        }, "String1", "String2");
    }

    /**Now we use a lambda expression instead of the anonymous class*/
    public String doSomethingLambdaStyle() {
        //Interface method definition using lambda expression. LAMBDA EXPRESSIONS ARE TREATED LIKE NESTED BLOCKS
        UpperConcat uc = (s1, s2 ) -> {
            System.out.println("The lambda expression's is " + getClass().getSimpleName());
            String result = s1.toUpperCase() + s2.toUpperCase();
            return result;
        };
        System.out.println("The AnotherClass class's name is " + getClass().getSimpleName());
        return  Main.doStringStuff(uc, "String1", "String2");
    }
}