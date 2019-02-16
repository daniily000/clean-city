package ru.konighack2019.cleancity.presentation.Welcome

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.*

import ru.konighack2019.cleancity.R

class WelcomeFragment : Fragment() {


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val am : AccountManager = AccountManager.get(activity.applicationContext)
        val accounts = am.accounts
        Log.wtf("lol_tag", accounts.toString())




        val tMgr = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val mPhoneNumber = tMgr.line1Number
        Log.wtf("lol_tag", mPhoneNumber)

        et_phone.setText(mPhoneNumber)
        et_email.setText(accounts[0].name)
        


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }


    interface OnFragmentInteractionListener {

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WelcomeFragment().apply {

            }
    }
}
