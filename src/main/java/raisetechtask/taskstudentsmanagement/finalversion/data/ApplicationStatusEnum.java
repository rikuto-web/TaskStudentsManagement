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

	public String getDisplayValue() {
		return displayValue;
	}
}
