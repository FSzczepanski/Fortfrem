package pl.aib.fortfrem.utility.repository.listener;

import pl.aib.fortfrem.utility.exception.FortfremException;

public interface OffersErrorListener {
    void onError(FortfremException e);
}
