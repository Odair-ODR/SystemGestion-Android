package com.example.website.consulta.View

import android.R
import android.graphics.Rect
import android.view.*
import android.widget.AbsListView
import android.widget.ListView
import android.widget.TableLayout
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.AnimatorListenerAdapter
import com.nineoldandroids.animation.ValueAnimator
import com.nineoldandroids.view.ViewHelper.setAlpha
import com.nineoldandroids.view.ViewHelper.setTranslationX
import com.nineoldandroids.view.ViewPropertyAnimator.animate
import java.util.*

class SwipeDismissTableLayoutTouchListener(table: TableLayout, callback: OnDismissCallback) :
    View.OnTouchListener {
    private var mSlop = 0
    private var mMinFlingVelocity = 0
    private var mMaxFlingVelocity = 0
    private var mAnimationTime: Long = 0

    // Fixed properties
    private var mTable: TableLayout?
    private var mCallback: OnDismissCallback
    private var mViewWidth = 1 // 1 and not 0 to prevent dividing by zero


    // Transient properties
    private val mPendingDismisses: ArrayList<PendingDismissData> = arrayListOf()
    private var mDismissAnimationRefCount = 0
    private var mDownX = 0f
    private var mSwiping = false
    private var mVelocityTracker: VelocityTracker? = null
    private var mDownPosition = 0
    private var mDownView: View? = null
    private var mPaused = false

    init {
        val vc = ViewConfiguration.get(table.context)
        mSlop = vc.scaledTouchSlop
        mMinFlingVelocity = vc.scaledMinimumFlingVelocity
        mMaxFlingVelocity = vc.scaledMaximumFlingVelocity
        mAnimationTime = table.context.resources.getInteger(
            R.integer.config_shortAnimTime
        ).toLong()
        mTable = table
        mCallback = callback
    }

    interface OnDismissCallback {
        /**
         * Called when the user has indicated they she would like to dismiss one or more list item
         * positions.
         *
         * @param listView               The originating [ListView].
         * @param reverseSortedPositions An array of positions to dismiss, sorted in descending
         * order for convenience.
         */
        fun onDismiss(tableLayout: TableLayout?, reverseSortedPositions: IntArray?)
    }

    internal class PendingDismissData(var position: Int, var view: View) :
        Comparable<PendingDismissData> {
        override fun compareTo(other: PendingDismissData): Int {
            return other.position - position
        }
    }

    fun setEnabled(enabled: Boolean) {
        mPaused = !enabled
    }

    fun makeScrollListener(): AbsListView.OnScrollListener? {
        return object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(absListView: AbsListView, scrollState: Int) {
                setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            }

            override fun onScroll(absListView: AbsListView, i: Int, i1: Int, i2: Int) {}
        }
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        if (mViewWidth < 2) {
            mViewWidth = mTable!!.width
        }

        when (motionEvent?.getActionMasked()) {
            MotionEvent.ACTION_DOWN -> {
                if (mPaused) {
                    return false
                }

                // Find the child view that was touched (perform a hit test)
                val rect = Rect()
                val childCount = mTable!!.childCount
                val listViewCoords = IntArray(2)
                mTable!!.getLocationOnScreen(listViewCoords)
                val x = motionEvent.getRawX().toInt() - listViewCoords[0]
                val y = motionEvent.getRawY().toInt() - listViewCoords[1]
                var child: View
                var i = 0
                while (i < childCount) {
                    child = mTable!!.getChildAt(i)
                    child.getHitRect(rect)
                    if (rect.contains(x, y)) {
                        mDownView = child
                        break
                    }
                    i++
                }
                if (mDownView != null) {
                    mDownX = motionEvent.getRawX()
                    mDownPosition = i
                    mVelocityTracker = VelocityTracker.obtain()
                    mVelocityTracker?.addMovement(motionEvent)
                }
                if (i == 0) return false //> No permite si mueve la cabecera del tableLayout
                view?.onTouchEvent(motionEvent)
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (mVelocityTracker == null) {
                    return false
                }
                val deltaX: Float = motionEvent.getRawX() - mDownX
                mVelocityTracker?.addMovement(motionEvent)
                mVelocityTracker?.computeCurrentVelocity(1000)
                val velocityX = Math.abs(mVelocityTracker!!.xVelocity)
                val velocityY = Math.abs(mVelocityTracker!!.yVelocity)
                var dismiss = false
                var dismissRight = false
                if (Math.abs(deltaX) > mViewWidth / 2) {
                    dismiss = true
                    dismissRight = deltaX > 0
                } else if (mMinFlingVelocity <= velocityX && velocityX <= mMaxFlingVelocity && velocityY < velocityX) {
                    dismiss = true
                    dismissRight = mVelocityTracker!!.getXVelocity() > 0
                }
                if (dismiss) {
                    // dismiss
                    val downView =
                        mDownView!! // mDownView gets null'd before animation ends
                    val downPosition = mDownPosition
                    ++mDismissAnimationRefCount
                    animate(mDownView)
                        .translationX((if (dismissRight) mViewWidth else -mViewWidth).toFloat())
                        .alpha(0f)
                        .setDuration(mAnimationTime)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                performDismiss(downView, downPosition)
                            }
                        })
                } else {
                    // cancel
                    animate(mDownView)
                        .translationX(0f)
                        .alpha(1f)
                        .setDuration(mAnimationTime)
                        .setListener(null)
                }
                mVelocityTracker = null
                mDownX = 0f
                mDownView = null
                mDownPosition = ListView.INVALID_POSITION
                mSwiping = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (mVelocityTracker == null || mPaused) {
                    return false
                }
                mVelocityTracker?.addMovement(motionEvent)
                val deltaX: Float = motionEvent.getRawX() - mDownX
                if (Math.abs(deltaX) + 27 > mSlop) {
                    mSwiping = true
                    mTable!!.requestDisallowInterceptTouchEvent(true)

                    // Cancel ListView's touch (un-highlighting the item)
                    val cancelEvent = MotionEvent.obtain(motionEvent)
                    cancelEvent.action = MotionEvent.ACTION_CANCEL or
                            (motionEvent.getActionIndex()
                                    shl MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                    mTable!!.onTouchEvent(cancelEvent)
                }
                if (mSwiping) {
                    setTranslationX(mDownView, deltaX)
                    setAlpha(
                        mDownView, Math.max(
                            0f, Math.min(
                                1f,
                                1f - 2f * Math.abs(deltaX) / mViewWidth
                            )
                        )
                    )
                    return true
                }
            }
        }
        return false
    }

    private fun performDismiss(dismissView: View, dismissPosition: Int) {
        // Animate the dismissed list item to zero-height and fire the dismiss callback when
        // all dismissed list item animations have completed. This triggers layout on each animation
        // frame; in the future we may want to do something smarter and more performant.
        val lp = dismissView.layoutParams
        val originalHeight = dismissView.height
        val animator: ValueAnimator =
            ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                --mDismissAnimationRefCount
                if (mDismissAnimationRefCount == 0) {
                    // No active animations, process all pending dismisses.
                    // Sort by descending position
                    Collections.sort(mPendingDismisses)
                    val dismissPositions = IntArray(mPendingDismisses.size)
                    for (i in mPendingDismisses.indices.reversed()) {
                        dismissPositions[i] = mPendingDismisses[i].position
                    }
                    mCallback.onDismiss(mTable, dismissPositions)
                    var lp: ViewGroup.LayoutParams
                    for (pendingDismiss in mPendingDismisses) {
                        // Reset view presentation
                        setAlpha(pendingDismiss.view, 1f)
                        setTranslationX(pendingDismiss.view, 0f)
                        lp = pendingDismiss.view.layoutParams
                        lp.height = originalHeight
                        pendingDismiss.view.layoutParams = lp
                    }
                    mPendingDismisses.clear()
                }
            }
        })
        animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
                lp.height = (valueAnimator.getAnimatedValue() as Int)
                dismissView.layoutParams = lp
            }
        })
        mPendingDismisses.add(PendingDismissData(dismissPosition, dismissView))
        animator.start()
    }
}