package org.chrjs.ratingdialoksample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.chrjs.ratingdialok.RatingDialok
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var ratingDialog: RatingDialok

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRatingDialog()
        button.setOnClickListener({ ratingDialog.showDialogNoMatterWhat() })
        button2.setOnClickListener { ratingDialog.rateNow() }
        ratingDialog.onStart()
    }

    override fun onResume() {
        super.onResume()
        ratingDialog.showDialogIfNeeded()
    }

    private fun setupRatingDialog() {
        ratingDialog = RatingDialok(this)
        ratingDialog.isCancelable = true
        ratingDialog.minimumDaysAfter = 7
        ratingDialog.minimumLaunchCount = 5
        ratingDialog.resourceIdStyle = R.style.CustomAlertDialogStyle
        ratingDialog.setStrings(R.string.title, R.string.message, R.string.rateNow, R.string.remindLater,
                R.string.remindNever)
        ratingDialog.actionCallback = actionCallback
        ratingDialog.addAdditionalCondition(condition)
    }

    private var condition: RatingDialok.Condition = object : RatingDialok.Condition {
        override fun conditionMet(): Boolean {
            val cal = Calendar.getInstance()
            return cal.get(Calendar.HOUR_OF_DAY) % 2 == 0
        }
    }

    private var actionCallback: RatingDialok.ActionCallback = object : RatingDialok.ActionCallback {
        override fun remindLaterClicked() {
            Toast.makeText(this@MainActivity, "Remind later clicked", Toast.LENGTH_SHORT).show()
        }

        override fun rateNowClicked() {
            Toast.makeText(this@MainActivity, "Rate Now Clicked", Toast.LENGTH_SHORT).show()
        }

        override fun remindNeverAgainClicked() {
            Toast.makeText(this@MainActivity, "Remind Never Again clicked", Toast.LENGTH_SHORT).show()
        }
    }
}