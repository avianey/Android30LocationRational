package com.github.avianey.android30locationrational

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.widget.ViewSwitcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        refreshUI()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        refreshUI()
    }

    fun grantAccessFineLocation(view: View) {
        AlertDialog.Builder(this)
                .setTitle("Location Access Rational")
                .setMessage("Explain why you need Location")
                .setPositiveButton("Ok, grant") { d, _ ->
                    d.dismiss()
                    ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                }
                .setNegativeButton("No, not yet") { d, _ ->
                    d.dismiss()
                }
                .show()
    }


    fun grantAccessBackgroundLocation(view: View) {
        AlertDialog.Builder(this)
                .setTitle("Background Location Access")
                .setMessage("Explain why you need Background Location access")
                .setPositiveButton("Ok, grant") { d, _ ->
                    d.dismiss()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_BACKGROUND_LOCATION), REQUEST_ACCESS_BACKGROUND_LOCATION)
                    }
                }
                .setNegativeButton("No, not yet") { d, _ ->
                    d.dismiss()
                }
                .show()
    }

    private fun refreshUI() {
        // foreground
        findViewById<ViewSwitcher>(R.id.fg_location).let {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                it.displayedChild = INDEX_LABEL_GRANTED
            } else {
                it.displayedChild = INDEX_BUTTON_GRANT
            }
        }
        // background
        findViewById<ViewSwitcher>(R.id.bg_location).let {
            it.getChildAt(INDEX_BUTTON_GRANT).isEnabled = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
                it.visibility = INVISIBLE
            } else if (ActivityCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) == PERMISSION_GRANTED) {
                it.displayedChild = INDEX_LABEL_GRANTED
            } else {
                it.displayedChild = INDEX_BUTTON_GRANT
            }
        }
    }

    companion object {
        const val REQUEST_ACCESS_FINE_LOCATION = 1
        const val REQUEST_ACCESS_BACKGROUND_LOCATION = 2
        const val INDEX_BUTTON_GRANT = 0
        const val INDEX_LABEL_GRANTED = 1
    }

}