package com.android.jjkim.navershopping.common

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateFormat.format
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.android.jjkim.navershopping.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

/** 토스트  */
fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
}

/** 날짜 */
// 날짜만 타임스탬프 변환 2020-01-01 - timestamp
fun String.convertDateToTimestamp(): Long =
    SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(this).time

fun Long.convertTimestampToDate(): String = DateFormat.format("yyyy-MM-dd", this).toString()

fun Long.convertTimestampToPointFullDate(): String =
    DateFormat.format("yyyy.MM.dd", this).toString()

fun Long.convertTimestampToPointFullDateTime(): String =
    DateFormat.format("yyyy.MM.dd HH:mm", this).toString()

fun Long.convertTimestampToSlashOnlyDate(): String =
    DateFormat.format("MM/dd", this).toString()

// 날짜,시간,분 포함된 타임스탬프 변환 2020-01-01-22-30 - timestamp
fun String.convertDateFullToTimestamp(): Long =
    SimpleDateFormat("yyyy-MM-dd-HH:mm", Locale.KOREA).parse(this).time

fun Long.convertTimestampToDateFull(): String =
    DateFormat.format("yyyy-MM-dd-HH:mm", this).toString()

fun Long.convertCurrentTimestampToDateTimestamp(): Long =
    this.convertTimestampToDate().convertDateToTimestamp()

// timestamp -> 13:40
fun Long.convertTimestampToTime(): String = DateFormat.format("HH:mm", this).toString()

//fun Long.convertTimesToTimestamp(): Long = DateFormat.format("HH:mm", this).toString()

// 시간 한자리면 앞에 0 붙여주어 변환
fun String.convertHourDoubleDigit(): String = if (this.length < 2) "0$this" else this

// 분 한자리면 앞에 0 붙여주어 반환
fun String.convertMinuteDoubleDigit(): String = if (this.length < 2) "0$this" else this

// 한자리 숫자면 두자리로 변환
fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this

fun Long.convertTimestampToHour(): Int = DateFormat.format("HH", this).toString().toInt()

fun Long.convertTimestampToMinute(): Int = DateFormat.format("mm", this).toString().toInt()

fun Int.convertNextHour(): Int = if (this == 23) 0 else this + 1

fun Int.convertNextMinute(): Int = if (this == 59) 0 else this + 1

// 다음날의 현재 Year
fun getNextDayCurrentYear(): Int {
    val c = Calendar.getInstance()
    c.add(Calendar.DATE, 1)
    return c.get(Calendar.YEAR)
}

// 다음날의 현재 Month
fun getNextDayCurrentMonth(): Int {
    val c = Calendar.getInstance()
    c.add(Calendar.DATE, 1)
    return if (c.get(Calendar.MONTH) == 11) 12
    else c.get(Calendar.MONTH) + 1
}

// 다음날의 현재 Day
fun getNextDayCurrentDay(): Int {
    val c = Calendar.getInstance()
    c.add(Calendar.DATE, 1)
    return c.get(Calendar.DAY_OF_MONTH)
}


// 현재 Year
fun getCurrentYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

// 현재 Month
fun getCurrentMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

// 현재 Day
fun getCurrentDay(): Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

// timestamp -> year
fun Long.convertTimestampToYear(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.YEAR]
}

// timestamp -> month
fun Long.convertTimestampToMonth(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.MONTH] + 1
}

// timestamp -> day
fun Long.convertTimestampToDay(): Int {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return cal[Calendar.DAY_OF_MONTH]
}


// 1시간 뒤 타임스탬프
fun Long.convertNextHourTimestamp(): Long = this + (60 * 60 * 1000)

// 2020, 1, 20 -> timestamp
fun convertDateToTimestamp(_year: Int, _month: Int, _day: Int): Long {
    val month = _month.toString().convertSingleToDoubleDigit().toInt()
    val day = _day.toString().convertSingleToDoubleDigit().toInt()
    val date = "$_year-$month-$day"
    return date.convertDateToTimestamp()
}

// timestamp -> 9.6~9.8
fun convertTimestampToTerm(startTimestamp: Long, endTimestamp: Long): String {
    return DateFormat.format("MM-dd", startTimestamp)
        .toString() + "~" + DateFormat.format("MM-dd", endTimestamp).toString()
}

// timestamp -> 9.6~9.8
fun convertTimestampOnlyDateMinusTerm(startTimestamp: Long, endTimestamp: Long): String {
    return DateFormat.format("yyyy.MM.dd", startTimestamp)
        .toString() + " - " + DateFormat.format("MM.dd", endTimestamp).toString()
}

// FCM 메시지로 사용(사용자에게 하루전날 예약알림)
fun convertTimeToUserStartFcmMessage(date: Long, time: String): String =
    date.convertTimestampToDate() + " " + time + " 여행 하루 전날입니다."

// FCM 메시지로 사용(이장님꼐 사용자의 예약알림)
fun convertTimeToMasterFcmMessage(date: Long): String =
    date.convertTimestampToDate() + "에 고객 예약이 있습니다."

// FCM 메시지로 사용(이장님수락)
fun convertTimeToMasterAcceptFcmMessage(date: Long, time: String): String =
    date.convertTimestampToDate() + " " + time + " 예약을 이장님이 수락했습니다."

// FCM 메시지로 사용(이장님거절)
fun convertTimeToMasterDenyFcmMessage(date: Long, time: String): String =
    date.convertTimestampToDate() + " " + time + " 예약을 이장님이 취소했습니다."

// FCM 메시지로 사용(사용자거절)
fun convertTimeToUserDenyFcmMessage(date: Long): String =
    date.convertTimestampToDate() + " 예약을 사용자가 취소했습니다."

fun combineTimestamp(x: Long, y: Long) = (x.toString() + y.toString()).toLong()

// 타임스탬프
fun getTimestamp(): Long = System.currentTimeMillis()

fun EditText.hideKeyboard(context: Context) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showKeyboard(context: Context) {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(this, 0)
}

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String) {
    Glide.with(this).load(url)
        .thumbnail(0.1f)
//        .error(R.drawable.img_product)
        .into(this)
}

@BindingAdapter("htmlText")
fun TextView.setHtmlText(html: String?) {
    text = html?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }
}

//@BindingAdapter("imageSrc")
//fun loadItemImage(view: ImageView, imageUrl: String?) {
//    Glide.with(view.context)
//        .load(imageUrl)
//        .into(view)
//}

@BindingAdapter("imageSrc")
fun ImageView.loadItemImage(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}



