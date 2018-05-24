package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.presenter.AddClientClass
import com.example.artjohn.blackfin.presenter.AddClientPresenter
import com.example.artjohn.blackfin.presenter.AddClientView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class AddClientActivity : BaseActivity(), AddClientView {

    //region - Variables
    var presenter = AddClientClass(this)
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        title = "Add Client"
        bind()

        occupationGuideText.setOnClickListener {
            startActivity<OccupationGuideActivity>()
        }
    }
    //endregion

    //region - Presenter Delegate
    override fun setAgeAdapter(data : ArrayList<Int>) {
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        ageSpinner.adapter = ageAdapter
    }
    override fun setStatusAdapter(data: Array<String>) {
        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        statusSpinner.adapter = statusAdapter
    }

    override fun setOccupationAdapter(data: Array<String>) {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        occupationSpinner.adapter = occupationAdapter
    }

    override fun setGenderAdapter(data: Array<String>) {
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        genderSpinner.adapter = genderAdapter
    }
    //endregion

    //region - Private methods
    private fun bind() {
        presenter.processAgeAdapter()
        presenter.setAdapters()
    }
    //endregion
}
