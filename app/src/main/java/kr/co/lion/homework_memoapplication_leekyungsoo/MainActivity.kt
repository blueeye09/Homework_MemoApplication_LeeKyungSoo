package kr.co.lion.homework_memoapplication_leekyungsoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.homework_memoapplication_leekyungsoo.databinding.ActivityMainBinding
import kr.co.lion.homework_memoapplication_leekyungsoo.databinding.RowMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    // Activity 런쳐
    private lateinit var writeActivityLauncher: ActivityResultLauncher<Intent>
    lateinit var showActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setLauncher()
        setToolbar()
        setView()
    }

    // 화면 갱신
    override fun onResume() {
        super.onResume()
        activityMainBinding.apply {
            activityMainBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
        }
    }

    // 런처 설정
    private fun setLauncher(){
        // writeActivity 런처
        val contract1 = ActivityResultContracts.StartActivityForResult()
        writeActivityLauncher = registerForActivityResult(contract1){

        }

        // ShowActivity 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        showActivityLauncher = registerForActivityResult(contract2){

        }
    }

    // 툴바 설정
    private fun setToolbar(){
        activityMainBinding.apply {
            toolbarMain.apply {
                // 제목 설정
                title = "메모 관리"
                // 메뉴
                inflateMenu(R.menu.menu_main)
                // 추가 버튼 눌렀을 때
                setOnMenuItemClickListener {
                    // 메뉴가 추가될 수도 있으니 id로 분기
                    when(it.itemId){
                        R.id.menu_item_main_add -> {
                            // 메모 작성 Activity로 이동
                            val writeIntent = Intent(this@MainActivity, WriteActivity::class.java)
                            writeActivityLauncher.launch(writeIntent)
                        }
                    }

                    true
                }
            }
        }
    }

    // 뷰 설정
    private fun setView(){
        activityMainBinding.apply {
            // RecyclerView
            recyclerViewMain.apply {
                // 어댑터
                adapter = RecyclerViewMainAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(this@MainActivity)
                // 데코레이션
                val deco = MaterialDividerItemDecoration(this@MainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }



    // RecyclerView의 어댑터
    inner class RecyclerViewMainAdapter : RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolderMain>() {
        // ViewHolder
        inner class ViewHolderMain(rowMainBinding: RowMainBinding) :
            RecyclerView.ViewHolder(rowMainBinding.root) {
            val rowMainBinding: RowMainBinding

            init {
                this.rowMainBinding = rowMainBinding

                this.rowMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderMain = ViewHolderMain(rowMainBinding)

            return viewHolderMain
        }

        override fun getItemCount(): Int {
            return Util.memoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
            val memo = Util.memoList[position]
            Log.d("test1235", position.toString())
            // 제목과 작성 날짜를 보여준다
            holder.rowMainBinding.textViewRowMainTitle.text = memo.title
            holder.rowMainBinding.textViewRowMainDate.text = memo.date

            holder.rowMainBinding.root.setOnClickListener{
                val showIntent = Intent(this@MainActivity, ShowActivity::class.java)

                // 객체를 전달한다.
                val obj = Util.memoList[position]
                // Util.memoList 리스트에 몇번째에 있는 값인지를 담아준다.
                showIntent.putExtra("position", position)
                // Position 번째에 있는 객체를 담아준다.
                showIntent.putExtra("obj", obj)

                showActivityLauncher.launch(showIntent)
            }
        }
    }
}