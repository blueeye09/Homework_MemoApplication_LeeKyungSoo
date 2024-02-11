package kr.co.lion.homework_memoapplication_leekyungsoo

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.lion.homework_memoapplication_leekyungsoo.databinding.ActivityWriteBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WriteActivity : AppCompatActivity() {

    lateinit var activityWriteBinding: ActivityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityWriteBinding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(activityWriteBinding.root)

        setToolbar()
        initView()
    }

    // 툴바 설정
    fun setToolbar(){
        activityWriteBinding.apply {
            toolbarWrite.apply {
                // 제목
                title = "메모 작성"

                // Back 버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                // 메뉴
                inflateMenu(R.menu.menu_write)
                setOnMenuItemClickListener {
                    checkInput()
                    true
                }
            }
        }
    }

    // title에 키보드가 올라오도록 설정
    fun initView(){
        Util.showSoftInput(activityWriteBinding.textFieldInputTitle, this@WriteActivity)
    }

    // 입력 유효성 검사
    fun checkInput(){
        activityWriteBinding.apply {
            val title = textFieldInputTitle.text.toString()
            if (title.trim().isEmpty()) {
                Util.showInfoDialog(this@WriteActivity, "제목 입력 오류", "제목을 입력해주세요"){ _: DialogInterface, _: Int ->
                    Util.showSoftInput(textFieldInputTitle, this@WriteActivity)
                }
                return
            }
            val content = textFieldInputContent.text.toString()
            if (content.trim().isEmpty()) {
                Util.showInfoDialog(this@WriteActivity, "내용 입력 오류", "내용을 입력해주세요"){ _: DialogInterface, _: Int ->
                    Util.showSoftInput(textFieldInputContent, this@WriteActivity)
                }
                return
            }

            addMemoData()
            finish()
        }
    }

    // 저장 처리
    fun addMemoData(){
        activityWriteBinding.apply {
            val memo = Memo()
            memo.title = textFieldInputTitle.text.toString()
            memo.content = textFieldInputContent.text.toString()
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            memo.date = dateFormat.format(calendar.time)
            // 메모 리스트에 메모를 추가한다.
            Util.memoList.add(memo)
            Log.d("test1234", memo.date)
            Log.d("test1234", Util.memoList[0].date)
        }
    }
}