package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Car;

public class Application {
    private static final List<Car> cars = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        setCarsNames();

        System.out.println("시도할 횟수는 몇 회인가요?");
        int round = setRoundTimes();
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

    public static int setRoundTimes() {
        String input = Console.readLine().strip();
        return validateRound(input);
    }

    public static int validateRound(String input) {
        try {
            int round = Integer.parseInt(input);
            if (round < 0) {
                throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
            }
            System.out.println();
            return round;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("올바른 시도 횟수를 입력해주세요.(숫자만 입력 가능하며 2,147,483,647 이하의 숫자만 허용됩니다.)");
        }
    }

    public static void moveCarsOnce() {
        for (int carIndex = 0; carIndex < cars.size(); carIndex++) {
            if (isOverFour()) {
                moveForward(carIndex);
            }
        }
    }

    public static boolean isOverFour() {
        return Randoms.pickNumberInRange(0, 9) >= 4;
    }

    public static void moveForward(int carIndex) {
        cars.get(carIndex).setPosition(cars.get(carIndex).getPosition() + 1);
    }
}
