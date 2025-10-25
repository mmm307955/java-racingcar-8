package racingcar;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Car;

public class Application {
    private static final List<Car> cars = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        setCarsNames();
    }

    public static void setCarsNames() {
        String[] eachCarNames = splitCarNames();

        for (String name : eachCarNames) {
            Car car = new Car();
            car.setName(name);
            cars.add(car);
        }
    }

    public static String[] splitCarNames() {
        return Console.readLine().split(",");
    }
}
