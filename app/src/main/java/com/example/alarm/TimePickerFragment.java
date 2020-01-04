package com.example.alarm;

        import android.app.Dialog;
        import android.app.TimePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.widget.TimePicker;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.DialogFragment;

        import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface OnTimeDialogListener {
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    OnTimeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnTimeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTimeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog and return it
        //TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,true);
        return new TimePickerDialog(getActivity(), this, hour, minute,true);
        //DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        mListener.onTimeSet(view, hourOfDay, minute);
    }
}
