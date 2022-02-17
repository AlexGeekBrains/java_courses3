package lessson7;

public class ForTest {

    @BeforeSuit
    public static void myTest1() {
        System.out.println("test 1");
    }

    @Test(priority = 10)
    public static void myTest2() {
        System.out.println("test 2");
    }

    @Test(priority = 9)
    public static void myTest3() {
        System.out.println("test 3");
    }

    @Test(priority = 8)
    private static void myTest4() {
        System.out.println("test 4");
    }

    @Test(priority = 7)
    public static void myTest5() {
        System.out.println("test 5");
    }

    @Test(priority = 6)
    public static void myTest6() {
        System.out.println("test 6");
    }

    @Test(priority = 5)
    public static void myTest7() {
        System.out.println("test 7");
    }

    @Test(priority = 4)
    public static void myTest8() {
        System.out.println("test 8");
    }

    @Test(priority = 3)
    private static void myTest9() {
        System.out.println("test 9");
    }

    @AfterSuit
    public static void myTest10() {
        System.out.println("test 10");
    }
}
