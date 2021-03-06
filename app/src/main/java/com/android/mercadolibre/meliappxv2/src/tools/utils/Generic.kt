package com.android.mercadolibre.meliappxv2.src.tools.utils

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned

object Generic {
    // Use for format text.
    @SuppressWarnings("deprecation")
    fun fromHtml(html: String?): Spanned {
        return when {
            html == null -> {
                // return an empty spannable if the html is null
                SpannableString("")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
                // we are using this flag to give a consistent behaviour
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            }
            else -> {
                Html.fromHtml(html)
            }
        }
    }
}