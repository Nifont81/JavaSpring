package ru.geekbrains.TestLambda;

@FunctionalInterface
interface ISum {
    public int sum(int a, int b);
}

@FunctionalInterface
interface ISqrt {
    double sqrt(double x);
}

public class Test {
    public static void main(String[] args) {
        ISum summer = new ISum() {
            @Override
            public int sum(int a, int b) {
                return a + b;
            }
        };

        ISum summer2 = (a, b) -> {
            if (a < b) return b - a;
            return a - b;
        };

        System.out.println(summer2.sum(3, 8));

        ISqrt sqrt = x -> Math.sqrt(x);
        System.out.println(sqrt.sqrt(9));

        ISqrt sqrt1 = Math::sqrt;

        System.out.println(sqrt1.sqrt(16));

    }

}
