package com.example.website.consulta.View

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.TextView


class SwipeDismissTouchListener(
    view: View,
    BackgroundContainer: View?,
    tvBg: TextView?,
    token: Any?,
    callback: OnDismissCallback?
) : View.OnTouchListener {
    private var mSlop = 0
    private var mMinFlingVelocity = 0
    private var mMaxFlingVelocity = 0
    private var mAnimationTime: Long = 0

    private var mView: View? = view
    private var mBackground: View? = BackgroundContainer
    private var mBackgroundText: TextView? = tvBg
    private var mCallback: OnDismissCallback? = callback
    private var mViewWidth = 1

    private var mDownX = 0f
    private var mSwiping = false
    private var mToken: Any? = token
    private var mVelocityTracker: VelocityTracker? = null
    private var mTranslationX = 0f

    interface OnDismissCallback {
        fun onDismiss(view: View?, token: Any?)
        fun onRightSwipe(view: View?)
        fun onLeftSwipe(view: View?)
    }

    init {
        val vc = ViewConfiguration.get(view.context)
        mSlop = vc.scaledTouchSlop
        mMinFlingVelocity = vc.scaledMinimumFlingVelocity
        mMaxFlingVelocity = vc.scaledMaximumFlingVelocity
        mAnimationTime = view.context.resources.getInteger(
            R.integer.config_shortAnimTime
        ).toLong()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        motionEvent.offsetLocation(mTranslationX, 0f)
        if (mViewWidth < 2) {
            mViewWidth = mView!!.width
        }
        when (motionEvent.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = motionEvent.rawX
                mVelocityTracker = VelocityTracker.obtain()
                mVelocityTracker?.addMovement(motionEvent)
                view.onTouchEvent(motionEvent)
                return false
            }
            MotionEvent.ACTION_UP -> {
                if (mVelocityTracker == null) {
                    return false
                }

                val deltaX = motionEvent.rawX - mDownX
                mVelocityTracker!!.addMovement(motionEvent)
                mVelocityTracker!!.computeCurrentVelocity(1000)
                val velocityX = Math.abs(mVelocityTracker!!.xVelocity)
                val velocityY = Math.abs(mVelocityTracker!!.yVelocity)
                var dismiss = false
                var dismissRight = false
                if (Math.abs(deltaX) > mViewWidth / 3 * 2) {
                    dismiss = true
                    dismissRight = deltaX > 0
                } else if (mMinFlingVelocity <= velocityX && velocityX <= mMaxFlingVelocity && velocityY < velocityX) {
                    if (Math.abs(deltaX) > mViewWidth / 5) {
                        dismiss = true
                        dismissRight = mVelocityTracker!!.xVelocity > 0
                    }
                }
                if (dismiss) {
                    mView!!.animate()
                        .translationX(if (dismissRight) mViewWidth.toFloat() else -mViewWidth.toFloat())
                        .alpha(0f)
                        .setDuration(mAnimationTime)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                performDismiss()
                            }
                        })
                } else {
                    mView!!.animate()
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(mAnimationTime)
                        .setListener(null)
                }
                mVelocityTracker = null
                mTranslationX = 0f
                mDownX = 0f
                mSwiping = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (mVelocityTracker == null) {
                    return false
                }

                mVelocityTracker!!.addMovement(motionEvent)
                val deltaX = motionEvent.rawX - mDownX
                if (Math.abs(deltaX) > mSlop) {
                    mSwiping = true
                    mView!!.parent.requestDisallowInterceptTouchEvent(true)
                    val cancelEvent = MotionEvent.obtain(motionEvent)
                    cancelEvent.action = MotionEvent.ACTION_CANCEL or
                            (motionEvent.actionIndex shl MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                    mView!!.onTouchEvent(cancelEvent)
                }

                if (mSwiping) {
                    mTranslationX = deltaX
                    mView!!.translationX = deltaX
                    if (deltaX > 0) {
                        mBackground!!.setBackgroundColor(Color.parseColor("#BD362F"))
                        mBackgroundText!!.text = "رد درخواست"
                    } else {
                        mBackground!!.setBackgroundColor(Color.parseColor("#51A361"))
                        mBackgroundText!!.text = "تایید درخواست"
                    }
                    mView!!.alpha = Math.max(
                        0f, Math.min(
                            1f,
                            1f - 2f * Math.abs(deltaX / 3 * 2) / mViewWidth
                        )
                    )
                    return true
                }
            }
        }
        return false
    }



    private fun performDismiss() {
        val lp = mView!!.layoutParams
        val originalHeight = mView!!.height
        val animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mCallback!!.onDismiss(mView, mToken)
                mView!!.alpha = 1f
                mView!!.translationX = 0f
                lp.height = originalHeight
                mView!!.layoutParams = lp
            }
        })
        animator.addUpdateListener { valueAnimator ->
            lp.height = (valueAnimator.animatedValue as Int)
            mView!!.layoutParams = lp
        }
        animator.start()
    }
}