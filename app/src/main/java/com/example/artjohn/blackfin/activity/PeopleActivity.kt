package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.PeopleAdapter
import com.example.artjohn.blackfin.dialog.PeopleDialog
import com.example.artjohn.blackfin.event.ConfigureClient
import com.example.artjohn.blackfin.event.PremiumRange
import com.example.artjohn.blackfin.event.RefreshPeopleEvent
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ConfigureBenefits
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_people.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class PeopleActivity : BaseActivity() {

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)
        title = "People"
        setRecyclerView()
        addUser.setOnClickListener {
            startActivity<AddActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        peopleRecyclerView.adapter.notifyDataSetChanged()
    }
    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //endregion

    //region - EventBus method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureClient(event : ConfigureClient) {
       PeopleDialog().show(this, event.index)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureClient(event : RefreshPeopleEvent) {
        peopleRecyclerView.adapter.notifyDataSetChanged()
    }
    //endregion

    //region - Private methods
    private fun setRecyclerView() {
        peopleRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        peopleRecyclerView.adapter = PeopleAdapter(ClientInfo.array, ConfigureBenefits.array)
    }
    //endregion
}
