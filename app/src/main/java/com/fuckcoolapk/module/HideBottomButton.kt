package com.fuckcoolapk.module

import android.widget.LinearLayout
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class HideBottomButton {
    fun init() {
        if (OwnSP.ownSP.getBoolean("hideBottomButton", false)) {
            "com.aurelhubert.ahbottomnavigation.AHBottomNavigation"
                    .hookBeforeMethod("addItems", List::class.java) {
                        val bottomBtnList = it.args[0] as MutableList<Any>
                        val bottomBtnList2 = mutableListOf<Any>()
                        for (i in bottomBtnList.indices) {
                            val btn = bottomBtnList[i]
                            val title = XposedHelpers.getObjectField(btn, "title") as String
                            if ("首页" == title || "我" == title) bottomBtnList2.add(btn)
                        }
                        it.args[0] = bottomBtnList2
                    }

            "com.aurelhubert.ahbottomnavigation.AHBottomNavigation"
                    .hookBeforeMethod("updateItems", Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType) {
                        var i = it.args[0] as Int
                        if (i == 1) i = 4
                        it.args[0] = i
                    }

        }
    }
}
