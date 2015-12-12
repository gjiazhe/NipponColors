package com.gjiazhe.nipponcolors.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.TextView;

/**
 * Created by gjz on 11/25/15.
 */
public class GGScrollNumberView extends TextView {

    private float currentNumberOfFloat = 0;
    private int currentNumberOfInt = 0;
    private ObjectAnimator objectAnimator = new ObjectAnimator();
    private long duration = 1500;
    private int delay = 0;
    private String format;
    private Interpolator interpolator = null;

    public GGScrollNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Initializes the int values the view scrolls between. A single value implies that
     * the view will scroll to this value. Two values imply starting and ending values.
     * More than two values imply a starting value, values to scroll through
     * along the way, and an ending value.
     * @param numbers A set of values that the view will scroll between over time.
     * @return GGScrollNumberView The object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setNumberSetOfInt(int... numbers) {
        if (numbers.length == 1) {
            numbers = new int[] {this.currentNumberOfInt, numbers[0]};
        }
        this.objectAnimator = ObjectAnimator.ofInt(this, "currentNumberOfInt", numbers);

        return this;
    }

    /**
     * Initializes the float values the view scrolls between. A single value implies that
     * the view will scroll to this value. Two values imply starting and ending values.
     * More than two values imply a starting value, values to scroll through
     * along the way, and an ending value.
     * @param numbers A set of values that the view will scroll between over time.
     * @return GGScrollNumberView The object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setNumberSetOfFloat(float... numbers) {
        if (numbers.length == 1) {
            numbers = new float[] {this.currentNumberOfFloat, numbers[0]};
        }
        this.objectAnimator = ObjectAnimator.ofFloat(this, "currentNumberOfFloat", numbers);

        return this;
    }

    public void setCurrentNumberOfInt(int number) {
        this.currentNumberOfInt = number;
        if (this.format == null) {
            this.setText(number + "");
        }else {
            this.setText(String.format(format, number));
        }
    }
    
    public void setCurrentNumberOfFloat(float number) {
        this.currentNumberOfFloat = number;
        if (this.format == null) {
            this.setText(number + "");
        }else {
            this.setText(String.format(format, number));
        }
    }

    /**
     * Sets the length of the scroll animation. The default duration is 0.
     * @param duration The length of the scroll animation, in milliseconds.
     * @return GGScrollNumberView The object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the time to delay running the scroll animation, in milliseconds.
     * @param delay the amount of time to delay the scroll animation, in milliseconds
     * @return GGScrollNumberView The object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    /**
     * Returns the time to delay running the scroll animation after {@link #startAnim()} is called, in milliseconds.
     * @return the amount of time to delay running the scroll animation, in milliseconds
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Sets the format the display numbers
     * @param format the format the display numbers
     * @return GGScrollNumberView The object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * @return the format the display numbers
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the time interpolator used in calculating the elapsed fraction of the animation. The
     * interpolator determines whether the animation runs with linear or non-linear motion,
     * such as acceleration and deceleration. The default value is
     * {@link android.view.animation.AccelerateDecelerateInterpolator}
     *
     * @param interpolator the interpolator to be used by the scroll animation. A value of <code>null</code>
     * will result in linear interpolation.
     *
     * @return GGScrollNumberView the object called this method, making it easy to use chainable syntax
     */
    public GGScrollNumberView setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    /**
     * @return the time interpolator used by the scroll animation
     */
    public Interpolator getInterpolator() {
        return interpolator;
    }

    /**
     * Returns whether the scroll animation is running, it becomes true after the first animation frame starts,
     * and becomes false after the last animation frame finishes.
     * Note that it's still false before the end of delay.
     * @return TRUE if the scroll animation is running
     */
    public boolean isAnimRunning() {
        return objectAnimator.isRunning();
    }

    /**
     * Returns whether the scroll animation is in paused state currently.
     * @return TRUE if the scroll animation is in paused state currently
     */
    public boolean isAnimPaused() {
        return objectAnimator.isPaused();
    }

    /**
     * Returns whether the scroll animation is started, whether or not there is a nonzero delay.
     * Note that it returns false after the scroll animation finishes.
     * @return TRUE if the scroll animation is started and hasn't finished
     */
    public boolean isAnimStarted() {
        return objectAnimator.isStarted();
    }

    /**
     * Start the scroll animation playing.
     */
    public void startAnim() {
        objectAnimator.setDuration(this.duration).setStartDelay(this.delay);
        objectAnimator.setInterpolator(this.interpolator);

        objectAnimator.start();
    }

    /**
     * Pauses the scroll animation. This method should only be called on the same thread on
     * which the animation was started. If the animation has not yet been started or has since ended,
     * then the call is ignored. Paused scroll animations can be resumed by calling {@link #resumeAnim()}.
     *
     * @see android.animation.Animator#pause()
     */
    public void pauseAnim() {
        objectAnimator.pause();
    }

    /**
     * Resumes a paused scroll animation, causing the animator to pick up where it left off
     * when it was paused. This method should only be called on the same thread on
     * which the animation was started. Calls to resume() on an animator that is
     * not currently paused will be ignored.
     *
     * @see android.animation.Animator#resume()
     */
    public void resumeAnim() {
        objectAnimator.resume();
    }


}
