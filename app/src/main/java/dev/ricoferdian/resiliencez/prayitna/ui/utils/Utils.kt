package dev.ricoferdian.resiliencez.prayitna.ui.utils

import android.content.Context
import android.widget.Toast


fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}