package raisetechtask.taskstudentsmanagement.finalversion.data;

public enum ApplicationStatusEnum {

	/* enumの場合,自動的に定数(=public static final)として扱われる
	命名規則はすべて大文字+スネークケース
	フィールドでの宣言
	*/
	KARI_MOSIKOMI("仮申込"),
	HON_MOSIKOMI("本申込"),
	JUKOCHU("受講中"),
	JUKO_SHURYO("受講終了");

	private final String displayValue;

	ApplicationStatusEnum(String displayValue) {
		this.displayValue = displayValue;
	}

	//定数から表示値を取得する操作
	public String getDisplayValue() {
		return displayValue;
	}

	//リクエストされた情報を定数に変換する操作
	public static ApplicationStatusEnum fromDisplayValue(String displayValue) {
		for ( ApplicationStatusEnum statusEnum : ApplicationStatusEnum.values() ) {
			if ( statusEnum.displayValue.equals(displayValue) ) {
				return statusEnum;
			}
		}
		throw new IllegalArgumentException("存在しない定数です。" + displayValue);
	}
}
