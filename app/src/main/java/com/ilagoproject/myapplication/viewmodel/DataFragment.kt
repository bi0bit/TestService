package com.ilagoproject.myapplication.viewmodel

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.apiservice.data.Pairs
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.databinding.FragmentDataBinding
import com.ilagoproject.myapplication.model.data.AccountData
import com.ilagoproject.myapplication.model.data.CCPromoData
import com.ilagoproject.myapplication.ui.CCPromoAdapter
import com.ilagoproject.myapplication.ui.DataSignalAdapter
import com.ilagoproject.myapplication.utils.autoScroll
import com.ilagoproject.myapplication.utils.isOnline
import com.ilagoproject.myapplication.viewmodel.base.AccountViewModelFragment
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap
import kotlinx.android.synthetic.main.fragment_data.*
import java.text.SimpleDateFormat

class DataFragment : AccountViewModelFragment() {

    init {
        App.modelComponent.inject(this)
    }

    private lateinit var binding: FragmentDataBinding

    private val dataSignalAdapter = DataSignalAdapter()

    private val ccPromosAdapter = CCPromoAdapter()

    private lateinit var requestData: HashMap<String, Any>

    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gson = App.modelComponent.getGson()
        requestData = defaultRequestData()
        dataSignalAdapter.setHasStableIds(true)
    }

    private fun defaultRequestData() : HashMap<String, Any>{
        val requestData = HashMap<String, Any>()
        val today = Calendar.getInstance()
        val fourteenDayAgo = Calendar.Builder().setInstant(today.timeInMillis).build()
        fourteenDayAgo.add(Calendar.DAY_OF_YEAR, -14)
        val from = fourteenDayAgo.timeInMillis / 1000
        val to = today.timeInMillis / 1000
        val pairs:String = EnumSet.of(Pairs.EURUSD, Pairs.USDCAD).stream()
            .map(Any::toString)
            .collect(Collectors.joining(","))
        with(requestData){
            put("from", from)
            put("to", to)
            put("pairs", pairs)
        }
        return requestData
    }

    private fun onSuccessUpdateData(accountData: AccountData?){
        updateDataEnd()

        if(accountData != null)
        {
            model.accountData = accountData

            model.saveAccountDataToCache()

            updateView()
        }
    }

    private fun updateView(){
        if(model.accountData != null){
            val dataSignal = model.accountData?.dataSignals ?: emptyList()
            dataSignalAdapter.setData(dataSignal)

            binding.invalidateAll()
        }
    }

    private fun updateDataStart(){
        progressIndicatorUpdateData.show()
    }

    private fun updateDataEnd(){
        progressIndicatorUpdateData.hide()
    }

    private fun updateData(){
        updateDataSignals()

        updateCcPromo()
    }

    private fun updateCcPromo(){
        if(context!!.isOnline()){
            model.service.getAdditionalInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getCcPromoData, this::getCCPromoError)
        }
        else{
            val cacheData = loadCCPromoFromCache()
            if (cacheData != null) {
                ccPromosAdapter.setData(cacheData)
            }
            else
                ccPromo.visibility = View.GONE
        }
    }

    private fun updateDataSignals(){
        updateDataStart()

        if(context!!.isOnline()){
            model.service.getAccountData(model.authDataPeanut, model.authDataPartner, requestData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .last(AccountData())
                .subscribe(this::onSuccessUpdateData,  this::onErrorUpdateData)
        }
        else{
            Toasty.warning(context!!, getString(R.string.failed_internet_connection), Toasty.LENGTH_LONG).show()
            model.loadAccountDataFromCache()
            updateView()
            updateDataEnd()
        }
    }

    private fun onErrorUpdateData(throwable: Throwable?){
        updateDataEnd()
        if(context!!.isOnline().not()){
            Toasty.warning(context!!, getString(R.string.failed_internet_connection), Toasty.LENGTH_LONG).show()
        }
        else{
            Toasty.warning(context!!, getString(R.string.failed_get_data), Toasty.LENGTH_LONG).show()
        }
        model.loadAccountDataFromCache()
        updateView()
        Log.e("Update data", throwable?.message, throwable)
    }

    fun isShowCcPromo():Int = if (ccPromosAdapter.itemCount < 1) View.GONE else View.VISIBLE

    private fun getCcPromoData(envelope: CCPromoResponseEnvelope){
        val json = envelope.body.data.result
        if(json.isNotEmpty()){
            ccPromo.visibility = View.VISIBLE
            val typeMap = object : TypeToken<Map<String, CCPromoData>>(){}.type
            val dataHolder:Map<String, CCPromoData> = gson.fromJson(json, typeMap)
            val ccPromoData = dataHolder.values
            ccPromoData
                .map {
                    it.image = it.image.replace("forex-images.instaforex.com", "forex-images.ifxdb.com")
                    it.link = it.link.replace(".com",".net")
                }
            ccPromosAdapter.setData(ccPromoData)
            saveCCPromoToCache(ccPromoData)
        }
    }

    private fun getCCPromoError(throwable: Throwable?){
        Log.e("Get promo", throwable?.message, throwable)
        val cacheData = loadCCPromoFromCache()
        if (cacheData != null) {
            ccPromosAdapter.setData(cacheData)
        }
        else
            ccPromo.visibility = View.GONE
    }

    fun updateDataSignalsRequest(){

        val view = LayoutInflater.from(context!!).inflate(R.layout.dialog_update_data_signals, binding.root as ViewGroup, false)

        val btnRange = view.findViewById<MaterialButton>(R.id.btnSelectRange)

        val selectedDateFrom = view.findViewById<MaterialTextView>(R.id.selected_date_from)
        val selectedDateTo = view.findViewById<MaterialTextView>(R.id.selected_date_to)

        val selectPairs = view.findViewById<MultiSpinnerSearch>(R.id.selectPairs)

        val selectedPair = (requestData.getValue("pairs") as String).split(",")

        val listPairs = Pairs.values().map {
            KeyPairBoolData(it.name, it.name in selectedPair)
        }

        lateinit var rangeDates: Pair<Long, Long>;

        selectPairs.isSearchEnabled = false

        selectPairs.setItems(listPairs, {})

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(R.string.select_dates_range)
            .setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            ).build()

        //On select new date range
        dateRangePicker.addOnPositiveButtonClickListener{
            rangeDates = Pair(it.first / 1000, it.second / 1000)

            val dateFrom = Date(rangeDates.first * 1000)
            val dateTo = Date(rangeDates.second * 1000)

            selectedDateFrom?.text = SimpleDateFormat("dd/MM/yy").format(dateFrom)
            selectedDateTo?.text = SimpleDateFormat("dd/MM/yy").format(dateTo)
        }


        btnRange.setOnClickListener{
            dateRangePicker.show(fragmentManager!!, "dateRange")
        }


        rangeDates = Pair(requestData.getValue("from") as Long, requestData.getValue("to") as Long)

        val dateFrom = Date(rangeDates.first * 1000)
        val dateTo = Date(rangeDates.second * 1000)

        selectedDateFrom?.text = SimpleDateFormat("dd/MM/yy").format(dateFrom)
        selectedDateTo?.text = SimpleDateFormat("dd/MM/yy").format(dateTo)

        val dialog = MaterialAlertDialogBuilder(context!!)
            .setView(view)
            .setPositiveButton(R.string.update){ _,_ ->
                requestData["from"] = rangeDates.first
                requestData["to"] = rangeDates.second
                requestData["pairs"] = selectPairs.selectedItems.map { it.name }.stream()
                        .collect(Collectors.joining(","))
                updateDataSignals()
            }
            .create()

        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.accountData?.dataSignals?.let {
            dataSignalAdapter.setData(it)
        }
        dataView.adapter = dataSignalAdapter
        ccPromo.adapter = ccPromosAdapter
        ccPromo.autoScroll(6000)

        updateData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data, container, false)
        binding.viewModel = this
        return binding.root
    }

    fun loadCCPromoFromCache() : Collection<CCPromoData>?{
        var cachePromo:List<CCPromoData>? = null
        model.db.executeTransaction {
            cachePromo = it.where(CCPromoData::class.java).findAll()
        }
        return cachePromo
    }

    fun saveCCPromoToCache(cachePromo: Collection<CCPromoData>){
        model.db.beginTransaction()
        cachePromo.forEach{
            model.db.insert(it)
        }
        model.db.commitTransaction()
    }

}