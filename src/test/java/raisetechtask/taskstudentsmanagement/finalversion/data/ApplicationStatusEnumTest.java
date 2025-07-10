package raisetechtask.taskstudentsmanagement.finalversion.data;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationStatusEnumTest {

	@ParameterizedTest
	@MethodSource("provideEnumAndDisplayValue")
	void 定数で宣言されている申込状況が適切な表示値となっていること(
			ApplicationStatusEnum statusEnum, String expectedDisplayValue) {
		assertThat(statusEnum.getDisplayValue()).isEqualTo(expectedDisplayValue);
	}

	@ParameterizedTest
	@MethodSource("provideDisplayValueAndEnum")
	void 受け取った表示名がEnum定数に適切に変換されること(String displayValue, ApplicationStatusEnum expectedEnum) {
		assertThat(ApplicationStatusEnum.inputDisplayValue(displayValue)).isEqualTo(expectedEnum);
	}

	@ParameterizedTest
	@MethodSource("provideInvalidDisplayValues")
	void 特定の例外がスローされること(String invalidDisplayValue) {
		assertThatThrownBy(() -> ApplicationStatusEnum.inputDisplayValue(invalidDisplayValue))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("存在しない定数です。");
	}


	static Stream<Arguments> provideEnumAndDisplayValue() {
		return Stream.of(
				Arguments.of(ApplicationStatusEnum.KARI_MOSIKOMI, "仮申込"),
				Arguments.of(ApplicationStatusEnum.HON_MOSIKOMI, "本申込"),
				Arguments.of(ApplicationStatusEnum.JUKOCHU, "受講中"),
				Arguments.of(ApplicationStatusEnum.JUKO_SHURYO, "受講終了")
		);
	}

	static Stream<Arguments> provideDisplayValueAndEnum() {
		return Stream.of(
				Arguments.of("仮申込", ApplicationStatusEnum.KARI_MOSIKOMI),
				Arguments.of("本申込", ApplicationStatusEnum.HON_MOSIKOMI),
				Arguments.of("受講中", ApplicationStatusEnum.JUKOCHU),
				Arguments.of("受講終了", ApplicationStatusEnum.JUKO_SHURYO)
		);
	}

	static Stream<Arguments> provideInvalidDisplayValues() {
		return Stream.of(
				Arguments.of("存在しない値"),
				Arguments.of("invalid_status"),
				Arguments.of("")
		);
	}
}