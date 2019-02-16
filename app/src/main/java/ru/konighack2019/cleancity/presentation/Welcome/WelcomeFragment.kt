package ru.konighack2019.cleancity.presentation.Welcome

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.*

import ru.konighack2019.cleancity.R

class WelcomeFragment : Fragment() {

    private var promptedForAccount = false
    private  val ACCOUNT_PICK = 10


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val am : AccountManager = AccountManager.get(activity.applicationContext)
        val accounts = am.accounts
        //Log.wtf("lol_tag", accounts.toString())




        val tMgr = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val mPhoneNumber = tMgr.line1Number
        Log.wtf("lol_tag", mPhoneNumber)

        et_phone.setText(mPhoneNumber)
        //et_email.setText(accounts[0].name)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }


    override fun onResume() {
        super.onResume()

        if (!promptedForAccount) {
            promptedForAccount = true
            val intent = AccountManager.newChooseAccountIntent(
                null,
                null,
                arrayOf("com.google"),
                false,
                null,
                null,
                null,
                null)
            startActivityForResult(intent, ACCOUNT_PICK)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACCOUNT_PICK) {
            val accountName = data?.extras?.get(AccountManager.KEY_ACCOUNT_NAME) ?: "NONE"
            Log.d("mem_tag", "Selected account: $accountName")

            et_email.setText(accountName.toString())


        }
    }


}
