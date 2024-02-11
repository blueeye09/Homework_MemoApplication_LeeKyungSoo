package kr.co.lion.homework_memoapplication_leekyungsoo

import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class Util {

    companion object {
        // 메모 객체들을 담을 리스트
        val memoList = mutableListOf<Memo>()

        fun showSoftInput(view: View, context: Context) {
            // 포커스를 준다.
            view.requestFocus()
            thread {
                SystemClock.sleep(1000)
                val inputMethodManager =
                    context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(view, 0)
            }
        }

        // 안내를 위한 다이얼로그
        fun showInfoDialog(
            context: Context,
            title: String,
            message: String,
            listener: (DialogInterface, Int) -> Unit
        ) {
            val dialogBuilder = MaterialAlertDialogBuilder(context)
            dialogBuilder.setTitle(title)
            dialogBuilder.setMessage(message)
            dialogBuilder.setPositiveButton("확인", listener)
            dialogBuilder.show()
        }
    }
}