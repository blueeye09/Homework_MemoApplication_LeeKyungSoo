package kr.co.lion.homework_memoapplication_leekyungsoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.co.lion.homework_memoapplication_leekyungsoo.databinding.ActivityShowBinding

class ShowActivity : AppCompatActivity() {

    lateinit var activityShowBinding: ActivityShowBinding

    // Activity 런쳐
    lateinit var modifyActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityShowBinding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(activityShowBinding.root)

        setLauncher()
        setToolbar()
        setView()
    }

    // 런처 설정
    fun setLauncher(){
        // ModifyActivity Launcher
        val contract1 = ActivityResultContracts.StartActivityForResult()
        modifyActivityLauncher = registerForActivityResult(contract1){

        }
    }

    fun setToolbar(){
        activityShowBinding.apply {
            toolbarShow.apply {
                // 제목 설정
                title = "메모 보기"

                // Back 버튼
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    setResult(RESULT_CANCELED)
                    finish()
                }

                // 메뉴
                inflateMenu(R.menu.menu_show)

                // 메뉴 클릭 리스너
                setOnMenuItemClickListener {
                    // 메뉴 id 별로 분기
                    when(it.itemId){
                        // 수정 (ModifyActivity로 객체 전달)
                        R.id.menu_item_show_modify -> {
                            val modifyIntent = Intent(this@ShowActivity, ModifyActivity::class.java)
                            // MainActivity에서 넘겨받은 position을 받아온다.
                            val position = intent.getIntExtra("position", 0)
                            // 받아온 position을 ModifyActivity로 넘겨준다.
                            modifyIntent.putExtra("position", position)
                            // 동물의 position을 intent에 담아서 ModifyActivity로 전달한다.
                            modifyActivityLauncher.launch(modifyIntent)
                        }

                        // 삭제
                        R.id.menu_item_show_delete -> {
                            // MainActivity로부터 삭제할 항목 순서 값을 가져온다.
                            val position = intent.getIntExtra("position", 0)
                            // 항목번째 객체를 리스트에서 제거한다.
                            Util.memoList.removeAt(position)
                            finish()
                        }
                    }
                    true
                }
            }
        }
    }

    fun setView(){
        activityShowBinding.apply {
            // 항목의 순서값을 가져온다.
            val position = intent.getIntExtra("position", 0)
            // 포지션번째의 객체를 추출한다.
            val memo = Util.memoList[position]

            textViewShowTitle.text = memo.title
            textViewShowContent.text = memo.content
            textViewShowDate.text = memo.date
        }
    }

    override fun onResume() {
        super.onResume()
        // 수정을 하고 다시 돌아왔을 때 View를 재구성함
        setView()
    }
}