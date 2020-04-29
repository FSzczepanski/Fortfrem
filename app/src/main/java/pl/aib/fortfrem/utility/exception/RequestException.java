package pl.aib.fortfrem.utility.exception;

import androidx.annotation.NonNull;

public class RequestException extends FortfremException {
    public RequestException(@NonNull String tag) {
        super(tag);
    }
}
