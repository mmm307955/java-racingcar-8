package racingcar;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.domain.Car;
import racingcar.strategy.AlwaysMoveStrategy;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 기능_테스트() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,woni", "1");
                    assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
                },
                MOVING_FORWARD, STOP
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

    @DisplayName("각 자동차에 이름을 부여할 수 있다")
    @Test
    void 각_자동차에_이름을_부여할_수_있다() {
        Car car = new Car();
        car.setName("pobi");
        assertThat(car.getName()).isEqualTo("pobi");
    }

    @DisplayName("이름이 5글자 이상이면 예외를 발생시킨다")
    @Test
    void 이름이_5글자_이상이면_예외를_발생시킨다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("주어진 횟수 동안 n대의 자동차는 전진 또는 멈출 수 있다")
    @ParameterizedTest
    @ValueSource(ints = {5})
    void 주어진_횟수_동안_n대의_자동차는_전진_또는_멈출_수_있다(int round) {
        // given
        AlwaysMoveStrategy alwaysMoveStrategy = new AlwaysMoveStrategy();

        Car pobi = new Car();
        pobi.setName("pobi");

        Car java = new Car();
        java.setName("java");

        Car woni = new Car();
        woni.setName("woni");

        List<Car> cars = List.of(pobi, java, woni);

        // when
        for (int i = 0; i < round; i++) {
            for (Car car : cars) {
                if (alwaysMoveStrategy.movable()) {
                    car.setPosition(car.getPosition() + 1);
                }
            }
        }

        // then
        for (Car car : cars) {
            assertThat(car.getPosition()).isEqualTo(round);
        }
    }

    @DisplayName("우승자가 여러 명일 경우 쉼표(,)를 이용하여 구분한다")
    @Test
    void 우승자가_여러_명일_경우_쉼표를_이용하여_구분한다() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("pobi,java,woni", "1");
                    assertThat(output()).contains(
                            "pobi : -",
                            "java : -",
                            "woni : -",
                            "최종 우승자 : pobi, java, woni"
                    );
                },
                MOVING_FORWARD, MOVING_FORWARD, MOVING_FORWARD
        );
    }

    @DisplayName("시도 횟수가 올바르지 않으면 예외를 발생시킨다")
    @Test
    void 시도_횟수가_올바르지_않으면_예외를_발생시킨다() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,java", "다섯번"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
