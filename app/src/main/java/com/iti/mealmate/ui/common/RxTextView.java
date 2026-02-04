package com.iti.mealmate.ui.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.rxjava3.core.Observable;

public class RxTextView {

    public static Observable<String> textChanges(EditText editText) {
        return Observable.create(emitter -> {
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            };
            editText.addTextChangedListener(watcher);
            emitter.setCancellable(() -> editText.removeTextChangedListener(watcher));
        });
    }
}