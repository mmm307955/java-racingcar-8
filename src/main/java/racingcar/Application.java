package racingcar;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Car;
import racingcar.strategy.RandomMoveStrategy;

public class Application {
    private static final List<Car> cars = new ArrayList<>();
    private static final RandomMoveStrategy randomMoveStrategy = new RandomMoveStrategy();

    public static void main(String[] args) {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        setCarsNames();

        System.out.println("시도할 횟수는 몇 회인가요?");
        int round = setRoundTimes();

        System.out.println("실행 결과");
        repeatUntilRound(round);
        printWinners(cars);
    }

    public static void setCarsNames() {
        String[] eachCarNames = splitCarNames();

        for (String name : eachCarNames) {
            String strippedName = validateCarName(name);
            createCar(strippedName);
        }
    }

    public static String[] splitCarNames() {
        String readCarNames = Console.readLine();
        if (readCarNames.contains(",")) {
            return readCarNames.split(",");
        } else {
            throw new IllegalArgumentException("쉼표를 포함하여 경주할 자동차 이름을 두 대 이상 입력해주세요.");
        }
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

    public static void createCar(String strippedName) {
        Car car = new Car();
        car.setName(strippedName);
        cars.add(car);
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

    public static void repeatUntilRound(int round) {
        for (int i = 0; i < round; i++) {
            moveCarsOnce();
            printRoundResult();
        }
    }

    public static void moveCarsOnce() {
        for (int carIndex = 0; carIndex < cars.size(); carIndex++) {
            if (randomMoveStrategy.movable()) {
                moveForward(carIndex);
            }
        }
    }

    public static void printRoundResult() {
        for (int carIndex = 0; carIndex < cars.size(); carIndex++) {
            printNowPosition(carIndex);
        }
        System.out.println();
    }

    public static void moveForward(int carIndex) {
        cars.get(carIndex).setPosition(cars.get(carIndex).getPosition() + 1);
    }

    public static void printNowPosition(int carIndex) {
        System.out.println(cars.get(carIndex).getName() + " : " + "-".repeat(cars.get(carIndex).getPosition()));
    }

    public static void printWinners(List<Car> cars) {
        int maxPosition = getMaxPosition(cars);
        List<String> winnersNames = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPosition() == maxPosition) {
                winnersNames.add(car.getName());
            }
        }
        System.out.println("최종 우승자 : " + String.join(", ", winnersNames));
    }

    public static int getMaxPosition(List<Car> cars) {
        return cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElse(0);
    }
}
