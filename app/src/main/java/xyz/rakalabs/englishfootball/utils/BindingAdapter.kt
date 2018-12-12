package xyz.rakalabs.englishfootball.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:setScore")
fun setScore(textView: TextView, score: Any?) {
    if (score != null) {
        textView.text = score.toString()
    }
}