package com.dailydigest.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import com.dailydigest.R
import com.dailydigest.databinding.RateUsDialogBinding

class RateUsDialog(context: Context) : Dialog(context) {
    // var declaration
    private lateinit var binding: RateUsDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RateUsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // rating bar star change logic
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating <= 1) {
                binding.imgRating.setImageResource(R.drawable.imoji_one_star)
            } else if (rating <= 2) {
                binding.imgRating.setImageResource(R.drawable.imoji_two_star)
            } else if (rating <= 3) {
                binding.imgRating.setImageResource(R.drawable.imoji_three_star)
            } else if (rating <= 4) {
                binding.imgRating.setImageResource(R.drawable.imoji_four_star)
            } else if (rating <= 5) {
                binding.imgRating.setImageResource(R.drawable.imoji_five_star)
            }

            // animation of image
            animateImage(binding.imgRating)
        }

        // may be later button click event
        binding.btnMaybeLater.setOnClickListener {
            dismiss()
        }

        // rate now button click event
        binding.btnRateNow.setOnClickListener {
            cancel()
        }
    }

    // image animation fun
    private fun animateImage(ratingImage: ImageView) {
        val scaleAnimate = ScaleAnimation(
            0f,
            1f,
            0f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scaleAnimate.fillAfter
        scaleAnimate.duration
        ratingImage.startAnimation(scaleAnimate)
    }
}