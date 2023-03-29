package cat.dam.biel.ocaloka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterCasella extends RecyclerView.Adapter<GridAdapterCasella.BoxViewHolder> {

    public List<Box> boxList;

    public GridAdapterCasella(List<Box> boxList) {
        this.boxList = boxList;
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new BoxViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {
        Box box = boxList.get(position);
        holder.bind(box);
    }

    @Override
    public int getItemCount() {
        return boxList.size();
    }

    public static class BoxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView box_title;
        private TextView box_description;
        private ImageView box_image;
        private Box box; // added field to store Box object

        public BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            box_title = itemView.findViewById(R.id.box_title);
            box_description = itemView.findViewById(R.id.box_description);
            box_image = itemView.findViewById(R.id.box_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

                //pasa les dades necesaries altre activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", box.getImageUrl());
                resultIntent.putExtra("title", box.getTitle());
                resultIntent.putExtra("description", box.getDescription());
                resultIntent.putExtra("filled", true);
                Context ctx = itemView.getContext();
                Activity parentActivity = (Activity) ctx;
                parentActivity.setResult(Activity.RESULT_OK, resultIntent);
                parentActivity.finish();

        }
        public void bind(Box box) {
            this.box = box; // store Box object in ViewHolder
            box_title.setText(box.getTitle());//passar titol desc i imatge
            box_description.setText(box.getDescription());
            Picasso.get().load(box.getImageUrl()).into(box_image);
        }
    }
}