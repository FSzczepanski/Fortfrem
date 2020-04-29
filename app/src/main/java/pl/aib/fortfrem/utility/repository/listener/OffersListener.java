package pl.aib.fortfrem.utility.repository.listener;

import java.util.List;

import pl.aib.fortfrem.data.entity.Offer;

public interface OffersListener {
    void onOffersReceived(List<Offer> offers);
}
