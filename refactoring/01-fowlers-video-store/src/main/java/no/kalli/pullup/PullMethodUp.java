package no.kalli.pullup;

public class PullMethodUp {

    class A {

    }

    class B extends A {

        double res;

        double foo(int i) {
            res = i * Math.PI;
            return res;
        }
    }

    class C extends A {

        double res;

        double foo(int i) {
            res = i / Math.PI;
            return res;
        }
    }

}
