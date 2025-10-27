package racingcar;


import java.util.ArrayList;
import java.util.List;
import racingcar.domain.Car;
import racingcar.strategy.RandomMoveStrategy;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    private final List<Car> cars = new ArrayList<>();
    private final RandomMoveStrategy randomMoveStrategy = new RandomMoveStrategy();

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        setCarsNames();
        int round = setRoundTimes();

        OutputView.printResultMessage();
        repeatUntilRound(round);
        printWinners(cars);
    }

    private void setCarsNames() {
        String[] eachCarNames = splitCarNames();

        for (String name : eachCarNames) {
            String strippedName = validateCarName(name);
            createCar(strippedName);
        }
    }

    private String[] splitCarNames() {
        String readCarNames = InputView.inputCarNames();
        if (readCarNames.contains(",")) {
            return readCarNames.split(",");
        } else {
            throw new IllegalArgumentException("쉼표를 포함하여 경주할 자동차 이름을 두 대 이상 입력해주세요.");
        }
    }

    private String validateCarName(String name) {
        String strippedName = name.strip();
        if (strippedName.length() > 5) {
            throw new IllegalArgumentException("이름은 5자를 초과할 수 없습니다.");
        }
        if (strippedName.isBlank()) {
            throw new IllegalArgumentException("빈 이름은 입력할 수 없습니다. ");
        }
        return strippedName;
    }

    private void createCar(String strippedName) {
        Car car = new Car();
        car.setName(strippedName);
        cars.add(car);
    }

    private int setRoundTimes() {
        String readRoundCount = InputView.inputTryCount().strip();
        return validateRound(readRoundCount);
    }

    private int validateRound(String input) {
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

    private void repeatUntilRound(int round) {
        for (int i = 0; i < round; i++) {
            moveCarsOnce();
            OutputView.printRoundResult(cars);
        }
    }

    private void moveCarsOnce() {
        for (int carIndex = 0; carIndex < cars.size(); carIndex++) {
            if (randomMoveStrategy.movable()) {
                moveForward(carIndex);
            }
        }
    }

    private void moveForward(int carIndex) {
        cars.get(carIndex).setPosition(cars.get(carIndex).getPosition() + 1);
    }

    private void printWinners(List<Car> cars) {
        int maxPosition = getMaxPosition(cars);
        List<String> winnersNames = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPosition() == maxPosition) {
                winnersNames.add(car.getName());
            }
        }
        OutputView.printWinners(winnersNames);
    }

    private int getMaxPosition(List<Car> cars) {
        return cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElse(0);
    }
}
