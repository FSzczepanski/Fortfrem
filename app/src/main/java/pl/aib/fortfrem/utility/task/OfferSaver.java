package pl.aib.fortfrem.utility.task;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.data.Database;
import pl.aib.fortfrem.data.entity.Offer;
import pl.aib.fortfrem.utility.repository.listener.OffersListener;

public class OfferSaver extends AsyncTask<JSONObject, Offer, List<Offer>> {
    private final WeakReference<Context> contextRef;

    public OfferSaver(Context context) {
        this.contextRef = new WeakReference<Context>(context);
    }

    @Override
    protected List<Offer> doInBackground(JSONObject... offersJson) {
        List<Offer> offers = new ArrayList<>();
        Database db = Database.getInstance(this.contextRef.get());
        for(JSONObject offerJson : offersJson) {
            try {
                Offer offer = new Offer();
                offer.setOid(offerJson.getString("oid"));
                offer.setTitle(offerJson.getString("title"));
                offer.setImageUrl(offerJson.getString("image_url"));
                offer.setDescription("<b>Hello</b> <i>World</i> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc id arcu at sem tincidunt bibendum et a ante. Sed aliquet nunc posuere leo varius vulputate in et elit. Vestibulum orci massa, sagittis id pellentesque sed, aliquam vitae mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce libero nibh, ullamcorper eu lectus et, fringilla facilisis enim. Phasellus eget ultrices nunc. Integer at nisi eget sapien tristique egestas. Fusce a nunc et tellus bibendum accumsan et at augue. Aliquam sodales pulvinar aliquet. Ut interdum ullamcorper mi consectetur tincidunt. Quisque bibendum erat et ligula placerat vestibulum. Suspendisse ultrices, orci sed interdum commodo, elit ligula interdum urna, vitae sagittis ante nisl quis turpis. Aliquam felis erat, lobortis et dignissim non, dictum nec sem.\n" +
                        "\n" +
                        "Donec in pulvinar nunc, et suscipit nulla. Nullam pellentesque, eros maximus molestie bibendum, arcu nisl placerat tellus, in gravida tortor sem vel neque. Morbi ut lectus quis lorem tincidunt convallis eget in eros. Suspendisse in ipsum molestie, ullamcorper lorem quis, posuere dui. Ut varius fringilla urna vitae porttitor. Sed molestie non sem et aliquet. Phasellus sem sem, tincidunt id maximus sit amet, lobortis ut purus. Quisque tristique libero nec risus convallis, a volutpat nulla aliquet.\n" +
                        "\n" +
                        "Phasellus ut risus a dolor pretium maximus vel eget nibh. Sed non pellentesque nisi, sit amet vehicula metus. Pellentesque pellentesque eros vitae nunc interdum venenatis. Cras faucibus ligula tristique tellus scelerisque posuere. Aenean at diam eget ligula mattis dapibus eu a lectus. Etiam ultricies nunc et lorem dictum hendrerit et a tortor. Nam id commodo ligula. Sed maximus, velit et ornare pretium, nulla massa feugiat turpis, consequat semper lectus odio feugiat leo. Aliquam pretium sem in lacus semper, eget mollis sapien bibendum.\n" +
                        "\n" +
                        "Donec lacinia ligula ac quam mollis auctor. Proin vel fringilla dui. Vivamus congue dui nec orci hendrerit, in aliquam est dignissim. In ultrices accumsan augue iaculis placerat. Nunc est sapien, congue quis lobortis ut, efficitur nec augue. Phasellus in libero enim. Nam sit amet accumsan lectus. Quisque imperdiet fermentum porttitor. Aenean venenatis tincidunt ipsum eget ultrices. Sed pellentesque ante et volutpat rutrum. Vestibulum semper accumsan dui in suscipit. Cras quis consectetur diam. Curabitur dictum interdum nunc. Nulla lectus purus, sollicitudin vel dolor vel, pellentesque consectetur mauris. Donec quam quam, tristique sit amet congue a, dapibus ac elit.\n" +
                        "\n" +
                        "Aliquam fringilla sapien nec ex ullamcorper venenatis. Vestibulum efficitur eget nulla quis finibus. Aenean in lacus ullamcorper turpis vestibulum porta eget sed eros. Sed vel nibh id augue molestie posuere a quis magna. Curabitur lobortis diam eu risus dictum, id pellentesque ipsum molestie. Aenean porttitor congue ligula, vel fringilla tellus eleifend quis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla facilisi. Nulla laoreet volutpat dolor, a suscipit ex feugiat quis. Interdum et malesuada fames ac ante ipsum primis in faucibus.");
                offer.setUrl("https://www.google.com");
                offer.setStoreName(offerJson.getString("store"));
                offer.setStoreLogoUrl(offerJson.getString("store_logo_url"));
                offer.setCategory(offerJson.getString("category"));
                offer.setSubcategory(offerJson.getString("subcategory"));
                offer.setPrice(offerJson.getInt("price"));
                offer.setOldPrice(offerJson.getInt("old_price"));
                offer.setStartTime(offerJson.getLong("start_time"));
                offer.setEndTime(offerJson.getLong("end_time"));
                offers.add(offer);
            } catch (JSONException ignored) {}
        }
        db.offerDao().insert(offers.toArray(new Offer[]{}));
        return offers;
    }

}
