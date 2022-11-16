package se.miun.caha1906.dt031g.dialer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DialpadButton extends ConstraintLayout {

    public DialpadButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DialpadButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        //inflate(context, R.layout.dialpadbutton, this);
        inflate(context, R.layout.view_dialpadbutton, this);

        setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animate().alpha(0f).setDuration(500).start();
                    break;
                case MotionEvent.ACTION_UP:
                    animate().alpha(1f).setDuration(500).start();
                    break;
            }
            return true;
        });
    }

//    // Does not make use of XML attributes
//    public DialpadButton(Context context) {
//        super(context);
//
//        // Add init method?
////        init(null);
//    }
//
//
//    //This constructor is used when you create instances from an xml layout file.
//    //For example, from activity_main.xml (where app:color is a custom attribute we
//    //can use to set starting color for the button):
//    public DialpadButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//        //return attrs;
//    }


    // 3 more ctors in original code?

    //ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat()




    // Use a private method to do the init
//    private void init(AttributeSet attrs) {
//        // Inflate the layout this component uses
//        inflate(getContext(), R.layout.dialpadbutton, this);
//
//        // Now we can get a reference to the views this component uses
//        dialPadMessage = findViewById(R.id.textDialpadMessage);
//        dialPadTitle = findViewById(R.id.textDialpadTitle);
//
//        ValueAnimator animation = ValueAnimator.ofFloat(0f, 100f); // TEST
//        animation.setDuration(1000); // Test
//        animation.start(); // Test
//        // Retrieve our custom attributes.
//        //TypedArray customAttributes = getContext().getTheme().obtainStyledAttributes(
//        //        attrs,
//        //        R.styleable.DialpadButton, // HÄR ÄR JAG!!!!
//        //        0, 0);
//
//
//    }
        //inflate(getContext(), R.layout.dialpadbutton, this);

    // Det ska finnas en metod som kan användas för att ändra vad som ska
    //TextView myTitle = findViewById(R.id.textDialpadTitle);
    // visas som title
    public void setTitle(String newTitle) {
        //myTitle.setText(newTitle);
        //return title;
    }

    //TextView myMessage = findViewById(R.id.textDialpadMessage);
    // Visar message under title
    public void setMessage(String newMessage) {
        // Sets message
        //myMessage.setText(newMessage);
    }
}
