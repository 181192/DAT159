package no.kalli.pullup;

public class SuperDuper {

    public int magic(int x) {
        return x + 1;
    }

}

class Super extends SuperDuper {
    @Override
    public int magic(int x) {
        return super.magic(x) + 2;
    }
}

class Foo extends Super {
    @Override
    public int magic(int x) {
        return super.magic(x) + 1;
    }
}

class Bar extends Super {

}