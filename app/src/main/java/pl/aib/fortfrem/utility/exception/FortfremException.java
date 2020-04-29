package pl.aib.fortfrem.utility.exception;

import androidx.annotation.NonNull;

public class FortfremException extends Exception {

    @NonNull
    protected String tag;

    @NonNull
    protected String message;

    public FortfremException(@NonNull String tag) {
        super(tag);
        this.tag = tag;
        this.message = this.resolveMessage();
    }

    @NonNull
    protected String resolveMessage() {
        return "Unknown error occurred";
    }

    @NonNull
    public String getTag() {
        return tag;
    }
}
