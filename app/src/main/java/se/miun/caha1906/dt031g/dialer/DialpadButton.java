package se.miun.caha1906.dt031g.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DialpadButton extends ConstraintLayout {

    // To get the sound
//    SoundPlayer s = SoundPlayer.getInstance(getContext());

    private static final String UNDEFINED = "";

    DialpadButton d = this;//test


    /**
     * Default constructor for java code
     */
    public DialpadButton(@NonNull Context context) {
        super(context);
        init(null);
    }

    /**
     * Costum constructor to use when setting title and message at the same time
     *  we create an instance.
     */
    public DialpadButton(@NonNull Context context, String title, String message) {
        super(context);
        init(null);
        setTitle(title);
        setMessage(message);
    }

    /**
     * Constructor used when creating instances of an xml file
     */
    public DialpadButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Takes care of all initialization the component needs     *
     */
    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_dialpadbutton, this);

        // Animates the view
        animateClick();

        // Retrieve custom attribues
        TypedArray customAttributes = getContext().getTheme().obtainStyledAttributes(
                attrs, // The base set of attribute values. May be null.
                R.styleable.DialpadButton, // Our custom attributes to be retrieved (in res/values/attrs.xml).
                0,0); // 0 = do to not look for default values

        String mess;
        String title;

        // Get the values for the custom attributes
        try {
            mess = customAttributes.getString(
                    R.styleable.DialpadButton_message);
            title = customAttributes.getString(
                    R.styleable.DialpadButton_title);
        } finally {
            customAttributes.recycle();
        }

        // Update the view to show string from attribute
        setMessage(mess);
        setTitle(title);

        // Call setSaveEnabled(true) to indicate that this view intends to save its state.
        setSaveEnabled(true);

    }

    /**
     * Animates the click on the component
     */
    @SuppressLint("ClickableViewAccessibility")
    private void animateClick() {

        setOnTouchListener((view, motionEvent) -> {
            Log.i("tag", "animateClick och view: " +view);
            Log.i("tag", "animateClick och dialpadbutton: " +d);
            switch (motionEvent.getAction()) {
                // When pushed
                case MotionEvent.ACTION_DOWN:
                    animate().alpha(0f).setDuration(500).start();
//                    s.playSound(d);
                    break;
                // When released
                case MotionEvent.ACTION_UP:
                    animate().alpha(1f).setDuration(500).start();
                    break;
            }
            //s.destroy();
            return true;
        });
        //s.destroy();
    }

    /**
     *  Sets the title of the view
     * @param newTitle string to be set
     */
    public void setTitle(@NonNull String newTitle) {

        if (TextUtils.isEmpty(newTitle)) {

            return;

        } else {
            String t = newTitle.substring(0,1).toUpperCase();
            TextView title = findViewById(R.id.textTitle);
            title.setText(t);
        }

    }

    /**
     * Sets the message for the view
     * @param newMessage string to be set
     */
    public void setMessage(@NonNull String newMessage) {

        String mess;

        if (TextUtils.isEmpty(newMessage)) {

            return;

        } else {

            if (newMessage.length() > 4) {
                mess = newMessage.substring(0, 4).toUpperCase();
            } else {
                mess = newMessage.toUpperCase();
            }
        }

        TextView message = findViewById(R.id.textMessage);
        message.setText(mess);
    }


    /**
     * Gets the title from view
     * */
    public String getTitle(){

        TextView title = findViewById(R.id.textTitle);
        return title.getText().toString();

    }
}
