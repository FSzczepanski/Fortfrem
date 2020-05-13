package pl.aib.fortfrem.ui.preferences.tabCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Category;

public class AdapterTabCategories extends RecyclerView.Adapter<AdapterTabCategories.MyViewHolder>{
    Context context;
    private ArrayList<Category> categoriesAddedList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.categories_list_item,parent,false);
        return new MyViewHolder(view);

    }

    public AdapterTabCategories(Context ct, ArrayList<Category> categoriesAddedList){
        this.context = ct;
        this.categoriesAddedList = categoriesAddedList;
    }



    @Override
    public void onBindViewHolder(@NonNull AdapterTabCategories.MyViewHolder holder, int position) {
        Category currentItem = categoriesAddedList.get(position);
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
