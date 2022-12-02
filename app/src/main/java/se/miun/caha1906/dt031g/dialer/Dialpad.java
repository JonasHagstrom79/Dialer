package se.miun.caha1906.dt031g.dialer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.zip.Inflater;

public class Dialpad extends ConstraintLayout {

    public Dialpad(@NonNull Context context) {
        super(context);
    }

    public Dialpad(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Dialpad(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // Use the layout in class if needed
    private void init(Context context) {
        inflate(context, R.layout.dialpad, this);
    }
}
