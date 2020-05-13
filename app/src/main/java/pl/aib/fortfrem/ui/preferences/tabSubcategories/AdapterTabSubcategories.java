package pl.aib.fortfrem.ui.preferences.tabSubcategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;

public class AdapterTabSubcategories extends RecyclerView.Adapter<AdapterTabSubcategories.MyViewHolder>{
    Context context;
    private ArrayList categoriesAddedList;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.categories_list_item,parent,false);
        return new MyViewHolder(view);

    }

    public AdapterTabSubcategories(Context ct, ArrayList<Category> categoriesList){
        this.context = ct;
        this.categoriesAddedList = categoriesList;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTabSubcategories.MyViewHolder holder, int position) {
        Category currentItem = (Category) categoriesAddedList.get(position);
        holder.myText1.setText(currentItem.getTitle());
        holder.myImage.setImageResource(currentItem.getImg());
    }

    @Override
    public int getItemCount() {
        return categoriesAddedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1;
        ImageView myImage;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myImage = itemView.findViewById(R.id.myImageview);
        }
    }
}

