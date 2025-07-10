package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Studentsテーブルに紐づいたdateです。
 */
@Schema(description = "受講生")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Student {

	/**
	 * 受講生の一意な識別子です。データベースで自動採番されます。
	 */
	private int id;

	/**
	 * 受講生の氏名です。必須項目で、空白は許容されません。最大50文字。
	 */
	@NotBlank(message = "氏名は必須項目です。")
	@Size(max = 50, message = "氏名は50文字以内で入力してください。")
	private String fullName;

	/**
	 * 受講生の氏名のふりがなです。必須項目で、空白は許容されません。最大50文字。
	 */
	@NotBlank(message = "ふりがなは必須項目です。")
	@Size(max = 50, message = "ふりがなは50文字以内で入力してください。")
	@Pattern(regexp = "^[ぁ-んァ-ヶー・]*$", message = "ふりがなはひらがなまたはカタカナで入力してください。")
	private String furigana;

	/**
	 * 受講生のニックネームです。最大30文字。
	 */
	@Size(max = 30, message = "ニックネームは30文字以内で入力してください。")
	private String nickname;

	/**
	 * 受講生のメールアドレスです。必須項目で、有効なメールアドレス形式である必要があります。最大255文字。
	 */
	@NotBlank(message = "メールアドレスは必須項目です。")
	@Email(message = "有効なメールアドレス形式で入力してください。")
	private String emailAddress;

	/**
	 * 受講生の住所です。必須項目で、空白は許容されません。最大255文字。
	 */
	@NotBlank(message = "住所は必須項目です。")
	@Size(max = 255, message = "住所は255文字以内で入力してください。")
	private String address;

	/**
	 * 受講生の年齢です。必須項目で、0以上の数値である必要があります。
	 */
	@Min(value = 0, message = "年齢は0歳以上である必要があります。")
	private int age;

	/**
	 * 受講生の性別です。必須項目で、空白は許容されません。
	 */
	@NotBlank(message = "性別は必須項目です。")
	private String gender;

	/**
	 * 受講生に関する特記事項や備考です。最大500文字。
	 */
	@Size(max = 500, message = "備考は500文字以内で入力してください。")
	private String remark;

	/**
	 * 論理削除フラグ。trueの場合、受講生は削除済みとして扱われます。
	 */
	private boolean isDeleted;

}