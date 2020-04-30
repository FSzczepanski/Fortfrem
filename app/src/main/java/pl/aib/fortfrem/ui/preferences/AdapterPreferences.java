package pl.aib.fortfrem.ui.preferences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.aib.fortfrem.R;
import pl.aib.fortfrem.data.entity.Preference;

public class AdapterPreferences extends RecyclerView.Adapter<AdapterPreferences.MyViewHolder>{
    Context context;
    private ArrayList preferencesList;




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.preferences_list_item,parent,false);
        return new MyViewHolder(view);
    }

    public AdapterPreferences(Context context, ArrayList<Preference> preferencesList) {
        this.context = context;
        this.preferencesList = preferencesList;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Preference currentItem = (Preference) preferencesList.get(position);
        holder.myText1.setText(currentItem.getTitle());
    }


    @Override
    public int getItemCount(){
        return preferencesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1;


        public MyViewHolder(@NonNull View itemView){            super(itemView);
            myText1 = itemView.findViewById(R.id.preference_title);
        }
    }
}
