package no.kalli.pullup;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuperDuperTest {

    private final int X = 1;
    private SuperDuper omg;
    private Foo foo;
    private Bar bar;

    @Before
    public void setUp() throws Exception {
        omg = new SuperDuper();
        foo = new Foo();
        bar = new Bar();
    }

    @Test
    public void whatIsMagicXSuperDuper() {
        assertEquals(2, omg.magic(X));
    }

    @Test
    public void whatIsMagicXFoo() {
        assertEquals(3, foo.magic(X));
    }

    @Test
    public void whatIsMagicXBar() {
        assertEquals(4, bar.magic(X));
    }
}