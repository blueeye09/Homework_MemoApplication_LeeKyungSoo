package kr.co.lion.homework_memoapplication_leekyungsoo

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.homework_memoapplication_leekyungsoo.databinding.ActivityModifyBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ModifyActivity : AppCompatActivity() {

    lateinit var activityModifyBinding: ActivityModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityModifyBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(activityModifyBinding.root)

        setToolbar()
        initView()
    }

    // 툴바 설정
    fun setToolbar(){
        activityModifyBinding.apply {
            toolbarModify.apply {
                title = "메모 수정"

                // Back 버튼 설정
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                // 메뉴
                inflateMenu(R.menu.menu_modify)
                // 완료 버튼 클릭
                setOnMenuItemClickListener {
                    checkInput()
                    true
                }
            }
        }
    }

    // 초기에 제목, 내용을 가져와서 설정
    fun initView(){
        activityModifyBinding.apply {
            // 순서 값 추출
            val position = intent.getIntExtra("position", 0)
            // position번째 Memo 추출
            val memo = Util.memoList[position]

            textFieldModifyTitle.setText(memo.title)
            textFieldModifyContent.setText(memo.content)

        }
    }

    // 수정 처리
    fun modifyData(){
        // 위치값을 가져온다.
        val position = intent.getIntExtra("position", 0)
        // position번째 객체를 가져온다.
        val memo = Util.memoList[position]

        activityModifyBinding.apply {
            memo.title = textFieldModifyTitle.text.toString()
            memo.content = textFieldModifyContent.text.toString()
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            memo.date = dateFormat.format(calendar.time)
        }
    }

    // 입력 유효성 검사
    fun checkInput(){
        activityModifyBinding.apply {
            val title = textFieldModifyTitle.text.toString()
            if (title.trim().isEmpty()) {
                Util.showInfoDialog(this@ModifyActivity, "제목 입력 오류", "제목을 입력해주세요"){ _: DialogInterface, _: Int ->
                    Util.showSoftInput(textFieldModifyTitle, this@ModifyActivity)
                }
                return
            }
            val content = textFieldModifyContent.text.toString()
            if (content.trim().isEmpty()) {
                Util.showInfoDialog(this@ModifyActivity, "내용 입력 오류", "내용을 입력해주세요"){ _: DialogInterface, _: Int ->
                    Util.showSoftInput(textFieldModifyContent, this@ModifyActivity)
                }
                return
            }
        }
        modifyData()
        finish()
    }
}