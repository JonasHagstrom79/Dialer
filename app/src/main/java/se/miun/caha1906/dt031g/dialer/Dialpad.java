package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Dialpad extends ConstraintLayout {

    public Dialpad(@NonNull Context context) {
        super(context);
    }

    public Dialpad(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Dialpad(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Takes care of all initialization the component needs     *
     */
    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.dialpad, this);

        // Retrieve custom attribues
        TypedArray customAttributes = getContext().getTheme().obtainStyledAttributes(
                attrs, // The base set of attribute values. May be null.
                R.styleable.DialpadButton, // Our custom attributes to be retrieved (in res/values/attrs.xml).
                0,0); // 0 = do to not look for default values


    }
}
