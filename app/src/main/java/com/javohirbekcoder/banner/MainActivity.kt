package com.javohirbekcoder.banner

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.javohirbekcoder.banner.adapter.MyHorizontalAdapter
import com.javohirbekcoder.banner.adapter.MyVerticalAdapter
import com.javohirbekcoder.banner.databinding.ActivityMainBinding
import com.javohirbekcoder.banner.databinding.SettingDialogBinding
import com.javohirbekcoder.banner.model.MyItem
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import java.util.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var myList = ArrayList<MyItem>()
    private lateinit var lorem: Lorem
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var editor :SharedPreferences.Editor
    private var orientation :String = "vertical"
    private var spanCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        lorem = LoremIpsum.getInstance()

        initList()
        initValues()
        initAdapter()

       /* val linearLayoutManager = LinearLayoutManager(this, orientation, false)
        binding.myRecycler.layoutManager = linearLayoutManager
        binding.myRecycler.adapter = adapter*/

    }

    private fun initAdapter() {
        if (orientation == "vertical") {
            val adapter = MyVerticalAdapter(myList)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.myRecycler.layoutManager = linearLayoutManager
            binding.myRecycler.adapter = adapter
        } else  if (orientation == "horizontal") {
            val adapter = MyHorizontalAdapter(myList)
            val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.myRecycler.layoutManager = linearLayoutManager
            binding.myRecycler.adapter = adapter
        }else  if (orientation == "grid_vertical") {
            val adapter = MyHorizontalAdapter(myList)
            val gridLayoutManager = GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false)
            binding.myRecycler.layoutManager = gridLayoutManager
            binding.myRecycler.adapter = adapter
        } else if (orientation == "grid_horizontal") {
            val adapter = MyHorizontalAdapter(myList)
            val gridLayoutManager = GridLayoutManager(this, spanCount, GridLayoutManager.HORIZONTAL, false)
            binding.myRecycler.layoutManager = gridLayoutManager
            binding.myRecycler.adapter = adapter
        }
    }

    private fun initValues() {
        orientation = sharedPreferences.getString("orientation", "vertical").toString()
        spanCount = sharedPreferences.getInt("spanCount" , 1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings_btn)
            showSettings()
        return true
    }

    private fun showSettings() {
        var orientation : String
        val settings_dialog = Dialog(this@MainActivity)
        val bindingDialog = SettingDialogBinding.inflate(layoutInflater)

        settings_dialog.setContentView(bindingDialog.root)

        bindingDialog.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.grid_vertical -> bindingDialog.spanCountEt.visibility = View.VISIBLE
                R.id.grid_horizontal -> bindingDialog.spanCountEt.visibility = View.VISIBLE
                else -> bindingDialog.spanCountEt.visibility = View.GONE
            }
        }
        bindingDialog.saveBtn.setOnClickListener{
            orientation = when(bindingDialog.radioGroup.checkedRadioButtonId){
                R.id.vertical -> "vertical"
                R.id.horizontal -> "horizontal"
                R.id.grid_vertical -> "grid_vertical"
                R.id.grid_horizontal -> "grid_horizontal"
                else -> {"vertical"}
            }


            val spanCount = bindingDialog.spanCountEt.text.toString().toInt()
            if (spanCount !in 1..4){
                bindingDialog.spanCountEt.error = "span count must be 1 .. 4"
            }else {
                editor.putInt("spanCount", spanCount)
            }

            editor.putString("orientation", orientation)
            editor.apply()
            initValues()
            initAdapter()
            settings_dialog.dismiss()
        }
        settings_dialog.show()
    }


    private fun initList() {
        for (i in 0..50) {
            myList.add(
                MyItem(
                    "https://picsum.photos/id/${Random().nextInt(200)}/200",
                    lorem.getTitle(1),
                    lorem.getWords(5, 8)
                )
            )
        }
    }
}