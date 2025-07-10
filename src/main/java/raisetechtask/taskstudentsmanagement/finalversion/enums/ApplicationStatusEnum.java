package raisetechtask.taskstudentsmanagement.finalversion.enums;

import lombok.Getter;

/**
 * Enum定数と、それに対応する表示用文字列（日本語名）との相互変換を行います。
 */
@Getter
public enum ApplicationStatusEnum {

	KARI_MOSIKOMI("仮申込"),
	HON_MOSIKOMI("本申込"),
	JUKOCHU("受講中"),
	JUKO_SHURYO("受講終了");

	/**
	 * Enum定数に対応する表示用文字列（日本語名）です。
	 * Lombokのgetterを付与することで（String displayValue）として表示用文字列を取得できます。
	 */
	private final String displayValue;

	ApplicationStatusEnum(String displayValue) {
		this.displayValue = displayValue;
	}

	/**
	 * リクエストされた文字列の申込状況をEnum定数へ変換を行います。
	 *
	 * @param displayValue リクエストされた文字列の申込状況
	 * @return Enum定数に変換された申込状況
	 * @throws IllegalArgumentException 対応する定数が見つからない場合エラーメッセージとリクエストされた文字列を伝播させます。
	 */
	public static ApplicationStatusEnum inputDisplayValue(String displayValue) {
		for(ApplicationStatusEnum statusEnum : ApplicationStatusEnum.values()) {
			if(statusEnum.displayValue.equals(displayValue)) {
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("存在しない定数です。" + displayValue);
	}
}
