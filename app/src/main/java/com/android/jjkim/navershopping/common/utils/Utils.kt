package com.android.jjkim.navershopping.common.utils

import android.annotation.TargetApi
import android.app.*
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.app.check.Checker
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {
        const val MSG_NONE = 0
        const val MSG_OK = 11
        const val MSG_CANCEL = 12
        const val MSG_NEXT = 13

        /**
         * Application 종료
         *
         * @param activity
         */
        fun quitApp(activity: Activity) {
            activity.moveTaskToBack(true)
            ActivityCompat.finishAffinity(activity)
            //android.os.Process.killProcess(android.os.Process.myPid());
        }

        fun isEmpty(s: Any?): Boolean {
            if (s == null)
                return true

            if (s is String && s.trim { it <= ' ' }.isEmpty())
                return true

            if (s is String && ("null" == s || "<null>" == s))
                return true

            if (s is Map<*, *>)
                return s.isEmpty()

            if (s is List<*>)
                return s.isEmpty()

            return if (s is Array<*>) (s as Array<Any?>).size == 0
                    else false
        }

        fun DialogShowOneBt(context: Activity, str_msg: String, str_ok: String, listener: DialogInterface.OnClickListener?) {
            DialogShow(true, context, "", str_msg, str_ok, "", listener)
        }

        fun DialogShowOneBt(context: Activity, str_title: String, str_msg: String, str_ok: String, listener: DialogInterface.OnClickListener?) {
            DialogShow(true, context, str_title, str_msg, str_ok, "", listener)
        }

        fun DialogShow(context: Activity, str_msg: String, str_ok: String, str_cancel: String, listener: DialogInterface.OnClickListener?) {
            DialogShow(false, context, "", str_msg, str_ok, str_cancel, listener)
        }

        fun DialogShow(context: Activity, str_title: String, str_msg: String, str_ok: String, str_cancel: String, listener: DialogInterface.OnClickListener?) {
            DialogShow(false, context, str_title, str_msg, str_ok, str_cancel, listener)
        }

        /*public static void DialogShowTextColor(Activity context, String str_title, String str_msg, String coloredText, String color, String str_ok, String str_cancel, final DialogInterface.OnClickListener listener) {
            DialogShowTextColor(true, context, str_title, str_msg, coloredText, color, str_ok, str_cancel, listener);
        }*/

        fun DialogShow(
            oneButton: Boolean, context: Activity, str_title: String, str_msg: String,
            str_ok: String, str_cancel: String, listener: DialogInterface.OnClickListener?
        ) {
            if (context.isFinishing) {
                return
            }

            val title = setDialogTitle(context, str_title)
            val alertBuilder = AlertDialog.Builder(
                ContextThemeWrapper(
                    context,
                    androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert
                )
            )

            // 커스텀 타이틀 2줄 이상 표시 위해
            val titleTextView = View.inflate(context, R.layout.dialog_title, null)
            val textview_title = titleTextView.findViewById<View>(R.id.textview_title) as TextView
            textview_title.text = title
            alertBuilder.setCustomTitle(titleTextView)
            //        alertBuilder.setTitle(title);
            alertBuilder.setMessage(str_msg)
            alertBuilder.setCancelable(false)
            alertBuilder.setPositiveButton(str_ok) { dialog, which ->
                listener?.onClick(
                    dialog,
                    MSG_OK
                )
            }

            if (!oneButton) alertBuilder.setNegativeButton(str_cancel) { dialog, which ->
                listener?.onClick(
                    dialog,
                    MSG_CANCEL
                )
            }

            val alert = alertBuilder.create()
            alert.show()
            alert.setOnDismissListener { /*if (listener != null)
                        listener.onClick(dialog, DIALOG_DISSMISS);*/
            }
        }

        private fun setDialogTitle(c: Context, str_title: String?): String {
            return str_title ?: c.getString(R.string.app_name)
        }

        fun getScreenWidth(context: Context): Int {
            val displayMetrics = DisplayMetrics()

            if (context is Activity) {
                context.windowManager
                    .defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Application) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Service) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }
            return displayMetrics.widthPixels
        }

        fun getScreenHeight(context: Context): Int {
            val displayMetrics = DisplayMetrics()
            if (context is Activity) {
                context.windowManager
                    .defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Application) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Service) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }
            return displayMetrics.heightPixels
        }

        fun getRatioLength(length: Float, ratioWidth: Float, ratioHeight: Float): Float {
            return length * ratioHeight / ratioWidth
        }


        fun getPixelToDp(context: Context, dp: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                context.resources.displayMetrics
            ).toInt()
        }

        fun getStatusBarHeight(context: Context): Int {
            val resources = context.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                Math.ceil(
                    ((if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) 24 else 25) *
                            resources.displayMetrics.density).toDouble()
                ).toInt()
            }
        }

        fun getAndroidId(context: Context): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun isTablet(context: Context): Boolean {
            var bTablet = false
            val screenSizeType = (context.resources.configuration.screenLayout
                    and Configuration.SCREENLAYOUT_SIZE_MASK)
            when (screenSizeType) {
                Configuration.SCREENLAYOUT_SIZE_XLARGE, Configuration.SCREENLAYOUT_SIZE_LARGE -> bTablet =
                    true
                else -> {}
            }
            return bTablet
        }

        fun getScreenOrientation(context: Context): Int {
            val displayMetrics = DisplayMetrics()
            if (context is Activity) {
                context.windowManager
                    .defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Application) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            } else if (context is Service) {
                val windowManager =
                    context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(displayMetrics)
            }
            return if (displayMetrics.widthPixels < displayMetrics.heightPixels) Configuration.ORIENTATION_PORTRAIT else Configuration.ORIENTATION_LANDSCAPE
        }

        fun downKeyboard(context: Context, editText: EditText?) {
            editText?.clearFocus()
            val mInputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(editText?.windowToken, 0)
        }

        /**
         * 문자열내 특정 문자색 지정
         *
         * @param src
         * @param target
         * @param baseColor
         * @param targetColor
         * @return
         */
        fun getHighlightedText(src: String, target: String, baseColor: String, targetColor: String
        ): String {
            var src = src
            var html = ""

            while (src != null && src.length > 0 && src.indexOf(target) > -1) {
                val targetIdx = src.indexOf(target)
                if (targetIdx == 0) {
                    html += "<font color=$targetColor>"
                    html += target
                    html += "</font>"
                } else {
                    html += "<font color=$baseColor>"
                    html += src.substring(0, targetIdx)
                    html += "</font>"
                    html += "<font color=$targetColor>"
                    html += target
                    html += "</font>"
                }
                src = src.substring(targetIdx + target.length, src.length)
            }
            if (src != null && src.isNotEmpty()) {
                html += "<font color=$baseColor>"
                html += src
                html += "</font>"
            }
            return html
        }

        /**
         * 가격 콤마 추가
         */
        fun setConvertPrice(value: String, unit: String): String {
            var v = 0

            if (Pattern.matches("^[0-9]*$", value)) {
                v = value.toInt()
            }
            return NumberFormat.getNumberInstance(Locale.KOREA).format(v.toLong()) + unit
        }

        /**
         * 세자리 컴마 + 소수점 두자리
         *
         * @param num
         * @param decimal 소수점 표시
         * @return
         */
        fun setConvertComma(num: Float, decimal: Boolean): String {
            return if (decimal) {
                val decimalFormat = DecimalFormat("#,##0.00")
                decimalFormat.format(num.toDouble())
            } else {
                val decimalFormat = DecimalFormat("#,###")
                decimalFormat.format(num.toDouble())
            }
        }

        fun setConvertComma(num: Double, decimal: Boolean): String {
            return if (decimal) {
                val decimalFormat = DecimalFormat("#,##0.00")
                decimalFormat.format(num)
            } else {
                val decimalFormat = DecimalFormat("#,###")
                decimalFormat.format(num)
            }
        }

        fun setConvertComma(num: Int, decimal: Boolean): String {
            return if (decimal) {
                val decimalFormat = DecimalFormat("#,##0.00")
                decimalFormat.format(num.toLong())
            } else {
                val decimalFormat = DecimalFormat("#,###")
                decimalFormat.format(num.toLong())
            }
        }

        fun isIntegerFromStr(num: String): Boolean {
            try {
                num.toInt()
            } catch (e: NumberFormatException) {
                return false
            } catch (e: NullPointerException) {
                return false
            }
            return true
        }

        /**
         * TextView 취소선 적용
         *
         * @param tv
         */
        fun setCancelLine(tv: TextView) {
            tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        /**
         * Statusbar Drawable 및 Icon 밝기 적용
         *
         * @param activity
         * @param drawableId
         */
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun setStatusBarDrawable(activity: Activity, drawableId: Int, isDark: Boolean, isFullLayout: Boolean
        ) {
            val window = activity.window

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (isFullLayout) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    )
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    )
                }
                val background = activity.resources.getDrawable(drawableId)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(R.color.transparent)
                window.navigationBarColor = activity.resources.getColor(R.color.transparent)
                window.setBackgroundDrawable(background)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val decor = window.decorView
                if (isDark) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decor.systemUiVisibility = 0
                }
            }
        }

        /**
         * Status bar color 및 Icon 밝기 적용
         */
        fun setChangeStatusBar(act: Activity, colorId: Int, isDark: Boolean) {
            val window = act.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(act, colorId)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val decor = window.decorView
                if (isDark) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decor.systemUiVisibility = 0
                }
            }
        }

        fun hiddenSoftKey(c: Context, editText: EditText?) {
            if (editText == null) return
            editText.clearFocus()
            val im = c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(editText.windowToken, 0)
        }

        /**
         * 성인인증 1년 지났는지 체크
         *
         * @param adultDay
         * @return
         */
        fun doDiffOfDate(adultDay: String): Long {
            if (adultDay != null && "" != adultDay) {
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val now = System.currentTimeMillis()
                val date = Date(now)
                val nowTime = formatter.format(date)
                try {
                    val beginDate = formatter.parse(adultDay)
                    val endDate = formatter.parse(nowTime)

                    // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
                    val diff = endDate.time - beginDate.time
                    return diff / (24 * 60 * 60 * 1000)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            return 366
        }

        /***
         * 페이저 내에서의 indicator
         */
        fun setPagerIndicator(c: Context, lay: LinearLayout?, total: Int, posi: Int, img_on: Int, img_off: Int, padding_dp: Int) {
            if (lay == null) return
            lay.removeAllViews()
            if (total < 2) return
            val padding = getPixelToDp(c, padding_dp.toFloat())
            for (index in 0 until  /*PAGES*/total) {
                val iv = ImageView(c)
                if (index == posi) {
                    iv.setImageResource(img_on)
                } else {
                    iv.setImageResource(img_off)
                }
                iv.setPadding(padding, 0, padding, 0)
                lay.addView(iv)
            }
        }

        /**
         * 소프트키 유무체크
         *
         * @param context
         * @return
         */

        fun hasSoftMenu(context: Context): Boolean {
            //메뉴버튼 유무
            val hasMenuKey = ViewConfiguration.get(context.applicationContext).hasPermanentMenuKey()

            //뒤로가기 버튼 유무
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            return if (!hasMenuKey && !hasBackKey) { // lg폰 소프트키일 경우
                true
            } else { // 삼성폰 등.. 메뉴 버튼, 뒤로가기 버튼 존재
                false
            }
        }

        /**
         * 소프트키 높이 가져오기
         *
         * @param context
         * @return
         */
        fun getSoftMenuHeight(context: Context): Int {
            if (!hasSoftMenu(context))
                return 0

            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            var softKeyHeight = 0

            if (resourceId > 0)
                softKeyHeight = resources.getDimensionPixelSize(resourceId)

            return softKeyHeight
        }

        /**
         * 현재 보여지는 액티비티 클래스 이름 가져오기
         *
         * @param context
         * @return
         */
        fun getRunActivityClassName(context: Context): String {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val taskInfo = activityManager.getRunningTasks(1)
            val topActivity = taskInfo[0].topActivity
            val runClassName = topActivity?.className
            return runClassName ?: ""
        }

        fun fastblur(sentBitmap: Bitmap, scale: Float, radius: Int): Bitmap? {
            var sentBitmap = sentBitmap
            val width = Math.round(sentBitmap.width * scale)
            val height = Math.round(sentBitmap.height * scale)
            sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false)
            val bitmap = sentBitmap.copy(sentBitmap.config, true)

            if (radius < 1)
                return null

            val w = bitmap.width
            val h = bitmap.height
            val pix = IntArray(w * h)
            Log.e("pix", w.toString() + " " + h + " " + pix.size)
            bitmap.getPixels(pix, 0, w, 0, 0, w, h)
            val wm = w - 1
            val hm = h - 1
            val wh = w * h
            val div = radius + radius + 1
            val r = IntArray(wh)
            val g = IntArray(wh)
            val b = IntArray(wh)
            var rsum: Int
            var gsum: Int
            var bsum: Int
            var x: Int
            var y: Int
            var i: Int
            var p: Int
            var yp: Int
            var yi: Int
            var yw: Int
            val vmin = IntArray(Math.max(w, h))
            var divsum = div + 1 shr 1
            divsum *= divsum
            val dv = IntArray(256 * divsum)
            i = 0
            while (i < 256 * divsum) {
                dv[i] = i / divsum
                i++
            }
            yi = 0
            yw = yi
            val stack = Array(div) { IntArray(3) }
            var stackpointer: Int
            var stackstart: Int
            var sir: IntArray
            var rbs: Int
            val r1 = radius + 1
            var routsum: Int
            var goutsum: Int
            var boutsum: Int
            var rinsum: Int
            var ginsum: Int
            var binsum: Int
            y = 0
            while (y < h) {
                bsum = 0
                gsum = bsum
                rsum = gsum
                boutsum = rsum
                goutsum = boutsum
                routsum = goutsum
                binsum = routsum
                ginsum = binsum
                rinsum = ginsum
                i = -radius
                while (i <= radius) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))]
                    sir = stack[i + radius]
                    sir[0] = p and 0xff0000 shr 16
                    sir[1] = p and 0x00ff00 shr 8
                    sir[2] = p and 0x0000ff
                    rbs = r1 - Math.abs(i)
                    rsum += sir[0] * rbs
                    gsum += sir[1] * rbs
                    bsum += sir[2] * rbs
                    if (i > 0) {
                        rinsum += sir[0]
                        ginsum += sir[1]
                        binsum += sir[2]
                    } else {
                        routsum += sir[0]
                        goutsum += sir[1]
                        boutsum += sir[2]
                    }
                    i++
                }
                stackpointer = radius
                x = 0
                while (x < w) {
                    r[yi] = dv[rsum]
                    g[yi] = dv[gsum]
                    b[yi] = dv[bsum]
                    rsum -= routsum
                    gsum -= goutsum
                    bsum -= boutsum
                    stackstart = stackpointer - radius + div
                    sir = stack[stackstart % div]
                    routsum -= sir[0]
                    goutsum -= sir[1]
                    boutsum -= sir[2]
                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm)
                    }
                    p = pix[yw + vmin[x]]
                    sir[0] = p and 0xff0000 shr 16
                    sir[1] = p and 0x00ff00 shr 8
                    sir[2] = p and 0x0000ff
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                    rsum += rinsum
                    gsum += ginsum
                    bsum += binsum
                    stackpointer = (stackpointer + 1) % div
                    sir = stack[stackpointer % div]
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                    rinsum -= sir[0]
                    ginsum -= sir[1]
                    binsum -= sir[2]
                    yi++
                    x++
                }
                yw += w
                y++
            }
            x = 0
            while (x < w) {
                bsum = 0
                gsum = bsum
                rsum = gsum
                boutsum = rsum
                goutsum = boutsum
                routsum = goutsum
                binsum = routsum
                ginsum = binsum
                rinsum = ginsum
                yp = -radius * w
                i = -radius
                while (i <= radius) {
                    yi = Math.max(0, yp) + x
                    sir = stack[i + radius]
                    sir[0] = r[yi]
                    sir[1] = g[yi]
                    sir[2] = b[yi]
                    rbs = r1 - Math.abs(i)
                    rsum += r[yi] * rbs
                    gsum += g[yi] * rbs
                    bsum += b[yi] * rbs
                    if (i > 0) {
                        rinsum += sir[0]
                        ginsum += sir[1]
                        binsum += sir[2]
                    } else {
                        routsum += sir[0]
                        goutsum += sir[1]
                        boutsum += sir[2]
                    }
                    if (i < hm) {
                        yp += w
                    }
                    i++
                }
                yi = x
                stackpointer = radius
                y = 0
                while (y < h) {

                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] =
                        -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
                    rsum -= routsum
                    gsum -= goutsum
                    bsum -= boutsum
                    stackstart = stackpointer - radius + div
                    sir = stack[stackstart % div]
                    routsum -= sir[0]
                    goutsum -= sir[1]
                    boutsum -= sir[2]
                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w
                    }
                    p = x + vmin[y]
                    sir[0] = r[p]
                    sir[1] = g[p]
                    sir[2] = b[p]
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                    rsum += rinsum
                    gsum += ginsum
                    bsum += binsum
                    stackpointer = (stackpointer + 1) % div
                    sir = stack[stackpointer]
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                    rinsum -= sir[0]
                    ginsum -= sir[1]
                    binsum -= sir[2]
                    yi += w
                    y++
                }
                x++
            }
            Log.e("pix", w.toString() + " " + h + " " + pix.size)
            bitmap.setPixels(pix, 0, w, 0, 0, w, h)
            return bitmap
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap? {
            var bitmap: Bitmap? = null
            if (drawable is BitmapDrawable) {
                val bitmapDrawable = drawable
                if (bitmapDrawable.bitmap != null) {
                    return bitmapDrawable.bitmap
                }
            }
            bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                ) // Single color bitmap will be created of 1x1 pixel
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        fun calling(context: Context, phonenumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phonenumber")
            context.startActivity(intent)
        }

        /**
         * 화면에 탭 가로 사이즈가 균등하게 마진 주기
         *
         * @param tabLayout
         * @param externalMargin
         * @param internalMargin
         */
        fun wrapTabIndicatorToTitle(
            tabLayout: TabLayout,
            externalMargin: Int,
            internalMargin: Int
        ) {
            val tabStrip = tabLayout.getChildAt(0)
            val externalMarginPx = getPixelToDp(tabLayout.context, externalMargin.toFloat())
            val internalMarginPx = getPixelToDp(tabLayout.context, internalMargin.toFloat())
            //        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            if (tabStrip is ViewGroup) {
                val childCount = tabStrip.childCount
                for (i in 0 until childCount) {
                    val tabView = tabStrip.getChildAt(i)
                    //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                    tabView.minimumWidth = 0
                    // set padding to 0 for wrapping indicator as title
                    tabView.setPadding(0, tabView.paddingTop, 0, tabView.paddingBottom)
                    // setting custom margin between tabs
                    if (tabView.layoutParams is MarginLayoutParams) {
                        val layoutParams = tabView.layoutParams as MarginLayoutParams
                        if (i == 0) {
                            // left
                            settingMargin(layoutParams, externalMarginPx, internalMarginPx)
                        } else if (i == childCount - 1) {
                            // right
                            settingMargin(layoutParams, internalMarginPx, externalMarginPx)
                        } else {
                            // internal
                            settingMargin(layoutParams, internalMarginPx, internalMarginPx)
                        }
                    }
                }
                tabLayout.requestLayout()
            }
        }

        private fun settingMargin(layoutParams: MarginLayoutParams, start: Int, end: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.marginStart = start
                layoutParams.marginEnd = end
            } else {
                layoutParams.leftMargin = start
                layoutParams.rightMargin = end
            }
        }

        fun resizeImagePath(originUrl: String, level: Int): String {
            if (TextUtils.isEmpty(originUrl)) {
                return originUrl
            }
            val extention =
                originUrl.substring(originUrl.lastIndexOf('.'), originUrl.length)
            return (originUrl.substring(0, originUrl.lastIndexOf('.'))
                    + "_" + Integer.toString(level) + extention)
        }

        fun isAppOnForeground(context: Context): Boolean {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses ?: return false
            val packageName = context.packageName
            for (appProcess in appProcesses) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName == packageName
                ) {
                    return true
                }
            }
            return false
        }

        fun isAppIsInBackground(context: Context): Boolean {
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                        processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND_SERVICE
                    ) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo?.packageName == context.packageName) {
                    isInBackground = false
                }
            }
            return isInBackground
        }

        fun getEncodedUr(urlVaule: String): String {
            return Uri.encode(urlVaule, "UTF-8")
        }

        fun darkenStatusBar(activity: Activity, color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1
            ) {
                activity.window.statusBarColor =
                    darkenColor(
                        ContextCompat.getColor(activity, color)
                    )
            }
        }

        // Code to darken the color supplied (mostly color of toolbar)
        private fun darkenColor(color: Int): Int {
            val hsv = FloatArray(3)
            Color.colorToHSV(color, hsv)
            hsv[2] *= 0.8f
            return Color.HSVToColor(hsv)
        }

        interface AdultCheckListener {
            fun onPlay()
            fun onPopUpPlay()
            fun onFinish()
            fun onPopUp()
        }
    }
}