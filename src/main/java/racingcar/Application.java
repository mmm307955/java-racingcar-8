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
            String strippedName = validateCarName(name);

            Car car = new Car();
            car.setName(strippedName);
            cars.add(car);
        }
    }

    public static String[] splitCarNames() {
        return Console.readLine().split(",");
    }

    public static String validateCarName(String name) {
        String strippedName = name.strip();
        if (strippedName.length() > 5) {
            throw new IllegalArgumentException("이름은 5자를 초과할 수 없습니다.");
        }
        if (strippedName.isBlank()) {
            throw new IllegalArgumentException("빈 이름은 입력할 수 없습니다. ");
        }
        return strippedName;
    }
}
