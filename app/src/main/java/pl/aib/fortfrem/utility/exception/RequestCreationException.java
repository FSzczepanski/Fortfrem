package pl.aib.fortfrem.utility.exception;

import androidx.annotation.NonNull;

public class RequestCreationException extends FortfremException {
    public RequestCreationException(@NonNull String tag) {
        super(tag);
    }
}
