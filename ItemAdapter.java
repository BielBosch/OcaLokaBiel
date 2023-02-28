package cat.dam.biel.ocaloka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemAdapter extends BaseAdapter {

    Context context;
    String[] fitxesNoms;
    int[] fitxesIcons;

    public ItemAdapter(Context context, String[] fitxesNoms, int[] fitxesIcons) {
        this.context = context;
        this.fitxesNoms = fitxesNoms;
        this.fitxesIcons = fitxesIcons;
    }

    @Override
    public int getCount() {
        return fitxesNoms.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tv);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image_icon);
        textView.setText(fitxesNoms[position]);

        // Change the image source based on the position
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.capollblau);
                break;
            case 1:
                imageView.setImageResource(R.drawable.capollverd);
                break;
            case 2:
                imageView.setImageResource(R.drawable.capollgroc);
                break;
            case 3:
                imageView.setImageResource(R.drawable.capolllila);
                break;
            case 4:
                imageView.setImageResource(R.drawable.capollvarmell);
                break;
        }

        return rowView;
    }
}
