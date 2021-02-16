package com.kushnir.elfc.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;

import com.kushnir.elfc.R;

public class AddSubjectDialogFragment extends DialogFragment {

    private final MutableLiveData<String> item;

    public AddSubjectDialogFragment(MutableLiveData<String> item) {
        this.item = item;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_subject, null))
                .setPositiveButton(R.string.add, (dialog, which) -> {
                    EditText text = getDialog().findViewById(R.id.dialog_subject_edit_text);
                    item.postValue(text.getText().toString());
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> getDialog().cancel());

        return builder.create();
    }
}